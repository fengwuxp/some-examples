package com.oak.member.security.authentication;

import com.oak.member.services.token.MemberTokenService;
import com.oak.member.services.token.req.SaveMemberTokenReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.security.authenticate.session.AuthenticateSessionManager;
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
import java.util.Date;

import static com.wuxp.api.signature.InternalApiSignatureRequest.CHANNEL_CODE_HEADER_KEY;

/**
 * 退出登录
 *
 * @author wxup
 */
@Slf4j
@Setter
public class OakMemberLogoutSuccessHandler implements LogoutSuccessHandler, BeanFactoryAware, InitializingBean {

    private BeanFactory beanFactory;

    private AuthenticateSessionManager<OakMemberDetails> authenticateSessionManager;

    private JwtProperties jwtProperties;

    private MemberTokenService memberTokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        if (log.isDebugEnabled()) {
            log.debug("{}", "用户退出");
        }
        // TODO 记录退出日志

        if (authentication != null) {
            OakMemberDetails oakMemberDetails = (OakMemberDetails) authentication.getPrincipal();
            // 更新token信息
            SaveMemberTokenReq req = new SaveMemberTokenReq();
            req.setMemberId(oakMemberDetails.getId())
                    .setToken("")
                    .setChannelCode(request.getHeader(CHANNEL_CODE_HEADER_KEY))
                    .setExpirationDate(new Date())
                    .setRefreshExpirationDate(new Date());
            ApiResp<Void> resp = memberTokenService.save(req);
            AssertThrow.assertResp(resp);
        }
        // 移除缓存中的数据
        String authorizationHeader = request.getHeader(jwtProperties.getHeaderName());
        if (StringUtils.hasText(authorizationHeader)) {
            authenticateSessionManager.remove(authorizationHeader);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.authenticateSessionManager == null) {
            this.authenticateSessionManager = beanFactory.getBean(AuthenticateSessionManager.class);
        }
        if (this.jwtProperties == null) {
            this.jwtProperties = beanFactory.getBean(JwtProperties.class);
        }
        if (this.memberTokenService == null) {
            this.memberTokenService = beanFactory.getBean(MemberTokenService.class);
        }
    }
}
