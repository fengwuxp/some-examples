package com.oak.messages.configuration;

import com.oak.messages.ClientMessagePusher;
import com.oak.messages.pusher.StationMessagePusher;
import com.oak.messages.pusher.xinge.XinGeConfiguration;
import com.oak.messages.pusher.xinge.XinGeMessagePusher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息推送相关配置
 *
 * @author wuxp
 */
@Configuration
public class MessagePushConfiguration {

    static final String PREFIX = "oak.push";
    static final String PREFIX_XIN_GE = "oak.push.xin-ge";

    /**
     * 信鸽推送配置
     *
     * @return
     */
    @Bean
    @ConfigurationProperties(prefix = PREFIX_XIN_GE)
    @ConditionalOnExpression(value = "${" + PREFIX_XIN_GE + ".android-access-id:null}!=null")
    public XinGeConfiguration xinGeConfiguration() {
        return new XinGeConfiguration();
    }

    /**
     * 信鸽推送者
     *
     * @param configuration
     * @return
     */
    @Bean
    @ConditionalOnBean(XinGeConfiguration.class)
    public XinGeMessagePusher xinGeMessagePusher(XinGeConfiguration configuration) {
        return new XinGeMessagePusher(configuration);
    }

    /**
     * 站内信息推送账者
     *
     * @return
     */
    @Bean
    public StationMessagePusher clientMessagePusher() {
        return new StationMessagePusher();
    }
}
