package test.com.oak.message;

import com.wuxp.api.interceptor.ApiInterceptor;
import com.wuxp.api.interceptor.ApiOperationSource;
import com.wuxp.api.interceptor.TestAnnotationApiOperationSource;
import com.wuxp.api.interceptor.TestMethodApiInterceptor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching()
public class TestConfig {


    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

    @Bean
    public ApiInterceptor testApiInterceptor() {
        return new TestMethodApiInterceptor();
    }
//
//    @Bean
//    public ApiOperationSource testApiOperationSource() {
//        return new TestAnnotationApiOperationSource();
//    }



}
