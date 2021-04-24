package test.com.oak.provider.member.wxjava;

import com.oak.provider.member.management.third.ThirdService;
import com.oak.provider.member.management.third.info.WxSessionInfo;
import com.oak.provider.member.management.third.req.GetWxMaSessionReq;
import com.wuxp.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.oak.provider.member.OakApplicationTest;

@RunWith(SpringRunner.class)
@Slf4j
@ActiveProfiles("test")
@SpringBootTest(classes = {OakApplicationTest.class})
public class WxjavaTest {

    @Autowired
    private ThirdService thirdService;

    @Test
    public void testWxMinapp() {
        GetWxMaSessionReq req = new GetWxMaSessionReq();
        req.setCode("1245");
        ApiResp<WxSessionInfo> resp = thirdService.getWxMaSessionInfo(req);
        System.out.println(resp);

    }
}
