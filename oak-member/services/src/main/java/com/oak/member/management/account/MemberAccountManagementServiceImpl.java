package com.oak.member.management.account;


import com.oak.api.helper.SettingValueHelper;
import com.oak.member.constant.DepositCashConstant;
import com.oak.member.constant.MemberAccountSettingsConstant;
import com.oak.member.constant.WithdrawBusinessConstant;
import com.oak.member.enums.AccountStatus;
import com.oak.member.enums.WithdrawStatus;
import com.oak.member.management.account.info.TodayMemberWithdraw;
import com.oak.member.management.account.req.ApplyWithdrawalReq;
import com.oak.member.management.account.req.CheckWithdrawReq;
import com.oak.member.services.account.MemberAccountService;
import com.oak.member.services.account.info.MemberAccountInfo;
import com.oak.member.services.account.req.ChangeAccountReq;
import com.oak.member.services.depositcashmethod.DepositCashMethodService;
import com.oak.member.services.depositcashmethod.info.DepositCashMethodInfo;
import com.oak.member.services.memberdepositcash.MemberDepositCashService;
import com.oak.member.services.memberdepositcash.info.MemberDepositCashInfo;
import com.oak.member.services.memberdepositcash.req.CreateMemberDepositCashReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * @author wxup
 */
@Service
@Slf4j
public class MemberAccountManagementServiceImpl implements MemberAccountManagementService {


    @Autowired
    private MemberAccountService memberAccountService;

    @Autowired
    private SettingValueHelper settingValueHelper;

    @Autowired
    private DepositCashMethodService depositCashMethodService;

    @Autowired
    private MemberDepositCashService memberDepositCashService;

