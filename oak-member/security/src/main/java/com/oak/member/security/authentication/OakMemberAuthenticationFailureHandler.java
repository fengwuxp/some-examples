package com.oak.member.security.authentication;

import com.oak.member.logic.authentication.MemberAuthenticationFailureHandler;
import com.wuxp.security.authenticate.form.FormAuthenticationFailureHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 用户登录失败处理
 *
 * @author wxup
 */
@Slf4j
@Setter
public class OakMemberAuthenticationFailureHandler extends FormAuthenticationFailureHandler implements AuthenticationFailureHandler, ApplicationContextAware, InitializingBean {


    private ApplicationContext applicationContext;

    private List<MemberAuthenticationFailureHandler> authenticationFailureHandlers;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        for (MemberAuthenticationFailureHandler handler : this.authenticationFailureHandlers) {
            handler.onAuthenticationFailure(request, exception);
        }
        super.onAuthenticationFailure(request, response, exception);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ApplicationContext applicationContext = this.applicationContext;

        if (this.authenticationFailureHandlers == null) {
            try {
                List<MemberAuthenticationFailureHandler> authenticationFailureHandlers = new ArrayList<>(applicationContext.getBeansOfType(MemberAuthenticationFailureHandler.class).values());
                authenticationFailureHandlers.sort(Comparator.comparingInt(MemberAuthenticationFailureHandler::getOrder));
                this.authenticationFailureHandlers = authenticationFailureHandlers;
            } catch (BeansException e) {
                e.printStackTrace();
                log.warn("初始化authenticationFailureHandlers异常", e);
                this.authenticationFailureHandlers = Collections.emptyList();
            }
        }
    }
}
