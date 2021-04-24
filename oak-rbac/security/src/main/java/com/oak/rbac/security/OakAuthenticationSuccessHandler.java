package com.oak.rbac.security;

import com.oak.rbac.services.role.RoleService;
import com.oak.rbac.services.user.OakAdminUserService;
import com.oak.rbac.services.user.req.EditOakAdminUserReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.api.signature.InternalApiSignatureRequest;
import com.wuxp.security.authenticate.HttpMessageResponseWriter;
import com.wuxp.security.authenticate.configuration.WuxpAuthenticateProperties;
import com.wuxp.security.authenticate.form.PasswordLoginEnvironmentHolder;
import com.wuxp.security.authenticate.session.AbstractSessionLimitStrategy;
import com.wuxp.security.authenticate.session.AuthenticateSessionManager;
import com.wuxp.security.jwt.JwtTokenPair;
import com.wuxp.security.jwt.JwtTokenProvider;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


/**
 * 登录成功处理
 *
 * @author wxup
 */
@Slf4j
@Setter
public class OakAuthenticationSuccessHandler extends AbstractSessionLimitStrategy implements AuthenticationSuccessHandler, BeanFactoryAware, InitializingBean, HttpMessageResponseWriter {


    private BeanFactory beanFactory;

    private PasswordLoginEnvironmentHolder loginEnvironmentHolder;

    private OakAdminUserService oakAdminUserService;

    private OakAdminAuthenticateSessionManager adminAuthenticateSessionManager;

    private RoleService roleService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {


        OakUser principal = (OakUser) authentication.getPrincipal();
        principal.setClientCode(request.getHeader(InternalApiSignatureRequest.CHANNEL_CODE_HEADER_KEY));

        // 更新用户登录状态
        EditOakAdminUserReq editOakAdminUserReq = new EditOakAdminUserReq();
        editOakAdminUserReq.setId(principal.getId())
                .setLastLoginTime(new Date());
        ApiResp<Void> editResp = oakAdminUserService.edit(editOakAdminUserReq);
        AssertThrow.assertResp(editResp);

        // 尝试移除超出登录限制的用户
        this.tryAcquire(principal);
        // 加入缓存
        OakUser oakUser = adminAuthenticateSessionManager.join(principal);

        if (loginEnvironmentHolder != null) {
            loginEnvironmentHolder.remove(request);
        }
        if (log.isDebugEnabled()) {
            log.debug("登录成功 {}", authentication);
        }

        // TODO 记录登录日志

        this.writeJson(response, RestfulApiRespFactory.ok(oakUser));
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if (this.loginEnvironmentHolder == null) {
            this.loginEnvironmentHolder = this.beanFactory.getBean(PasswordLoginEnvironmentHolder.class);
        }

        if (this.oakAdminUserService == null) {
            this.oakAdminUserService = this.beanFactory.getBean(OakAdminUserService.class);
        }

        if (this.adminAuthenticateSessionManager == null) {
            this.adminAuthenticateSessionManager = this.beanFactory.getBean(OakAdminAuthenticateSessionManager.class);
        }

        if (this.roleService == null) {
            this.roleService = this.beanFactory.getBean(RoleService.class);
        }

        if (this.authenticateProperties == null) {
            this.authenticateProperties = this.beanFactory.getBean(WuxpAuthenticateProperties.class);
        }
        if (this.authenticateSessionManager == null) {
            this.authenticateSessionManager = this.beanFactory.getBean(AuthenticateSessionManager.class);
        }
    }
}
