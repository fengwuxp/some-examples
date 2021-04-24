package com.oak.member.logic.iddcard;

/**
 * 用于实名认证的模型数据
 *
 * @author wxup
 */
public interface IdCardAuthenticationModel {


    /**
     * 真实姓名
     *
     * @return
     */
    String getName();

    /**
     * 身份证号
     *
     * @return
     */
    String getIdentityNumber();

    /**
     * 证件开始生效时间
     *
     * @return
     */
    String getBeginEffectiveDate();

    /**
     * 证件开始生效时间
     *
     * @return
     */
    String getEndEffectiveDate();

    /**
     * 证件图片 列表
     *
     * @return
     */
    String[] getPhotos();
}
