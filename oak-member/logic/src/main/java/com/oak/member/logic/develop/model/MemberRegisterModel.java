package com.oak.member.logic.develop.model;


import java.io.Serializable;

/**
 * 用户注册模型
 *
 * @author wxup
 */
public interface MemberRegisterModel extends Serializable {

    /**
     * 用户名称
     *
     * @return
     */
    String getName();

    /**
     * 用户名
     *
     * @return
     */
    String getUserName();


    /**
     * 登录密码
     *
     * @return
     */
    String getPassword();


    /**
     * 用户名
     *
     * @return
     */
    String getNickName();


    /**
     * 渠道编号
     *
     * @return
     */
    String getChannelCode();


}
