package test.com.oak.member;

import com.wuxp.api.context.ApiRequestContextFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.oak.member.constant.MemberApiContextInjectExprConstant.IP;
import static com.wuxp.api.ApiRequest.*;

@Component
public class MockApiRequestContextFactory implements ApiRequestContextFactory {

    private static Map<String, Object> FIXED_MAP = new HashMap<>();

    static {
        FIXED_MAP.put(APP_ID_KEY, "111");
        FIXED_MAP.put(APP_SECRET_KEY, "222");
        FIXED_MAP.put(NONCE_STR_KEY, RandomStringUtils.randomAlphabetic(32));
        FIXED_MAP.put(TIME_STAMP, System.currentTimeMillis());
        FIXED_MAP.put(CHANNEL_CODE, "mock");
        FIXED_MAP.put("apiSignature", RandomStringUtils.randomAlphabetic(32));
        FIXED_MAP.put(IP, "127.0.0.1");
    }

    @Override
    public Map<String, Object> factory(HttpServletRequest request) {
        return FIXED_MAP;
    }
}
