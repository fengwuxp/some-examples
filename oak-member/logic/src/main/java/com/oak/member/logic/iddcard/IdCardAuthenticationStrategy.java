package com.oak.member.logic.iddcard;


/**
 * 实名认证策略
 *
 * @author wxup
 */
public interface IdCardAuthenticationStrategy {


    /**
     * 实名认证
     *
     * @param model
     * @throws RuntimeException 认证失败则抛异常
     */
    void authentication(IdCardAuthenticationModel model) throws RuntimeException;
}
