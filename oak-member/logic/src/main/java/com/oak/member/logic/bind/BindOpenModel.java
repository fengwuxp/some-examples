package com.oak.member.logic.bind;

import com.oak.member.logic.enums.OpenType;

import javax.validation.constraints.NotNull;

/**
 * 绑定第三方开放平台的模型
 *
 * @author wxup
 */
public interface BindOpenModel {

    /**
     * 用户id
     *
     * @return
     */
    @NotNull
    Long getMemberId();

    /**
     * 开放平台类型
     *
     * @return
     */
    @NotNull
    OpenType getOpenType();

    /**
     * 开放平台id
     *
     * @return
     */
    @NotNull
    String getOpenId();

    /**
     * 微信开放平台相关需要
     *
     * @return
     */
    String getUnionId();
}
