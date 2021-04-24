package com.oak.aliyun.sms.configuration;

import com.oak.aliyun.sms.SimpleMessageSender;
import com.wuxp.security.captcha.mobile.MobileCaptchaSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfiguration {


    @Bean
    @ConfigurationProperties(prefix = OakSmsProperties.PREFIX)
    public OakSmsProperties oakSmsProperties() {
        return new OakSmsProperties();
    }

    @Bean()
    @ConditionalOnMissingBean(MobileCaptchaSender.class)
    public MobileCaptchaSender mobileCaptchaSender() {
        return new SimpleMessageSender();
    }
}
