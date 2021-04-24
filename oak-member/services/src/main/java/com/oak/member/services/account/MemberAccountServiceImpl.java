package com.oak.member.services.account;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SettingValueHelper;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.api.utils.DataSignUtil;
import com.oak.member.entities.E_MemberAccount;
import com.oak.member.entities.MemberAccount;
import com.oak.member.enums.AccountOperatorValueType;
import com.oak.member.enums.AccountStatus;
import com.oak.member.enums.VipGrade;
import com.oak.member.services.account.info.MemberAccountInfo;
import com.oak.member.services.account.req.ChangeAccountReq;
import com.oak.member.services.account.req.CreateMemberAccountReq;
import com.oak.member.services.account.req.QueryMemberAccountReq;
import com.oak.member.services.account.req.UpdateAccountStatusReq;
import com.oak.member.services.accountlog.MemberAccountLogService;
import com.oak.member.services.accountlog.req.CreateMemberAccountLogReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.oak.member.constant.MemberAccountSettingsConstant.POINTS_AMOUNT_RATION;
import static java.math.BigDecimal.ROUND_HALF_UP;


/**
 * 会员账户信息服务
 * 2020-6-8 10:22:51
 */
@Service
@Slf4j
public class MemberAccountServiceImpl implements MemberAccountService {


    @Autowired
    private JpaDao jpaDao;

    @Autowired
    private MemberAccountLogService memberAccountLogService;

    @Autowired
    private SettingValueHelper settingValueHelper;

    public static final String ACCOUNT_SECURITY_VERIFICATION_ENABLE = "account.security.verification";

    /**
     * 最大抵扣积分
     */
    private static final String MAX_DEDUCT_POINTS = "max.deduct.points";

    /**
     * 单位兑换积分
     */
    public static final String UNIT_INTEGRAL = "unit.integral";

    /**
     * 单位积分所能抵扣金额数量
     */
    public static final String DEDUCT_AMOUNT = "deduct.amount";

