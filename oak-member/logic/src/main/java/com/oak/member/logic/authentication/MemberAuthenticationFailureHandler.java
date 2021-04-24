package com.oak.member.logic.authentication;

import org.springframework.core.Ordered;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户认证失败的处理者，允许存在多个
 * <>
 * 可以在这里执行用户登录失败次数的检查，对用户进行禁用等操作
 * </>
 *
 * @author wxup
 */
public interface MemberAuthenticationFailureHandler extends Ordered {


    /**
     * 处理登录失败事件
     *
     * @param request
     * @param exception
     */
    void onAuthenticationFailure(HttpServletRequest request, AuthenticationException exception);

}
