package com.wuxp.security.example.signature;

import com.wuxp.api.signature.AppInfoStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;

import javax.validation.constraints.NotNull;

@Slf4j
public class MockAppInfoStore implements AppInfoStore {


    private static final String appSecret = "com.wuxp.security.example";

    @Override
    @Cacheable(key = "#appId", value = APP_STORE_CACHE_NAME)
    public MockAppInfo getAppInfo(@NotNull String appId) {
        return new MockAppInfo(appId, appSecret,"mock");
    }
}
