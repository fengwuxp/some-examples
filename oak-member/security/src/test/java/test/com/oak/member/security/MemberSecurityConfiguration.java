package test.com.oak.member.security;

import com.oak.member.logic.bind.MemberBinder;
import com.oak.member.logic.develop.MemberCreatedManager;
import com.oak.member.security.authentication.*;
import com.oak.member.security.bind.SimpleMemberBinder;
import com.oak.member.security.develop.DefaultMemberCreatedManager;
import com.oak.member.security.develop.MobilePhoneMemberCreatedProvider;
import com.oak.member.security.develop.OpenIdMemberCreatedProvider;
import com.wuxp.security.authenticate.LockedUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * spring security 配置
 *
 * @author wxup
 */
@Configuration
public class MemberSecurityConfiguration {


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
    public MemberBinder memberBinder() {
        return new SimpleMemberBinder();
    }

    @Bean
    public UserDetailsService oakMemberDetailsService() {
        return new OakMemberDetailsService();
    }

    @Bean
    public LockedUserDetailsService lockedUserDetailsService() {
        return new OakLockedMemberUserDetailsService();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new OakMemberAuthenticationSuccessHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new OakMemberLogoutSuccessHandler();
    }

//    @Bean
//    public RequestHeaderAuthorizationDetailsService requestHeaderAuthorizationDetailsService() {
//        return new OakMemberRequestHeaderAuthorizationDetailsService();
//    }

    @Bean
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new OakMemberSessionInformationExpiredStrategy();
    }

    @Bean
    public AuthenticationFailureHandler oakMemberAuthenticationFailureHandler() {
        return new OakMemberAuthenticationFailureHandler();
    }

//    @Bean
//    public MemberSnGenerateStrategy memberSnGenerateStrategy() {
//        return new SimpleMemberUuidGenerateStrategy();
//    }
}
