package com.oak.rbac.security;

import com.oak.rbac.services.user.OakAdminUserService;
import com.wuxp.security.jwt.JwtProperties;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录
 *
 * @author wxup
 */
@Slf4j
@Setter
public class OakLogoutSuccessHandler implements LogoutSuccessHandler, BeanFactoryAware, InitializingBean {

    private BeanFactory beanFactory;

    private OakAdminAuthenticateSessionManager userSessionCacheHelper;

    private JwtProperties jwtProperties;

    private OakAdminUserService oakAdminUserService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (log.isDebugEnabled()) {
            log.debug("{}", "用户退出");
        }
        if (authentication != null) {
            // TODO 记录退出日志
//            OakUser user = (OakUser) authentication.getPrincipal();
            // 更新登陆状态

        }
        String authorizationHeader = request.getHeader(jwtProperties.getHeaderName());
        if (StringUtils.hasText(authorizationHeader)) {
            // 移除缓存中的数据
            userSessionCacheHelper.remove(authorizationHeader);
        }

    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.userSessionCacheHelper == null) {
            this.userSessionCacheHelper = beanFactory.getBean(OakAdminAuthenticateSessionManager.class);
        }
        if (this.jwtProperties == null) {
            this.jwtProperties = beanFactory.getBean(JwtProperties.class);
        }
        if (this.oakAdminUserService == null) {
            this.oakAdminUserService = beanFactory.getBean(OakAdminUserService.class);
        }
    }
}
