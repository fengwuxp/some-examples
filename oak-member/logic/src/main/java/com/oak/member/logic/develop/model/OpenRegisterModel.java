package com.oak.member.logic.develop.model;


import com.oak.member.logic.enums.OpenType;

/**
 * 第三方开放平台注册模型
 *
 * @author wxup
 */
public interface OpenRegisterModel extends MemberRegisterModel {

    /**
     * 开放平台类型
     *
     * @return
     */
    OpenType getOpenType();

    /**
     * 第三方开放平台用户id
     *
     * @return
     */
    String getOpenId();


}
