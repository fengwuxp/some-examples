package test.com.oak.member.security.develop;

import com.oak.member.logic.MemberDefinition;
import com.oak.member.security.develop.MobilePhoneMemberCreatedProvider;
import com.oak.member.security.develop.req.MobileRegisterReq;
import com.oak.member.services.account.MemberAccountService;
import com.oak.member.services.account.info.MemberAccountInfo;
import com.oak.member.services.account.req.ChangeAccountReq;
import com.wuxp.api.ApiResp;
import com.wuxp.security.captcha.mobile.MobileCaptcha;
import com.wuxp.security.captcha.mobile.MobileCaptchaGenerateResult;
import com.wuxp.security.captcha.mobile.MobileCaptchaType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import test.com.oak.member.security.OakMemberApplicationTest;


@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakMemberApplicationTest.class})
@Slf4j
public class MobilePhoneMemberCreatedProviderTest {


    @Autowired
    private MobilePhoneMemberCreatedProvider memberCreatedProvider;


    @Autowired
    private MobileCaptcha mobileCaptcha;

    @Autowired
    private MemberAccountService accountService;

    @Test
    public void testCreate() {

        createMember();
    }


    @Test
    @Transactional
    public void testChangeAccount() {
        MemberDefinition member = this.createMember();
        ChangeAccountReq req = new ChangeAccountReq();
        req.setId(member.getId())
                .setMoney(100)
                .setPoints(5)
                .setCoupon(22)
                .setBusinessType("MALL")
                .setOperator(member.getUserName());
        ApiResp<Void> resp = accountService.changeAccount(req);
        Assert.assertTrue(resp.getErrorMessage(), resp.isSuccess());
        MemberAccountInfo accountInfo = accountService.findById(member.getId());
        Assert.assertEquals(100L, accountInfo.getAvailableMoney().longValue());
        Assert.assertEquals(5L, accountInfo.getAvailablePoints().longValue());
        Assert.assertEquals(22L, accountInfo.getAvailableCoupon().longValue());

        req.setId(member.getId())
                .setMoney(100)
                .setPoints(5)
                .setCoupon(22)
                .setBusinessType("MALL")
                .setOperator(member.getUserName());
        resp = accountService.changeAccount(req);
        Assert.assertTrue(resp.getErrorMessage(), resp.isSuccess());
        accountInfo = accountService.findById(member.getId());
        log.info("{}", accountInfo);
    }

    private MemberDefinition createMember() {
        String mobilePhone = "18099929993";
        MobileCaptchaGenerateResult generateResult = mobileCaptcha.generate(MobileCaptchaType.REGISTER.name(), mobilePhone);

        MobileRegisterReq model = new MobileRegisterReq();
        model.setCaptcha(generateResult.getValue().getValue())
                .setMobilePhone(mobilePhone)
                .setPassword("123456")
                .setChannelCode("app");

        MemberDefinition memberDefinition = memberCreatedProvider.create(model);
        Assert.assertNotNull(memberDefinition);
        return memberDefinition;
    }

}