package test.com.oak.member.business;

import com.oak.member.business.deposit.DefaultTransferManager;
import com.oak.member.security.develop.SimpleMemberUuidGenerateStrategy;
import com.wuxp.api.helper.SpringContextHolder;
import com.wuxp.api.interceptor.ApiInterceptor;
import com.wuxp.api.interceptor.ApiOperationSource;
import com.wuxp.api.interceptor.TestAnnotationApiOperationSource;
import com.wuxp.api.interceptor.TestMethodApiInterceptor;
import com.wuxp.basic.uuid.JdkUUIDGenerateStrategy;
import com.wuxp.basic.uuid.sn.JdkSnGenerateStrategy;
import com.wuxp.basic.uuid.sn.SnGenerateStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableCaching()
public class TestConfig {

    @Bean
    public ApiInterceptor testApiInterceptor() {
        return new TestMethodApiInterceptor();
    }

    @Bean
    public ApiOperationSource testApiOperationSource() {
        return new TestAnnotationApiOperationSource();
    }

    /**
     * 默认的提现转账审核
     * @return
     */
    @Bean
    public DefaultTransferManager defaultTransferManager(){
        return new DefaultTransferManager();
    }


    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }


    @Bean
    @ConditionalOnMissingBean
    public FormattingConversionService formattingConversionService() {
        return new FormattingConversionService();
    }

    @Bean
    @ConditionalOnMissingBean
    public JdkUUIDGenerateStrategy uuidGenerateStrategy() {
        return new JdkUUIDGenerateStrategy();
    }

    @Bean()
    public ApiInterceptor apiInterceptor() {

        return new TestMethodApiInterceptor();
    }

    @Bean
    public SimpleMemberUuidGenerateStrategy simpleMemberUuidGenerateStrategy() {
        return new SimpleMemberUuidGenerateStrategy();
    }

    @Bean
    public SnGenerateStrategy jdkSnGenerateStrategy(){
        return new JdkSnGenerateStrategy();
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        return threadPoolTaskScheduler;
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
