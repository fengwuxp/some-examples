package test.com.oak.api.services.app;

import com.oak.api.services.app.AppAuthService;
import com.oak.api.services.app.info.AppAuthAccountInfo;
import com.oak.api.services.app.req.QueryAppAuthAccountReq;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QueryType;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.oak.api.OakApplicationTest;

/**
 * AppAuthServiceImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>2月 28, 2020</pre>
 */
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
@Slf4j
public class AppAuthServiceImplTest {

    @Autowired
    private AppAuthService appAuthService;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: createAppAuthAccount(CreateAppAuthAccountReq req)
     */
    @Test
    public void testCreateAppAuthAccount() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: editAppAuthAccount(EditAppAuthAccountReq req)
     */
    @Test
    public void testEditAppAuthAccount() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: queryAppAuthAccount(QueryAppAuthAccountReq req)
     */
    @Test
    public void testQueryAppAuthAccount() throws Exception {
        QueryAppAuthAccountReq req = new QueryAppAuthAccountReq();
        req.setAppId("1l");
        req.setQueryType(QueryType.QUERY_NUM);
        Pagination<AppAuthAccountInfo> infoPagination = appAuthService.queryAppAuthAccount(req);
        log.debug("查询结果 {}", infoPagination);
    }

    /**
     * Method: findAppAuthAccount(FindAuthReq req)
     */
    @Test
    public void testFindAppAuthAccount() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: getAppInfo(@NotNull String appId)
     */
    @Test
    public void testGetAppInfo() throws Exception {
//TODO: Test goes here...
    }


}
