package com.oak.member.logic.develop.model;


/**
 * 邮箱注册用户模型
 *
 * @author wxup
 */
public interface EmailRegisterModel extends MemberRegisterModel {


    /**
     * 邮箱
     *
     * @return
     */
    String getEmail();


    /**
     * 验证码
     *
     * @return
     */
    String getCaptcha();


}
