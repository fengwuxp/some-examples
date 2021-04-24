package test.com.oak.member.security.authentication;

import com.github.javafaker.Faker;
import com.oak.api.enums.ClientType;
import com.oak.member.enums.Gender;
import com.oak.member.logic.MemberDefinition;
import com.oak.member.logic.develop.MemberCreatedManager;
import com.oak.member.security.develop.req.MobileRegisterReq;
import com.wuxp.security.captcha.mobile.MobileCaptcha;
import com.wuxp.security.captcha.mobile.MobileCaptchaGenerateResult;
import com.wuxp.security.captcha.mobile.MobileCaptchaType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.oak.member.security.OakMemberApplicationTest;

@RunWith(SpringRunner.class)
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(classes = {OakMemberApplicationTest.class})
public class AuthenticationTest {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MemberCreatedManager memberCreatedManager;

    @Autowired
    private MobileCaptcha mobileCaptcha;

    private Faker faker = new Faker();


    @Test
    public void testUsernamePassword() {
        String mobilePhone = "1800000001";
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    mobilePhone,
                    "123456"
            ));
            Assert.assertNotNull(authenticate);
            log.info("登录成功 :{}", authenticate);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            if (e instanceof UsernameNotFoundException) {
                log.info("注册用户");
                MobileRegisterReq model = new MobileRegisterReq();
                MobileCaptchaGenerateResult result = mobileCaptcha.generate(MobileCaptchaType.REGISTER.name(), mobilePhone);
                model.setMobilePhone(mobilePhone)
                        .setCaptcha(result.getValue().getValue())
                        .setPassword("123456")
                        .setNickName(faker.name().name())
                        .setClientType(ClientType.DEFAULT)
                        .setGender(Gender.MAN)
                        .setChannelCode("app");
                MemberDefinition memberDefinition = memberCreatedManager.create(model);
                Assert.assertNotNull(memberDefinition);
                log.info("注册用户成功 :{}", memberDefinition);
                Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        mobilePhone,
                        "123456"
                ));
                Assert.assertNotNull(authenticate);
                log.info("登录成功 :{}", authenticate);
            }


        }
    }
}
