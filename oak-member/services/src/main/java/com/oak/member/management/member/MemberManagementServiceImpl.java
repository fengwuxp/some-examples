package com.oak.member.management.member;

import com.levin.commons.dao.JpaDao;
import com.oak.api.services.log.req.EditOperationalLogReq;
import com.oak.member.entities.E_Member;
import com.oak.member.entities.Member;
import com.oak.member.logic.MemberCreatedException;
import com.oak.member.management.member.req.ChangeLoginPasswordReq;
import com.oak.member.management.member.req.ForgetPasswordReq;
import com.oak.member.services.secure.MemberSecureService;
import com.oak.member.services.secure.info.MemberSecureInfo;
import com.oak.member.services.secure.req.EditMemberSecureReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.captcha.Captcha;
import com.wuxp.security.captcha.mobile.MobileCaptcha;
import com.wuxp.security.captcha.mobile.MobileCaptchaGenerateResult;
import com.wuxp.security.captcha.mobile.MobileCaptchaType;
import com.wuxp.security.captcha.mobile.MobileCaptchaValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author wuxp
 */
@Service
@Slf4j
public class MemberManagementServiceImpl implements MemberManagementService {
    @Autowired
    private MemberSecureService memberSecureService;

    @Autowired
    private MobileCaptcha mobileCaptcha;

    @Autowired
    private JpaDao jpaDao;

    /**
     * 忘记密码
     *
     * @param req
     * @return
     */
    @Override
    public ApiResp<Void> forgetPassword(ForgetPasswordReq req) {
        MobileCaptchaGenerateResult captchaGenerateResult = MobileCaptchaGenerateResult.newInstance(
                MobileCaptchaType.REST_PASSWORD.name(), req.getMobilePhone(), -1);
        //短信验证码验证
        Captcha.CaptchaVerifyResult verifyResult = mobileCaptcha.verify(captchaGenerateResult.getKey(),
                new MobileCaptchaValue(req.getCaptcha(), MobileCaptchaType.REGISTER.name(), -1));
        if (!verifyResult.isSuccess()) {
            throw new MemberCreatedException("验证码错误");
        }

        //根据手机号查找用户账号信息
        Member member = jpaDao.selectFrom(Member.class)
                .eq(E_Member.mobilePhone, req.getMobilePhone())
                .findOne(Member.class);
        if (member == null) {
            return RestfulApiRespFactory.error("手机号码有误");
        }
        EditMemberSecureReq editMemberSecureReq = new EditMemberSecureReq();
        editMemberSecureReq.setId(member.getId());
        editMemberSecureReq.setLoginPassword(req.getNewPassword());
        return memberSecureService.edit(editMemberSecureReq);
    }

    @Override
    public ApiResp<Void> changeLoginPassword(ChangeLoginPasswordReq req) {

        MemberSecureInfo secureInfo = memberSecureService.findById(req.getMemberId());

        //密码校验
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(secureInfo.getLoginCryptoSalt());
        if (!passwordEncoder.matches(req.getOldPassword(), secureInfo.getLoginPassword())) {
            return RestfulApiRespFactory.error("旧密码错误");
        }

        //修改登陆密码
        EditMemberSecureReq editReq = new EditMemberSecureReq();
        editReq.setId(req.getMemberId())
                .setLoginPassword(req.getNewPassword());

        return memberSecureService.edit(editReq);
    }
}

