package com.oak.provider.member.services.member;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.provider.member.entities.E_Member;
import com.oak.provider.member.entities.Member;
import com.oak.provider.member.enums.AccountStatus;
import com.oak.provider.member.enums.VipGrade;
import com.oak.provider.member.helper.SnHelper;
import com.oak.provider.member.services.account.MemberAccountService;
import com.oak.provider.member.services.account.req.CreateMemberAccountReq;
import com.oak.provider.member.services.member.info.MemberInfo;
import com.oak.provider.member.services.member.req.*;
import com.oak.provider.member.services.open.MemberOpenService;
import com.oak.provider.member.services.open.req.CreateMemberOpenReq;
import com.oak.provider.member.services.secure.MemberSecureService;
import com.oak.provider.member.services.secure.req.CreateMemberSecureReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;


/**
 * 会员信息服务
 * 2020-2-6 15:32:43
 */
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {


    @Autowired
    private JpaDao jpaDao;

    @Autowired
    private MemberAccountService accountService;

    @Autowired
    private MemberSecureService secureService;

    @Autowired
    private MemberOpenService openService;

    @Override
    @Transactional
    public ApiResp<Long> create(CreateMemberReq req) {
        //生成member no
        String no = SnHelper.generateShortUuid();
        req.setNo(no);

        long noC = jpaDao.selectFrom(Member.class)
                .eq("no", req.getNo())
                .count();
        if (noC > 0) {
            return RestfulApiRespFactory.error("系统忙，请重试");
        }

        long userNameC = jpaDao.selectFrom(Member.class)
                .eq("userName", req.getUserName())
                .count();
        if (userNameC > 0) {
            return RestfulApiRespFactory.error("用户名已被使用");
        }

        Member entity = new Member();
        BeanUtils.copyProperties(req, entity);

        entity.setCreateTime(new Date());

        jpaDao.create(entity);

        Long memberId = entity.getId();
        //会员账号信息
        CreateMemberAccountReq accountReq = new CreateMemberAccountReq();
        accountReq.setId(memberId)
                .setMoney(0)
                .setFrozenMoney(0)
                .setFrozenCoupon(0)
                .setCoupon(0)
                .setPoints(0)
                .setFrozenPoints(0)
                .setStatus(AccountStatus.AVAILABLE)
                .setVipGrade(VipGrade.M_COMMON);
        ApiResp<Long> accountRsp = accountService.create(accountReq);
        AssertThrow.assertResp(accountRsp);

        //会员安全信息
        CreateMemberSecureReq secureReq = new CreateMemberSecureReq();
        if (StringUtils.isEmpty(req.getLoginPassword())) {
            req.setLoginPassword(SnHelper.makeRandom(6));
        }
        secureReq.setId(memberId)
                .setLoginPassword(req.getLoginPassword())
                .setLoginPwdUpdateTime(new Date());
        ApiResp<Long> secureRsp = secureService.create(secureReq);
        AssertThrow.assertResp(secureRsp);

        if (req.getOpenType() != null) {
            //会员绑定信息
            CreateMemberOpenReq openReq = new CreateMemberOpenReq();
            openReq.setMemberId(memberId)
                    .setOpenId(req.getOpenId())
                    .setOpenType(req.getOpenType())
                    .setUnionId(req.getUnionId())
                    .setBindChannelCode(req.getRegSource());
            ApiResp<Long> openRsp = openService.create(openReq);
            AssertThrow.assertResp(openRsp);
        }

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditMemberReq req) {

        if (!StringUtils.isEmpty(req.getUserName())) {
            long userNameC = jpaDao.selectFrom(Member.class)
                    .eq("userName", req.getUserName())
                    .appendWhere("id != :?", req.getId())
                    .count();
            if (userNameC > 0) {
                return RestfulApiRespFactory.error("用户名已被使用");
            }
        }

        Member entity = jpaDao.find(Member.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("会员信息数据不存在");
        }

        UpdateDao<Member> updateDao = jpaDao.updateTo(Member.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新会员信息失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteMemberReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(Member.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(Member.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除会员信息");
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public MemberInfo findById(Long id) {

        QueryMemberReq queryReq = new QueryMemberReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MemberInfo> query(QueryMemberReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, Member.class, MemberInfo.class, req);

    }

    @Override
    public ApiResp<Long> checkMember(CheckMemberReq req) {
        Long memberId = jpaDao.selectFrom(Member.class)
                .select(E_Member.id)
                .appendWhere(StringUtils.hasText(req.getUserName()), E_Member.userName + " = :?", req.getUserName())
                .appendWhere(StringUtils.hasText(req.getMobilePhone()), E_Member.mobilePhone + " = :?",req.getMobilePhone())
                .appendWhere(StringUtils.hasText(req.getEmail()), E_Member.email + " = :?", req.getEmail())
                .findOne();
        return RestfulApiRespFactory.ok(memberId);
    }
}
