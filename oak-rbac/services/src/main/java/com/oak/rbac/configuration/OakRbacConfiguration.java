package com.oak.rbac.configuration;

import com.oak.rbac.authority.OakRequestUrlResourceProvider;
import com.wuxp.security.authority.AntRequestUrlResourceProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wxup
 */
@Configuration
public class OakRbacConfiguration {


    @ConfigurationProperties(prefix = OakRbacProperties.PREFIX)
    @Bean
    public OakRbacProperties oakRbacProperties() {
        return new OakRbacProperties();
    }

    @Bean
    @ConditionalOnMissingBean(AntRequestUrlResourceProvider.class)
    public AntRequestUrlResourceProvider antRequestUrlResourceProvider() {
        return new OakRequestUrlResourceProvider();
    }
}
