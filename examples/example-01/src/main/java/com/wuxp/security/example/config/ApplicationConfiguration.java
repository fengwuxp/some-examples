package com.wuxp.security.example.config;


import com.wuxp.api.context.ApiRequestContextFactory;
import com.wuxp.api.signature.AppInfoStore;
import com.wuxp.basic.date.TimestampDateFormatter;
import com.wuxp.basic.uuid.JdkUUIDGenerateStrategy;
import com.wuxp.basic.uuid.UUIDGenerateStrategy;
import com.wuxp.security.example.advisor.OrderedAbstractBeanFactoryAwareAdvisingPostProcessor;
import com.wuxp.security.example.context.MockApiRequestContextFactory;
import com.wuxp.security.example.signature.MockAppInfoStore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.format.FormatterRegistry;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManagerFactory;

/**
 * @author wxup
 */
@Configuration
@ImportResource("classpath:/applicationContext-*.xml")
@EnableCaching
public class ApplicationConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new TimestampDateFormatter());
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    @ConditionalOnMissingBean
    public JpaTransactionManager transactionManager(EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    @Bean
    public ApiRequestContextFactory requestContextFactory() {
        return new MockApiRequestContextFactory();
    }

    @Bean
    public CacheManager cacheManager() {
        return new CaffeineCacheManager();
    }

    @Bean
    public UUIDGenerateStrategy uuidGenerateStrategy() {
        return new JdkUUIDGenerateStrategy();
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        return threadPoolTaskScheduler;
    }


    @Bean
    public OrderedAbstractBeanFactoryAwareAdvisingPostProcessor orderedAbstractBeanFactoryAwareAdvisingPostProcessor() {
        return new OrderedAbstractBeanFactoryAwareAdvisingPostProcessor();
    }

    @Bean
    @ConditionalOnMissingBean(AppInfoStore.class)
    public AppInfoStore mockAppInfoStore() {
        return new MockAppInfoStore();
    }

}
