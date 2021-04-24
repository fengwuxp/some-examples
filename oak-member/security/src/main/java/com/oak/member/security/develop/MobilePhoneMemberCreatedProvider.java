package com.oak.member.security.develop;

import com.oak.member.logic.MemberCreatedException;
import com.oak.member.logic.MemberDefinition;
import com.oak.member.logic.develop.model.MemberRegisterModel;
import com.oak.member.security.develop.req.MobileRegisterReq;
import com.oak.member.services.member.req.CreateMemberReq;
import com.wuxp.security.captcha.Captcha;
import com.wuxp.security.captcha.mobile.MobileCaptcha;
import com.wuxp.security.captcha.mobile.MobileCaptchaGenerateResult;
import com.wuxp.security.captcha.mobile.MobileCaptchaType;
import com.wuxp.security.captcha.mobile.MobileCaptchaValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * 通过手机验证码注册户注册的实现
 *
 * @author wxup
 */
@Slf4j
public class MobilePhoneMemberCreatedProvider extends AbstractMemberCreateProvider {

    @Autowired
    private MobileCaptcha mobileCaptcha;


    @Override
    public MemberDefinition create(MemberRegisterModel model) throws MemberCreatedException {

        MobileRegisterReq req = (MobileRegisterReq) (model);
        MobileCaptchaGenerateResult captchaGenerateResult = MobileCaptchaGenerateResult.newInstance(
                MobileCaptchaType.REGISTER.name(), req.getMobilePhone(), -1);

        //短信验证码验证
        Captcha.CaptchaVerifyResult verifyResult = mobileCaptcha.verify(captchaGenerateResult.getKey(),
                new MobileCaptchaValue(req.getCaptcha(), MobileCaptchaType.REGISTER.name(), -1));
        if (!verifyResult.isSuccess()) {
            throw new MemberCreatedException("验证码错误");
        }
        // 判断用户是否存在
        Long memberId = memberLoader.checkByMobilePhone(req.getMobilePhone());
        if (memberId != null) {
            throw new MemberCreatedException("用户已存在");
        }
        return super.create(model);
    }

    @Override
    protected void handleCreateMemberReq(CreateMemberReq memberReq, MemberRegisterModel model) {
        MobileRegisterReq req = (MobileRegisterReq) (model);
        memberReq.setMobilePhone(req.getMobilePhone())
                .setEmail(req.getEmail());

    }


    @Override
    public boolean supports(Class<?> model) {
        return MobileRegisterReq.class.isAssignableFrom(model);
    }
}