    @Override
    public ApiResp<Long> create(CreateMemberAccountReq req) {


        MemberAccount entity = new MemberAccount();
        entity.setId(req.getMemberId())
                .setMoney(0)
                .setPoints(0)
                .setCoupon(0)
                .setFrozenMoney(0)
                .setFrozenPoints(0)
                .setFrozenCoupon(0)
                .setStatus(AccountStatus.AVAILABLE)
                .setVipGrade(VipGrade.M_COMMON);
        Date date = new Date();
        entity.setCreateTime(date)
                .setLastUpdateTime(date)
                .setCheckCode(this.generateCheckCode(entity));

        jpaDao.save(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }


    @Override
    public ApiResp<Void> updateStatus(UpdateAccountStatusReq req) {

        MemberAccount entity = jpaDao.find(MemberAccount.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("会员账户信息数据不存在");
        }
        int update = jpaDao.updateTo(MemberAccount.class).appendByQueryObj(req)
                .set(E_MemberAccount.lastUpdateTime, new Date())
                .update();
        if (update != 1) {
            return RestfulApiRespFactory.error("更新会员数据失败");
        }
        return RestfulApiRespFactory.ok();
    }


    @Override
    @Transactional
    public ApiResp<Void> changeAccount(ChangeAccountReq req) {
        MemberAccount entity = jpaDao.find(MemberAccount.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("会员账户信息数据不存在");
        }
        // 用于生成新的校验码
        MemberAccount memberAccount = new MemberAccount();
        BeanUtils.copyProperties(entity, memberAccount);

        String checkCode = entity.getCheckCode();
        String currentCode = this.generateCheckCode(entity);
        if (settingValueHelper.getSettingValue(ACCOUNT_SECURITY_VERIFICATION_ENABLE, false) && !checkCode.equals(currentCode)) {
            return RestfulApiRespFactory.error("账号更新校验信息验证失败");
        }

        Map<AccountOperatorValueType, CreateMemberAccountLogReq> logs = new LinkedHashMap<>();

        UpdateDao<MemberAccount> updateDao = jpaDao.updateTo(MemberAccount.class)
                .eq(E_MemberAccount.id, req.getId());
        if (settingValueHelper.getSettingValue(ACCOUNT_SECURITY_VERIFICATION_ENABLE, false)){
            updateDao.eq(E_MemberAccount.checkCode, checkCode);
        }

        // 更新余额
        if (req.getMoney() != null) {
            ApiResp<Integer> resp = valueChange(entity.getMoney(), req.getMoney(), "当前变更余额大于现有账户余额，请重新确认");
            if (!resp.isSuccess()) {
                return RestfulApiRespFactory.error(resp.getErrorMessage());
            }

            updateDao.set(E_MemberAccount.money, resp.getData());
            memberAccount.setMoney(resp.getData());

            CreateMemberAccountLogReq createReq = new CreateMemberAccountLogReq();
            createReq.setOperatorValue(req.getMoney());
            createReq.setCurrentValue(entity.getMoney());
            logs.put(AccountOperatorValueType.MONEY, createReq);
        }

        if (req.getFrozenMoney() != null) {
            ApiResp<Integer> resp = valueChange(entity.getFrozenMoney(), req.getFrozenMoney(), "当前变更冻结金额大于现有账户冻结金额，请重新确认");
            if (!resp.isSuccess()) {
                return RestfulApiRespFactory.error(resp.getErrorMessage());
            }
            updateDao.set(E_MemberAccount.frozenMoney, resp.getData());
            memberAccount.setFrozenMoney(resp.getData());

            CreateMemberAccountLogReq createReq = logs.getOrDefault(AccountOperatorValueType.MONEY, new CreateMemberAccountLogReq());
            createReq.setFrozenValue(req.getFrozenMoney());
            createReq.setCurrentFrozenValue(entity.getFrozenMoney());
            logs.put(AccountOperatorValueType.MONEY, createReq);
        }
        // 更新积分
        if (req.getPoints() != null) {
            ApiResp<Integer> resp = valueChange(entity.getPoints(), req.getPoints(), "当前变更积分大于现有账户积分，请重新确认");
            if (!resp.isSuccess()) {
                return RestfulApiRespFactory.error(resp.getErrorMessage());
            }
            updateDao.set(E_MemberAccount.points, resp.getData());
            memberAccount.setPoints(resp.getData());

            CreateMemberAccountLogReq createReq = new CreateMemberAccountLogReq();
            createReq.setOperatorValue(req.getPoints());
            createReq.setCurrentValue(entity.getPoints());
            logs.put(AccountOperatorValueType.POINTS, createReq);
        }

        if (req.getFrozenPoints() != null) {
            ApiResp<Integer> resp = valueChange(entity.getFrozenPoints(), req.getFrozenPoints(), "当前变更冻结积分大于现有账户冻结积分，请重新确认");
            if (!resp.isSuccess()) {
                return RestfulApiRespFactory.error(resp.getErrorMessage());
            }
            updateDao.set(E_MemberAccount.frozenPoints, resp.getData());
            memberAccount.setFrozenPoints(resp.getData());

            CreateMemberAccountLogReq createReq = logs.getOrDefault(AccountOperatorValueType.POINTS, new CreateMemberAccountLogReq());
            createReq.setFrozenValue(req.getFrozenPoints());
            createReq.setCurrentFrozenValue(entity.getFrozenPoints());
            logs.put(AccountOperatorValueType.POINTS, createReq);
        }

        // 更新代金券
        if (req.getCoupon() != null) {
            ApiResp<Integer> resp = valueChange(entity.getCoupon(), req.getCoupon(), "当前变更代金券大于现有账户代金券，请重新确认");
            if (!resp.isSuccess()) {
                return RestfulApiRespFactory.error(resp.getErrorMessage());
            }
            updateDao.set(E_MemberAccount.coupon, resp.getData());
            memberAccount.setCoupon(resp.getData());
            CreateMemberAccountLogReq createReq = new CreateMemberAccountLogReq();
            createReq.setOperatorValue(req.getCoupon());
            createReq.setCurrentValue(entity.getCoupon());
            logs.put(AccountOperatorValueType.COUPON, createReq);
        }

        if (req.getFrozenCoupon() != null) {
            ApiResp<Integer> resp = valueChange(entity.getFrozenCoupon(), req.getFrozenCoupon(), "当前变更冻结代金券大于现有账户冻结冻结代金券，请重新确认");
            if (!resp.isSuccess()) {
                return RestfulApiRespFactory.error(resp.getErrorMessage());
            }
            updateDao.set(E_MemberAccount.frozenCoupon, resp.getData());
            memberAccount.setFrozenCoupon(resp.getData());
            CreateMemberAccountLogReq createReq = logs.getOrDefault(AccountOperatorValueType.POINTS, new CreateMemberAccountLogReq());
            createReq.setFrozenValue(req.getFrozenCoupon());
            createReq.setCurrentFrozenValue(entity.getFrozenCoupon());
            logs.put(AccountOperatorValueType.COUPON, createReq);
        }

        //设置最后修改时间
        Date lastUpdateTime = new Date();
        int update = updateDao.set(E_MemberAccount.lastUpdateTime, lastUpdateTime)
                .set(E_MemberAccount.checkCode, this.generateCheckCode(memberAccount))
                .set(E_MemberAccount.version, entity.getVersion() + 1)
//                .eq(E_MemberAccount.version, entity.getVersion())
                .update();
        if (update != 1) {
            return RestfulApiRespFactory.error("更新会员账户信息失败");
        }

        // 写操作日志
        logs.forEach((key, value) -> {
            value.setValueType(key)
                    .setBusinessType(req.getBusinessType())
                    .setDescription(req.getDescription())
                    .setStatus(entity.getStatus())
                    .setOperator(req.getOperator())
                    .setMemberId(entity.getId())
                    .setTargetId(req.getTargetId());
            memberAccountLogService.create(value);
        });

        return RestfulApiRespFactory.ok();
    }

    @Override
    public MemberAccountInfo findById(Long id) {

        QueryMemberAccountReq queryReq = new QueryMemberAccountReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }


    @Override
    public Pagination<MemberAccountInfo> query(QueryMemberAccountReq req) {
        return SimpleCommonDaoHelper.queryObject(jpaDao, MemberAccount.class, MemberAccountInfo.class, req);
    }

    /**
     * 根据系统设置自动结算当前用户积分所能兑换的金额
     *
     * @param points 当前用户可用的积分
     * @return 积分所能兑换的金额
     */
    @Override
    public Integer pointsToFee(int points) {
        //当前最大抵扣积分
        Integer maxPoints = settingValueHelper.getSettingValue(MAX_DEDUCT_POINTS, 0);
        //可用的兑换的积分
        int usePoints = Math.min(maxPoints, points);
        //单位积分
        int unitIntegral = settingValueHelper.getSettingValue(UNIT_INTEGRAL, 0);
        if (0 == unitIntegral) {
            return 0;
        } else {
            //兑换金额
            int deductAmount = settingValueHelper.getSettingValue(DEDUCT_AMOUNT, 0);
            //最终金额
            int money = new BigDecimal(usePoints)
                    .divide(new BigDecimal(unitIntegral), 3, ROUND_HALF_UP)
                    .multiply(new BigDecimal(deductAmount))
                    .intValue();

            return money;
        }
    }

    @Override
    public Integer feeToPoints(int amount) {
        // 默认100积分抵扣1元
//        int ration = this.settingValueHelper.getSettingValue(POINTS_AMOUNT_RATION, 100);
//        return BigDecimal.valueOf(amount)
//                .divide(BigDecimal.valueOf(100))
//                .multiply(BigDecimal.valueOf(ration))
//                .intValue();

        //兑换金额
        int deductAmount = settingValueHelper.getSettingValue(DEDUCT_AMOUNT, 0);

        if( 0 == deductAmount ){
            return 0;
        }else {
            //单位积分
            int unitIntegral = settingValueHelper.getSettingValue(UNIT_INTEGRAL, 0);

            int points = new BigDecimal(amount)
                    .divide(new BigDecimal(deductAmount), 3, ROUND_HALF_UP)
                    .multiply(new BigDecimal(unitIntegral))
                    .intValue();

            return points;
        }
    }


    /**
     * 用于生成账号更新的校验码，防止账号被恶意修改过
     *
     * @return
     */
    private String generateCheckCode(MemberAccount account) {
        try {
            return DataSignUtil.buildSign(account, null,
                    E_MemberAccount.checkCode,
                    E_MemberAccount.lastUpdateTime,
                    E_MemberAccount.status,
                    E_MemberAccount.vipGrade,
                    E_MemberAccount.version
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 账户数值变更，判断账户数值变更合理性
     *
     * @param currentValue 当前值
     * @param changeValue  调整的值
     * @param errorMessage 错误消息
     * @return 值是否允许改变
     */
    private ApiResp<Integer> valueChange(Integer currentValue, Integer changeValue, String errorMessage) {
        int result = currentValue + changeValue;
        if (result < 0) {
            return RestfulApiRespFactory.error(errorMessage);
        } else {
            return RestfulApiRespFactory.ok(result);
        }

    }

}
