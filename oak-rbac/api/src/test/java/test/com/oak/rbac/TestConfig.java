package test.com.oak.rbac;

import com.wuxp.api.helper.SpringContextHolder;
import com.wuxp.basic.uuid.JdkUUIDGenerateStrategy;
import com.wuxp.basic.uuid.UUIDGenerateStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.simple.SimpleJdbcCallOperations;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.orm.jpa.JpaTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

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
    public SimpleJdbcInsertOperations simpleJdbcInsertOperations(DataSource dataSource) {
        return new SimpleJdbcInsert(dataSource);
    }

    @Bean
    @ConditionalOnMissingBean
    public SimpleJdbcCallOperations simpleJdbcCallOperations(DataSource dataSource) {
        return new SimpleJdbcCall(dataSource);
    }


    @Bean
    @ConditionalOnMissingBean
    public JpaTransactionManager transactionManager(EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    @Bean
    @ConditionalOnMissingBean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }

    @Bean
    @ConditionalOnMissingBean
    public UUIDGenerateStrategy uuidGenerateStrategy() {
        return new JdkUUIDGenerateStrategy();
    }
}
