package com.oak.member.security.authentication;

import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.authenticate.HttpMessageResponseWriter;
import com.wuxp.security.authenticate.session.AuthenticateSessionManager;
import com.wuxp.security.jwt.JwtProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 会话信息过期策略
 *
 * @author wxup
 */
@Setter
@Slf4j
public class OakMemberSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy, HttpMessageResponseWriter, InitializingBean, BeanFactoryAware {


    private BeanFactory beanFactory;

    private AuthenticateSessionManager<OakMemberDetails> authenticateSessionManager;

    private JwtProperties jwtProperties;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {

        String authorizationHeader = sessionInformationExpiredEvent.getRequest().getHeader(jwtProperties.getHeaderName());

        if (authorizationHeader != null) {
            authenticateSessionManager.remove(authorizationHeader);
        }

        HttpServletResponse response = sessionInformationExpiredEvent.getResponse();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        this.writeJson(response, RestfulApiRespFactory.unAuthorized("登录已失效"));
    }


    @Override
    public void afterPropertiesSet() throws Exception {

        BeanFactory beanFactory = this.beanFactory;
        if (this.authenticateSessionManager != null) {
            this.authenticateSessionManager = beanFactory.getBean(AuthenticateSessionManager.class);
        }

        if (this.jwtProperties != null) {
            this.jwtProperties = beanFactory.getBean(JwtProperties.class);
        }

    }
}
