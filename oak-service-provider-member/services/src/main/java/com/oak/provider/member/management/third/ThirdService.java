package com.oak.provider.member.management.third;

import com.oak.provider.member.management.third.info.WxSessionInfo;
import com.oak.provider.member.management.third.info.WxUserInfo;
import com.oak.provider.member.management.third.req.*;
import com.wuxp.api.ApiResp;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 第三方平台
 * @author laiy
 */
public interface ThirdService {

    @Schema(description = "获取微信UnionID")
    ApiResp<String> getWxUnionID(GetWxUnionIDReq evt);

    @Schema(description = "获取微信用户信息")
    ApiResp<WxUserInfo> getWxUserInfo(GetWxUserInfoReq evt);

    @Schema(description = "获取微信小程序会话信息")
    ApiResp<WxSessionInfo> getWxMaSessionInfo(GetWxMaSessionReq evt);

    @Schema(description = "获取微信小程序用户信息")
    ApiResp<WxUserInfo> getWxMaUserInfo(GetWxMaUserReq evt);

    @Schema(description = "获取微信小程序用户手机号")
    ApiResp<String> getWxMaPhoneNumber(GetWxMaPhoneNumberReq evt);

}
