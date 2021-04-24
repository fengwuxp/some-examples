package com.oak.rbac.configuration;

import com.oak.rbac.log.RbacApiLogRecorder;
import com.oak.rbac.security.*;
import com.wuxp.api.log.ApiLogRecorder;
import com.wuxp.security.authenticate.LockedUserDetailsService;
import com.wuxp.security.authenticate.session.AuthenticateSessionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @author wxup
 */
@Configuration
public class OakRbacSecurityConfiguration {


    @Bean
    @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
    public AuthenticationSuccessHandler oakAuthenticationSuccessHandler() {
        return new OakAuthenticationSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new OakLogoutSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new OakUserDetailsService();
    }

    @Bean
    @ConditionalOnMissingBean(LockedUserDetailsService.class)
    public LockedUserDetailsService lockedUserDetailsService() {
        return new OakLockedUserDetailsService();
    }

//    @Bean
//    @ConditionalOnMissingBean(RequestHeaderAuthorizationDetailsService.class)
//    public RequestHeaderAuthorizationDetailsService requestHeaderAuthorizationDetailsService() {
//        return new OakRequestHeaderAuthorizationDetailsService();
//    }

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {

        return new OakSessionInformationExpiredStrategy();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticateSessionManager.class)
    public AuthenticateSessionManager<OakUser> adminAuthenticateSessionManager() {
        return new OakAdminAuthenticateSessionManager();
    }

    @Bean
    @ConditionalOnMissingBean(ApiLogRecorder.class)
    public ApiLogRecorder apiLogRecorder() {
        return new RbacApiLogRecorder();
    }
}
