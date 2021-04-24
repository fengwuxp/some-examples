package test.com.oak.member.security.authentication;


import com.oak.api.enums.ClientType;
import com.oak.member.security.develop.req.MobileRegisterReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

//@RunWith(SpringRunner.class)
//@ActiveProfiles("dev")
//@SpringBootTest(classes = {OakMemberApplicationTest.class})
@Slf4j
public class MemberAuthenticationTest {


    //    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    private String mobilePhone = "1800000091";

    @Test
    public void loginTest() {

        Map<String, String> request = new HashMap<>();
        request.put("username", "");
        request.put("password", "123456");
        Map map = restTemplate.postForObject("http://localhost:8090/api/login", request, Map.class);
        log.info("登录结果 {}", map);
    }

    @Test
    public void registerTest() throws Exception {
        Map<String, String> requestCaptcha = new HashMap<>();
        Map captcha = restTemplate.getForObject("http://localhost:8090/api/captcha/mobile/register?mobile=" + mobilePhone, Map.class, requestCaptcha);
        log.info("短信 {}", captcha);
        assert captcha != null;

        // 注册
        MobileRegisterReq req = new MobileRegisterReq();
        req.setMobilePhone(mobilePhone);
        req.setPassword("123456");
        req.setClientType(ClientType.ANDROID);
        req.setCaptcha(captcha.get("data").toString()).setChannelCode(RandomStringUtils.randomAlphabetic(5));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MobileRegisterReq> entity = new HttpEntity<>(req, headers);
        Map map = restTemplate.postForObject("http://localhost:8090/api/develop/mobile", req, Map.class);
        log.info("注册结果 {}", map);
    }

}
