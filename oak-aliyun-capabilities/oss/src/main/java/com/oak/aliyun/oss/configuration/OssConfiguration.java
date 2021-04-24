package com.oak.aliyun.oss.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wxup
 */
@Configuration
public class OssConfiguration {


    @Bean
    @ConfigurationProperties(prefix = OssProperties.PREFIX)
    public OssProperties aliYunOssProperties() {

        return new OssProperties();
    }
}
