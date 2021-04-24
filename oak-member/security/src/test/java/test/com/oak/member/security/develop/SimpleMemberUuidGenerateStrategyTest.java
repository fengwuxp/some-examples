package test.com.oak.member.security.develop;

import com.oak.member.security.develop.SimpleMemberUuidGenerateStrategy;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * SimpleMemberUuidGenerateStrategy Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>6月 4, 2020</pre>
 */
@Slf4j
public class SimpleMemberUuidGenerateStrategyTest {


    private SimpleMemberUuidGenerateStrategy simpleMemberUuidGenerateStrategy = new SimpleMemberUuidGenerateStrategy();

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }

    /**
     * Method: inviteCode(MemberRegisterModel memberId)
     */
    @Test
    public void testInviteCode() throws Exception {
//TODO: Test goes here...
    }

    /**
     * Method: sn(MemberRegisterModel model)
     */
    @Test
    public void testSn() throws Exception {
        for (int i = 0; i < 100; i++) {
            log.info("sn = {}", simpleMemberUuidGenerateStrategy.sn(null));
        }
    }

    /**
     * Method: userName(MemberRegisterModel model)
     */
    @Test
    public void testUserName() throws Exception {
        for (int i = 0; i < 100; i++) {
            log.info("username = {}", simpleMemberUuidGenerateStrategy.userName(null));
        }
    }


}
