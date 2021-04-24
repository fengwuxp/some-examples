package com.oak.member.security.develop;

import com.oak.api.services.client.ClientChannelService;
import com.oak.member.enums.MemberLogType;
import com.oak.member.enums.MemberVerifyStatus;
import com.oak.member.logic.MemberChecker;
import com.oak.member.logic.MemberCreatedException;
import com.oak.member.logic.MemberDefinition;
import com.oak.member.logic.develop.MemberCreatedProvider;
import com.oak.member.logic.develop.model.MemberRegisterModel;
import com.oak.member.logic.uuid.MemberInviteCodeGenerateStrategy;
import com.oak.member.logic.uuid.MemberSnGenerateStrategy;
import com.oak.member.logic.uuid.MemberUserNameGenerateStrategy;
import com.oak.member.security.develop.req.BaseRegisterReq;
import com.oak.member.services.account.MemberAccountService;
import com.oak.member.services.account.req.CreateMemberAccountReq;
import com.oak.member.services.member.MemberService;
import com.oak.member.services.member.req.CreateMemberReq;
import com.oak.member.services.memberlog.MemberLogService;
import com.oak.member.services.memberlog.req.CreateMemberLogReq;
import com.oak.member.services.secure.MemberSecureService;
import com.oak.member.services.secure.req.CreateMemberSecureReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.basic.utils.IpAddressUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author wxup
 */
@Slf4j
public abstract class AbstractMemberCreateProvider implements MemberCreatedProvider {


    @Autowired
    protected MemberLogService memberLogService;

    @Autowired
    protected MemberAccountService memberAccountService;

    @Autowired
    protected MemberSecureService memberSecureService;

    @Autowired
    protected MemberSnGenerateStrategy memberSnGenerateStrategy;

    @Autowired
    protected MemberInviteCodeGenerateStrategy memberInviteCodeGenerateStrategy;

    @Autowired
    protected MemberUserNameGenerateStrategy memberUserNameGenerateStrategy;

    @Autowired
    protected ClientChannelService clientChannelService;

    @Autowired
    protected MemberService memberService;

    @Autowired
    protected MemberChecker memberLoader;


    @Override
    public MemberDefinition create(MemberRegisterModel model) throws MemberCreatedException {

        return this.createMember(model);
    }

    protected abstract void handleCreateMemberReq(CreateMemberReq memberReq, MemberRegisterModel model);


    protected void handleCreateMemberSecureReq(CreateMemberSecureReq req, MemberRegisterModel model) {
        req.setLoginPassword(model.getPassword());
        this.generateDefaultPassword(req, model);
    }

    protected MemberDefinition createMember(MemberRegisterModel model) {
        BaseRegisterReq req = (BaseRegisterReq) model;
        CreateMemberReq memberReq = new CreateMemberReq();
        memberReq.setVerify(MemberVerifyStatus.PENDING)
                .setChannelCode(req.getChannelCode())
                .setVerify(MemberVerifyStatus.APPROVED)
                .setNo(memberSnGenerateStrategy.sn(model))
                .setName(model.getName())
                .setInviteCode(memberInviteCodeGenerateStrategy.inviteCode(model))
                .setAreaId(req.getAreaId())
                .setAreaName(req.getAreaName())
                .setBirthday(req.getBirthday())
                .setAvatarUrl(req.getAvatarUrl())
                .setNickName(req.getNickName())
                .setUserName(model.getUserName());
        this.handleCreateMemberReq(memberReq, model);
        if (!StringUtils.hasText(memberReq.getUserName())) {
            memberReq.setUserName(memberUserNameGenerateStrategy.userName(model));
        }
        memberReq.setMobileAuth(StringUtils.hasText(memberReq.getMobilePhone()));
        // 创建用户
        ApiResp<Long> resp = this.memberService.create(memberReq);
        AssertThrow.assertResp(resp);
        Long memberId = resp.getData();

        createAccount(memberId);
        createSecure(model, memberId);
        writeRegisterLog(req, memberReq, memberId);

        return memberService.findById(memberId);
    }



    protected void generateDefaultPassword(CreateMemberSecureReq req, MemberRegisterModel model) {
        if (!StringUtils.hasText(req.getLoginPassword())) {
            req.setLoginPassword(RandomStringUtils.randomPrint(16));
        }
        if (!StringUtils.hasText(req.getPayPassword())) {
            req.setPayPassword(RandomStringUtils.randomPrint(16));
        }
    }

    private void createSecure(MemberRegisterModel model, Long memberId) {
        // 创建会员安全信息(密码等数据)
        CreateMemberSecureReq secureReq = new CreateMemberSecureReq();
        this.handleCreateMemberSecureReq(secureReq, model);
        secureReq.setId(memberId);
        ApiResp<Long> secureRsp = memberSecureService.create(secureReq);
        AssertThrow.assertResp(secureRsp);
    }

    private void createAccount(Long memberId) {
        //会员账号信息（余额等）
        CreateMemberAccountReq accountReq = new CreateMemberAccountReq();
        accountReq.setMemberId(memberId);
        ApiResp<Long> accountRsp = memberAccountService.create(accountReq);
        AssertThrow.assertResp(accountRsp);
    }


    private void writeRegisterLog(BaseRegisterReq req, CreateMemberReq memberReq, Long memberId) {
        //记录日志
        CreateMemberLogReq createMemberLogReq = new CreateMemberLogReq();
        createMemberLogReq.setMemberId(memberId);
        createMemberLogReq.setType(MemberLogType.REGISTER.name());
        createMemberLogReq.setOperator(memberReq.getUserName());
        createMemberLogReq.setShowName(!StringUtils.isEmpty(req.getNickName()) ? req.getNickName() : memberReq.getUserName());
        createMemberLogReq.setDescription("注册成功");
        createMemberLogReq.setOperatingTime(new Date());
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        if (httpServletRequest != null) {
            createMemberLogReq.setIp(IpAddressUtils.try2GetUserRealIPAddr(httpServletRequest));
        }
        ApiResp<Long> resp = memberLogService.create(createMemberLogReq);
        AssertThrow.assertResp(resp);
    }

    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        if (requestAttributes == null) {
            return null;
        }
        return ((ServletRequestAttributes) requestAttributes).getRequest();
    }
}
