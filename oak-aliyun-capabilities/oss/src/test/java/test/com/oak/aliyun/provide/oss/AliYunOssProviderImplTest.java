package test.com.oak.aliyun.provide.oss;

import com.oak.aliyun.oss.configuration.OssProperties;
import com.oak.aliyun.oss.provide.AliYunOssProvider;
import com.oak.aliyun.oss.provide.info.AliYunStsTokenInfo;
import com.oak.aliyun.oss.provide.req.GetAliYunOssStsTokenReq;
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
import test.com.oak.aliyun.OakApplicationTest;

/**
 * AliYunOssProviderImpl Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>5月 22, 2020</pre>
 */
@ActiveProfiles("dev")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {OakApplicationTest.class})
@Slf4j
public class AliYunOssProviderImplTest {


    @Autowired
    private AliYunOssProvider aliYunOssProvider;

    @Autowired
    private OssProperties aliYunOssProperties;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: getAliYunStsToken(GetAliYunOssStsTokenReq req)
     */
    @Test
    public void testGetAliYunStsToken() throws Exception {

        GetAliYunOssStsTokenReq req = new GetAliYunOssStsTokenReq();
        req.setOperatorId(null);
        OssProperties.StsToken stsToken = aliYunOssProperties.getSts();
        req.setAccessKeyId(stsToken.getAccessKey());
        req.setAccessKeySecret(stsToken.getSecretKey());
        req.setRoleArn(stsToken.getRoleArn());
        ApiResp<AliYunStsTokenInfo> resp = aliYunOssProvider.getAliYunStsToken(req);
        Assert.assertTrue(resp.getErrorMessage(), resp.isSuccess());
        log.info("{}", resp.getData());
    }

    /**
     * Method: afterPropertiesSet()
     */
    @Test
    public void testAfterPropertiesSet() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: setBeanFactory(BeanFactory beanFactory)
     */
    @Test
    public void testSetBeanFactory() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: setCacheManager(CacheManager cacheManager)
     */
    @Test
    public void testSetCacheManager() throws Exception {
//TODO: Test goes here...
    }


    /**
     * Method: getStsToken(GetAliYunOssStsTokenReq req, String roleSessionName, Long expirationTimes)
     */
    @Test
    public void testGetStsToken() throws Exception {
//TODO: Test goes here...
/*
try {
   Method method = AliYunOssProviderImpl.getClass().getMethod("getStsToken", GetAliYunOssStsTokenReq.class, String.class, Long.class);
   method.setAccessible(true);
   method.invoke(<Object>, <Parameters>);
} catch(NoSuchMethodException e) {
} catch(IllegalAccessException e) {
} catch(InvocationTargetException e) {
}
*/
    }

}