    @Override
    public ApiResp<Void> checkWithdrawRequest(CheckWithdrawReq req) {

        //系统是否开启提现功能
        boolean withdrawEnableValue = settingValueHelper.getSettingValue(DepositCashConstant.SYSTEM_WITHDRAWAL_ENABLE,
                DepositCashConstant.DEFAULT_SYSTEM_WITHDRAWAL_ENABLE);
        if (!withdrawEnableValue) {
            return RestfulApiRespFactory.error("当前系统不支持提现功能");
        }

        //判断是否会员实名制验证情况
        boolean systemMemberRealNameEnable = settingValueHelper.getSettingValue(MemberAccountSettingsConstant.SYSTEM_MEMBER_REAL_NAME_ENABLE,
                MemberAccountSettingsConstant.DEFAULT_SYSTEM_MEMBER_REAL_NAME_ENABLE);

        if (systemMemberRealNameEnable) {
            //如果为true ，则需要进行会员账户实名制校验功能
            //TODO 进行实名制验证
        }

        //判断账户是否存在或者是被冻结
        MemberAccountInfo accountInfo = memberAccountService.findById(req.getMemberId());

        if (accountInfo == null) {
            return RestfulApiRespFactory.error("当前会员账户不存在，请重新进行初始化");
        }
        if (AccountStatus.FREEZE.equals(accountInfo.getStatus())) {
            return RestfulApiRespFactory.error("当前账户被冻结，无法操作");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    @Transactional
    public ApiResp<Void> applyWithdrawal(ApplyWithdrawalReq req) {

        //前置提现金额限制内容
        ApiResp<Void> preconditionResp = checkWithdrawRequest(new CheckWithdrawReq().setMemberId(req.getMemberId()));
        if (!preconditionResp.isSuccess()) {
            return RestfulApiRespFactory.error(preconditionResp.getErrorMessage());
        }

        //提现校验规则
        //（1）提现金额不能 小于 单笔最低提现金额
        int minWithdrawValue = settingValueHelper.getSettingValue(DepositCashConstant.MEMBER_WITHDRAW_MIN_AMOUNT,
                DepositCashConstant.DEFAULT_MEMBER_WITHDRAW_MIN_AMOUNT);
        if (req.getAmount() < minWithdrawValue) {
            return RestfulApiRespFactory.error("提现金额尚未达到最低提现金额");
        }

        // (2) 提现金额不能 大于 单笔提现最大金额
        int maxWithdrawValue = settingValueHelper.getSettingValue(DepositCashConstant.MEMBER_WITHDRAW_MAX_AMOUNT,
                DepositCashConstant.DEFAULT_MEMBER_WITHDRAW_MAX_AMOUNT);

        if (req.getAmount() > maxWithdrawValue) {
            return RestfulApiRespFactory.error("提现金额不能大于单笔最大提现金额");
        }

        // （3） 会员账户中需要有足够的金额提现
        MemberAccountInfo accountInfo = memberAccountService.findById(req.getMemberId());
        if (req.getAmount() > accountInfo.getAvailableMoney()) {
            return RestfulApiRespFactory.error("当前账户余额不足");
        }

        // （4） 所选提现方式是否支持
        DepositCashMethodInfo withdrawMethodInfo = depositCashMethodService.findByCode(req.getDepositCashCode());
        if (withdrawMethodInfo == null || !withdrawMethodInfo.getEnabled()) {
            return RestfulApiRespFactory.error("提现方式暂不支持");
        }

        //获取当前用户的转账记录
        TodayMemberWithdraw todayMemberWithdraw = getTodayMemberWithdraw(req.getMemberId());
        //获取当日提现最高限制金额
        int dailyAmount = settingValueHelper.getSettingValue(DepositCashConstant.MEMBER_WITHDRAW_DAILY_LIMIT_AMOUNT,
                DepositCashConstant.DEFAULT_MEMBER_WITHDRAW_DAILY_LIMIT_AMOUNT);

        // (5)  当前账户已经申请提现金额 + 准备要提现的金额 是否  大于 每日提现金额总额
        if (todayMemberWithdraw.getAmount() + req.getAmount() > dailyAmount) {
            return RestfulApiRespFactory.error("当日提现申请额度已达上限");
        }

        //获取当日提现最大限制次数
        int dailyLimitNumber = settingValueHelper.getSettingValue(DepositCashConstant.MEMBER_WITHDRAW_DAILY_LIMIT_NUMBER,
                DepositCashConstant.DEFAULT_MEMBER_WITHDRAW_LIMIT_NUMBER);

        //（6） 当前账户已经提现次数 + 当前准备提现次数（1次）是否 大于 每日提现总次数
        if (todayMemberWithdraw.getNumber() + 1 > dailyLimitNumber) {
            return RestfulApiRespFactory.error("当日提现申请次数已达上限");
        }

        //计算 手续费
        int cashFee = withdrawMethodInfo.getFee();
        int fee = cashFee > 0 ? new BigDecimal(cashFee)
                .divide(new BigDecimal("1000"), 3, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(req.getAmount()))
                .setScale(0, BigDecimal.ROUND_UP).intValue() : 0;

        //创建提现申请记录
        CreateMemberDepositCashReq createCashReq = new CreateMemberDepositCashReq();
        createCashReq.setWithdrawStatus(WithdrawStatus.APPLY)
                .setApplyDate(new Date())
                .setMemberId(req.getMemberId())
                .setAccountName(req.getBankUser())
                .setAccountNumber(req.getBankNo())
                .setWithdrawCode(req.getDepositCashCode())
                .setAmount(req.getAmount())
                .setHandlingFee(fee)
                .setActualAmount(req.getAmount() - fee);

        ApiResp<Long> withdrawApiResp = memberDepositCashService.create(createCashReq);
        if (!withdrawApiResp.isSuccess()) {
            return RestfulApiRespFactory.error("会员提现申请失败");
        }

        MemberDepositCashInfo memberDepositCashInfo = memberDepositCashService.findById(withdrawApiResp.getData());

        ChangeAccountReq changeAccountReq = new ChangeAccountReq();
        changeAccountReq.setId(req.getMemberId())
                .setFrozenMoney(req.getAmount())
                .setBusinessType(StringUtils.isBlank(req.getBusinessType()) ? WithdrawBusinessConstant.WITHDRAW_OPERATING : req.getBusinessType())
                .setTargetId(String.valueOf(memberDepositCashInfo.getId()))
                .setDescription(MessageFormat.format("会员（id：{0}）发起提现操作，冻结金额增加{1}", req.getMemberId(), req.getAmount()))
                .setOperator(req.getOperator());
        //变更会员账户金额
        ApiResp<Void> memberAccountLogApiResp = memberAccountService.changeAccount(changeAccountReq);

        if (!memberAccountLogApiResp.isSuccess()) {
            return memberAccountLogApiResp;
        }

        //使用自动转账
        //是否进行自动转账 提现金额是否支持自动转账
        boolean autoWithdrawEnable = settingValueHelper.getSettingValue(DepositCashConstant.SYSTEM_AUTO_WITHDRAWAL_ENABLE,
                DepositCashConstant.DEFAULT_SYSTEM_AUTO_WITHDRAWAL_ENABLE);

        if (req.getAmount() > withdrawMethodInfo.getAutoAmount() && autoWithdrawEnable) {
            //支持自动转账
            //创建自动提现请求
            // TODO 自动转账功能


        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public TodayMemberWithdraw getTodayMemberWithdraw(Long memberId) {

        TodayMemberWithdraw todayMemberWithdraw = new TodayMemberWithdraw();

        //获取当前用户的转账记录
        List<MemberDepositCashInfo> cashInfoList = memberDepositCashService.queryByMemberDaily(memberId);

        if (cashInfoList != null && !cashInfoList.isEmpty()) {

            int withdrawTotalAmount = cashInfoList.stream()
                    .map(MemberDepositCashInfo::getAmount)
                    .reduce(0, Integer::sum);

            todayMemberWithdraw.setAmount(withdrawTotalAmount)
                    .setNumber(cashInfoList.size());

        } else {
            todayMemberWithdraw.setAmount(0)
                    .setNumber(0);
        }

        return todayMemberWithdraw;
    }
}
