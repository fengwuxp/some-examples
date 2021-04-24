package com.oak.rbac.endpoint;


import com.oak.rbac.security.OakUser;
import com.oak.rbac.security.OakAdminAuthenticateSessionManager;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.api.signature.InternalApiSignatureRequest;
import com.wuxp.security.jwt.JwtProperties;
import com.wuxp.security.jwt.JwtTokenPair;
import com.wuxp.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wxup
 */
@Slf4j
@RestController
@RequestMapping("/oak_user")
public class OakUserEndpoint {

    @Autowired
    private OakAdminAuthenticateSessionManager adminAuthenticateSessionManager;

    /**
     * 刷新token
     *
     * @return
     */
    @PostMapping("/refresh_token")
    public ApiResp<UserDetails> refreshToken(HttpServletRequest request, String refreshToken) {
        OakUser oakUser = adminAuthenticateSessionManager.refreshToken(refreshToken, request.getHeader(InternalApiSignatureRequest.CHANNEL_CODE_HEADER_KEY));
        if (oakUser == null) {
            return RestfulApiRespFactory.error("刷新失败");
        }
        return RestfulApiRespFactory.ok(oakUser);
    }



}
