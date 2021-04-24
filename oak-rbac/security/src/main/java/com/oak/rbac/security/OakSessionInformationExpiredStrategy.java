package com.oak.rbac.security;

import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.authenticate.HttpMessageResponseWriter;
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
@Slf4j
@Setter
public class OakSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy, HttpMessageResponseWriter, InitializingBean, BeanFactoryAware {


    private BeanFactory beanFactory;

    private OakAdminAuthenticateSessionManager userSessionCacheHelper;

    private JwtProperties jwtProperties;

    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent sessionInformationExpiredEvent) throws IOException, ServletException {

        String authorizationHeader = sessionInformationExpiredEvent.getRequest().getHeader(jwtProperties.getHeaderName());

        if (authorizationHeader != null) {
            userSessionCacheHelper.remove(authorizationHeader);
        }

        HttpServletResponse response = sessionInformationExpiredEvent.getResponse();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        this.writeJson(response, RestfulApiRespFactory.unAuthorized("登录已失效"));
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.userSessionCacheHelper == null) {
            this.userSessionCacheHelper = this.beanFactory.getBean(OakAdminAuthenticateSessionManager.class);
        }

        if (this.jwtProperties == null) {
            this.jwtProperties = this.beanFactory.getBean(JwtProperties.class);
        }
    }
}
