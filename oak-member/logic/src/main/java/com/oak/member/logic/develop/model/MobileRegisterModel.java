package com.oak.member.logic.develop.model;




/**
 * 手机号码注册
 *
 * @author wxup
 */
public interface MobileRegisterModel extends MemberRegisterModel {


    /**
     * 手机号码
     *
     * @return
     */
    String getMobilePhone();



    /**
     * 验证码
     *
     * @return
     */
    String getCaptcha();



}
