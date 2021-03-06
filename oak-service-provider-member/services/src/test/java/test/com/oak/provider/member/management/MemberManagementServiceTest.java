package test.com.oak.provider.member.management;

import com.github.javafaker.Faker;
import com.levin.commons.dao.JpaDao;
import com.oak.api.services.app.AppAuthService;
import com.oak.provider.member.enums.Gender;
import com.oak.provider.member.enums.LoginModel;
import com.oak.provider.member.management.member.MemberManagementService;
import com.oak.provider.member.management.member.info.AccountInfo;
import com.oak.provider.member.management.member.info.MemberLoginInfo;
import com.oak.provider.member.management.member.req.*;
import com.oak.provider.member.services.member.info.MemberInfo;
import com.wuxp.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.oak.provider.member.OakApplicationTest;

/**
 * @author laiy
 * create at 2020-02-10 10:42
 * @Description
 */
@RunWith(SpringRunner.class)
@Slf4j
@ActiveProfiles("dev")
@SpringBootTest(classes = {OakApplicationTest.class})
public class MemberManagementServiceTest {

    @Autowired
    private MemberManagementService memberManagementService;

    @Autowired
    private JpaDao jpaDao;

    private Faker faker = new Faker();

    @Autowired
    private AppAuthService appAuthService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void test()  throws Exception {
        //QueryAppAuthAccountReq queryAuthAccountEvt = new QueryAppAuthAccountReq("agent");
        //queryAuthAccountEvt.setQueryType(QueryType.QUERY_NUM);
        //System.out.println(appAuthService.queryAppAuthAccount(queryAuthAccountEvt));
        testRegister();
        login();
    }

    @Test
    public void testRegister() throws Exception {

        RegisterMemberReq req = new RegisterMemberReq();
        req.setAddress(faker.address().streetAddress())
                .setNotPassword(true)
                .setMobilePhone("13107685221")
                .setMobileAuth(false)
                .setAreaId("350102")
                .setNickName("??????")
                .setUserName("??????")
                .setLoginPassword("543210")
                .setAreaName("?????????")
                .setAvatarUrl("ster");
        ApiResp<Long> resp = memberManagementService.register(req);

        Assert.assertTrue(resp.getErrorMessage(), resp.isSuccess());
        System.out.println(resp);
    }

    @Test
    public void testRegisterFromWxMa() throws Exception {

        RegisterMemberFromWxMaReq req = new RegisterMemberFromWxMaReq();
        req.setAddress(faker.address().streetAddress())
                .setCode("0130jIF82biEnM009cH82sjtF820jIFO")
                .setEncryptedData("UttQML9Oc6asOeHPNMyjgvdZLEN2Gvk7HDtMrZUkeJrjb1E5n+KQEnoeptYitvQrQZeckck3ScpYN38zFYLAz6weYuMTxFfyIP7sujNMCTboXEi4OzJQKNS/mTOomjv7KYfnsEWCB9s4uYKSOFq+j0NpJTdPXnuRSNkVQOBLFp3aMnlzvF0cQ165Tnc/bHuiB6oOF5JQsBj0fiKU3hA9Xj227x4M93QkbJq0QhSK17P2eNRbEbRzMaMCpm+ejJTQ+nBfnbKCnF92iXEEN4R6hZUI2DgHXw5zwzYEzrXxIFNmRkHKqo+5vw5302oPYX8qfAO3Z6PgfMKH/rqfQROpJu+K9Fs/GUK27KmcXxESmamPLJOT9Vc/iwhyVQevQt1ZuvOS9GxXmUixkKOPb1uh/xomfvspGNDOacA55/Hbm3v6or3C46ygrG/84xORSvkQMczoHn+IT8UOn3V95q2X3g==")
                .setIvStr("qHLxwglbPqVbuzWuS2SE0g==")
                .setNickName("??????")
                .setAvatarUrl("??????")
                .setAreaId("350102")
                .setGender(Gender.SECRET)
                .setUserEncryptedData("")
                .setUserIvStr("qHLxwglbPqVbuzWuS2SE0g==")
                .setAreaName("?????????")
                .setAvatarUrl("ster");
        ApiResp<Long> resp = memberManagementService.registerFromWxMa(req);

        Assert.assertTrue(resp.getErrorMessage(), resp.isSuccess());

    }

    @Test
    public void testRegisterFromWx() throws Exception {

        RegisterMemberFromWxReq req = new RegisterMemberFromWxReq();
        req.setAddress(faker.address().streetAddress())
                .setAreaId("350102")
                .setAreaName("?????????");
        ApiResp<MemberInfo> resp = memberManagementService.registerFromWx(req);

        Assert.assertTrue(resp.getErrorMessage(), resp.isSuccess());

    }

    @Test
    public void getMemberAccountInfo() throws Exception {

        MemberAccountInfoReq req = new MemberAccountInfoReq();
        req.setId(1L);
        ApiResp<AccountInfo> resp = memberManagementService.getMemberInfo(req);

        Assert.assertTrue(resp.getErrorMessage(), resp.isSuccess());
        System.out.println(resp.toString());
    }

    public void login() {
        MemberLoginReq req = new MemberLoginReq();
        req.setLoginModel(LoginModel.PASSWORD);
        req.setMobilePhone("13107685221");
        req.setPassword("543210");
        ApiResp<MemberLoginInfo> resp = memberManagementService.login(req);
        Assert.assertTrue(resp.getErrorMessage(), resp.isSuccess());
        System.out.println(resp.toString());
    }

    @Test
    public void uniLogin() {
    //    loginModel: OPEN_ID
        //nickName: L???
        //avatarUrl: https://wx.qlogo.cn/mmopen/vi_32/w4WA3LMMDUw6RfFzQXNEpx8aWCa7og976ylCTJqxhrw8tGnproHCz21LzDRUFts0AjjW83rqrG4ua21NovH8Dg/132
        //openType: WEIXIN_MA
        //openId: o6IWI5AJnVA-WAnzzCx1dYjPzIxo
//
//        UniloginReq uniloginReq = new UniloginReq();
//        uniloginReq.setLoginModel(LoginModel.OPEN_ID)
//                .setNickName("L???")
//                .setAvatarUrl("https://wx.qlogo.cn/mmopen/vi_32/w4WA3LMMDUw6RfFzQXNEpx8aWCa7og976ylCTJqxhrw8tGnproHCz21LzDRUFts0AjjW83rqrG4ua21NovH8Dg/132")
//                .setOpenId("o6IWI5AJnVA-WAnzzCx1dYjPzIxo")
//                .setOpenType(OpenType.WEIXIN_MA).setChannelCode("0000000");
//        System.out.println(memberManagementService.unilogin(uniloginReq));
    }

}
