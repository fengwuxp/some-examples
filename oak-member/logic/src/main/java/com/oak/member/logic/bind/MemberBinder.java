package com.oak.member.logic.bind;


import com.wuxp.api.ApiResp;

import javax.validation.constraints.NotNull;

/**
 * 用来绑定第三方开放平台 手机号码等操作
 *
 * @author wxup
 */
public interface MemberBinder {

    /**
     * 绑定第三方开放平台
     *
     * @param model
     */
    ApiResp<Void> bindOpen(BindOpenModel model);

    /**
     * 解绑第三方开放平台
     *
     * @param model
     */
    ApiResp<Void> unbindOpen(BindOpenModel model);

    /**
     * 绑定手机号码
     *
     * @param memberId
     * @param mobilePhone
     */
    ApiResp<Void> bindMobilePhone(@NotNull Long memberId, @NotNull String mobilePhone);

    /**
     * 绑定手机号码
     *
     * @param memberId
     * @param mobilePhone
     */
//    ApiResp<Void> unBindMobilePhone(@NotNull Long memberId, @NotNull String mobilePhone);
}
