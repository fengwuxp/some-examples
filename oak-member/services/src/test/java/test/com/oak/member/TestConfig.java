package test.com.oak.member;

import com.wuxp.api.helper.SpringContextHolder;
import com.wuxp.api.interceptor.ApiInterceptor;
import com.wuxp.api.interceptor.TestMethodApiInterceptor;
import com.wuxp.basic.uuid.JdkUUIDGenerateStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
@EnableCaching()
public class TestConfig {


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


    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        return threadPoolTaskScheduler;
    }

    @Bean()
    public ApiInterceptor apiInterceptor() {

        return new TestMethodApiInterceptor();
    }

    /**
     * 测试时拦截方法 进行参数验证
     *
     * @return
     */
//    @Bean()
//    public BeanFactoryApiOperationSourceAdvisor beanFactoryApiOperationSourceAdvisor() {
//        BeanFactoryApiOperationSourceAdvisor advisor = new BeanFactoryApiOperationSourceAdvisor();
//        advisor.setApiOperationSource(new TestAnnotationApiOperationSource());
//        advisor.setAdvice(apiInterceptor());
//        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        return advisor;
//    }

}
