package com.oak.member.logic;

import java.io.Serializable;

/**
 * 用户信息定义
 *
 * @author wxup
 */
public interface MemberDefinition extends Serializable {


    /**
     * 用户id
     *
     * @return
     */
    Long getId();

    /**
     * 用户真实名称
     *
     * @return
     */
    String getName();

    /**
     * 用户昵称
     *
     * @return
     */
    String getNickName();

    /**
     * 用户昵称
     *
     * @return
     */
    String getUserName();

    /**
     * 手机号码
     *
     * @return
     */
    String getMobilePhone();

    /**
     * 邮箱
     *
     * @return
     */
    String getEmail();


    /**
     * 头像
     *
     * @return
     */
    String getAvatarUrl();
}
