package com.oak.member.configuration;

import com.oak.member.logic.bind.MemberBinder;
import com.oak.member.logic.develop.MemberCreatedManager;
import com.oak.member.logic.uuid.MemberSnGenerateStrategy;
import com.oak.member.security.authentication.*;
import com.oak.member.security.bind.SimpleMemberBinder;
import com.oak.member.security.develop.DefaultMemberCreatedManager;
import com.oak.member.security.develop.MobilePhoneMemberCreatedProvider;
import com.oak.member.security.develop.OpenIdMemberCreatedProvider;
import com.oak.member.security.develop.SimpleMemberUuidGenerateStrategy;
import com.wuxp.security.authenticate.LockedUserDetailsService;
import com.wuxp.security.authenticate.session.AuthenticateSessionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @author wxup
 */
@Configuration
public class OakMemberSecurityConfiguration {


    @Bean
    @ConditionalOnMissingBean(MemberSnGenerateStrategy.class)
    public MemberSnGenerateStrategy memberSnGenerateStrategy() {
        return new SimpleMemberUuidGenerateStrategy();
    }

    @Bean
    public MemberCreatedManager memberCreatedManager() {
        return new DefaultMemberCreatedManager();
    }

    @Bean
    public OpenIdMemberCreatedProvider openIdMemberCreatedProvider() {
        return new OpenIdMemberCreatedProvider();
    }

    @Bean
    public MobilePhoneMemberCreatedProvider mobilePhoneMemberCreatedProvider() {
        return new MobilePhoneMemberCreatedProvider();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticationSuccessHandler.class)
    public OakMemberAuthenticationSuccessHandler oakAuthenticationSuccessHandler() {
        return new OakMemberAuthenticationSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new OakMemberLogoutSuccessHandler();
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new OakMemberDetailsService();
    }

    @Bean
    @ConditionalOnMissingBean(LockedUserDetailsService.class)
    public LockedUserDetailsService lockedUserDetailsService() {
        return new OakLockedMemberUserDetailsService();
    }


    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new OakMemberSessionInformationExpiredStrategy();
    }

    @Bean
    @ConditionalOnMissingBean(MemberBinder.class)
    public MemberBinder memberBinder() {
        return new SimpleMemberBinder();
    }


    @Bean
    @ConditionalOnMissingBean(AuthenticationFailureHandler.class)
    public AuthenticationFailureHandler oakMemberAuthenticationFailureHandler() {
        return new OakMemberAuthenticationFailureHandler();
    }

    @Bean
    @ConditionalOnMissingBean(AuthenticateSessionManager.class)
    public AuthenticateSessionManager<OakMemberDetails> authenticateSessionManager() {
        return new OakMemberAuthenticateSessionManager();
    }


}
