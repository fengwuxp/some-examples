package test.com.oak.provider.member;

import com.fengwuxp.miniapp.multiple.WeChatMaConfigProvider;
import com.fengwuxp.mp.multiple.WeChatMpConfigStorageProvider;
import com.fengwuxp.wechat.multiple.WeChatAppIdProvider;
import com.wuxp.api.helper.SpringContextHolder;
import com.wuxp.api.interceptor.BeanFactoryApiOperationSourceAdvisor;
import com.wuxp.api.interceptor.TestAnnotationApiOperationSource;
import com.wuxp.api.interceptor.TestMethodApiInterceptor;
import com.wuxp.basic.uuid.JdkUUIDGenerateStrategy;
import com.wuxp.basic.uuid.UUIDGenerateStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.Ordered;
import org.springframework.format.support.FormattingConversionService;

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
    public UUIDGenerateStrategy uuidGenerateStrategy() {
        return new JdkUUIDGenerateStrategy();
    }


    @Bean()
    public TestMethodApiInterceptor testMethodApiInterceptor() {

        return new TestMethodApiInterceptor();
    }

    /**
     * 测试时拦截方法 进行参数验证
     *
     * @return
     */
    @Bean()
    public BeanFactoryApiOperationSourceAdvisor testBeanFactoryApiOperationSourceAdvisor() {
        BeanFactoryApiOperationSourceAdvisor advisor = new BeanFactoryApiOperationSourceAdvisor();
        advisor.setApiOperationSource(new TestAnnotationApiOperationSource());
        advisor.setAdvice(testMethodApiInterceptor());
        advisor.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return advisor;
    }


    @Bean
    public WeChatAppIdProvider mockWeChatAppIdProvider() {
        return new MockWeChatAppIdProvider();
    }

    @Bean
    public WeChatMpConfigStorageProvider weChatMpConfigStorageProvider() {

        return new MockWeChatMpConfigStorageProvider();
    }

    @Bean
    public WeChatMaConfigProvider weChatMiniAppConfigStorageProvider() {
        return new MockWeChatMiniAppConfigStorageProvider();
    }

}
