package com.oak.member.logic.authentication;


import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户认证成功的处理者,允许存在多个
 * <p>
 * 在这里可以执行例如登录送积分、登录日志记录等相关处理
 * </p>
 *
 * @author wxup
 */
public interface MemberAuthenticationSuccessHandler extends Ordered {


    /**
     * 登录成功后的处理
     *
     * @param request
     * @param authentication
     */
    void onAuthenticationSuccess(HttpServletRequest request, Authentication authentication);


}
