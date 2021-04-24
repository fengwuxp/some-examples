package com.oak.member.security.authentication;

import com.oak.member.enums.MemberLogType;
import com.oak.member.logic.authentication.MemberAuthenticationSuccessHandler;
import com.oak.member.services.memberlog.MemberLogService;
import com.oak.member.services.memberlog.req.CreateMemberLogReq;
import com.oak.member.services.secure.MemberSecureService;
import com.oak.member.services.secure.req.EditMemberSecureReq;
import com.oak.member.services.token.MemberTokenService;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.api.signature.InternalApiSignatureRequest;
import com.wuxp.basic.utils.IpAddressUtils;
import com.wuxp.security.authenticate.HttpMessageResponseWriter;
import com.wuxp.security.authenticate.configuration.WuxpAuthenticateProperties;
import com.wuxp.security.authenticate.form.PasswordLoginEnvironmentHolder;
import com.wuxp.security.authenticate.session.AbstractSessionLimitStrategy;
import com.wuxp.security.authenticate.session.AuthenticateSessionManager;
import com.wuxp.security.jwt.JwtTokenPair;
import com.wuxp.security.jwt.JwtTokenProvider;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.Executor;

/**
 * @author wxup
 */
@Slf4j
@Setter
public class OakMemberAuthenticationSuccessHandler extends AbstractSessionLimitStrategy implements AuthenticationSuccessHandler, ApplicationContextAware, InitializingBean, HttpMessageResponseWriter {


    private ApplicationContext applicationContext;

    private List<MemberAuthenticationSuccessHandler> authenticationSuccessHandlers;

    private PasswordLoginEnvironmentHolder loginEnvironmentHolder;

    private JwtTokenProvider jwtTokenProvider;

    private MemberSecureService memberSecureService;

    private MemberTokenService memberTokenService;

    private MemberLogService memberLogService;

    private Executor taskExecutor;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 更新用户登录状态
        OakMemberDetails principal = (OakMemberDetails) authentication.getPrincipal();
        principal.setClientCode(request.getHeader(InternalApiSignatureRequest.CHANNEL_CODE_HEADER_KEY));

        EditMemberSecureReq req = new EditMemberSecureReq();
        String lastLoginIp = IpAddressUtils.try2GetUserRealIPAddr(request);
        req.setId(principal.getId())
                .setLastLoginTime(new Date())
                .setLastLoginIp(lastLoginIp);
        ApiResp<Void> editResp = memberSecureService.edit(req);
        AssertThrow.assertResp(editResp);

        this.tryAcquire(principal);

        // 写入缓存
        OakMemberDetails userDetails = (OakMemberDetails) authenticateSessionManager.join(principal);
        if (loginEnvironmentHolder != null) {
            loginEnvironmentHolder.remove(request);
        }

        if (log.isDebugEnabled()) {
            log.debug("登录成功 {}", authentication);
        }

        if (this.taskExecutor != null) {
            this.taskExecutor.execute(() -> {
                this.doSuccessfulHandle(request, authentication);
                this.writeLoginLog(principal, lastLoginIp);
            });
        } else {
            this.doSuccessfulHandle(request, authentication);
            this.writeLoginLog(userDetails, lastLoginIp);
        }
        this.writeJson(response, RestfulApiRespFactory.ok(userDetails));
    }

    private void writeLoginLog(OakMemberDetails principal, String lastLoginIp) {
        CreateMemberLogReq req = new CreateMemberLogReq();
        req.setMemberId(principal.getId());
        req.setType(MemberLogType.LOGIN.name());
        req.setOperator(principal.getUsername());
        req.setOperatingTime(new Date());
        req.setShowName(principal.getUsername());
        req.setDescription("登录成功");
        req.setIp(lastLoginIp);
        ApiResp<Long> resp = memberLogService.create(req);
        AssertThrow.assertResp(resp);

    }


    protected void doSuccessfulHandle(HttpServletRequest request, Authentication authentication) {
        for (MemberAuthenticationSuccessHandler handler : this.authenticationSuccessHandlers) {
            handler.onAuthenticationSuccess(request, authentication);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ApplicationContext applicationContext = this.applicationContext;
        if (this.authenticationSuccessHandlers == null) {
            try {
                List<MemberAuthenticationSuccessHandler> authenticationSuccessHandlers = new ArrayList<>(applicationContext.getBeansOfType(MemberAuthenticationSuccessHandler.class).values());
                authenticationSuccessHandlers.sort(Comparator.comparingInt(MemberAuthenticationSuccessHandler::getOrder));
                this.authenticationSuccessHandlers = authenticationSuccessHandlers;
            } catch (BeansException e) {
                e.printStackTrace();
                log.warn("初始化authenticationSuccessHandlers异常", e);
                this.authenticationSuccessHandlers = Collections.emptyList();
            }
        }
        if (this.loginEnvironmentHolder == null) {
            try {
                this.loginEnvironmentHolder = applicationContext.getBean(PasswordLoginEnvironmentHolder.class);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }

        if (this.authenticateSessionManager == null) {
            try {
                this.authenticateSessionManager = applicationContext.getBean(AuthenticateSessionManager.class);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }

        if (this.memberSecureService == null) {
            try {
                this.memberSecureService = applicationContext.getBean(MemberSecureService.class);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }

        if (this.memberTokenService == null) {
            try {
                this.memberTokenService = applicationContext.getBean(MemberTokenService.class);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }

        if (this.memberLogService == null) {
            try {
                this.memberLogService = applicationContext.getBean(MemberLogService.class);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }

        if (this.jwtTokenProvider == null) {
            try {
                this.jwtTokenProvider = applicationContext.getBean(JwtTokenProvider.class);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }

        if (this.taskExecutor == null) {
            this.taskExecutor = this.applicationContext.getBean(Executor.class);
        }
        if (this.authenticateProperties == null) {
            this.authenticateProperties = applicationContext.getBean(WuxpAuthenticateProperties.class);
        }
        if (this.authenticateSessionManager == null) {
            this.authenticateSessionManager = applicationContext.getBean(AuthenticateSessionManager.class);
        }
    }

}
