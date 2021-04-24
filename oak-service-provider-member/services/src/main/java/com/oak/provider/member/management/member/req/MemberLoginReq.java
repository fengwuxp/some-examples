package com.oak.provider.member.management.member.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.provider.member.constant.MemberApiContextInjectExprConstant;
import com.oak.provider.member.enums.LoginModel;
import com.oak.provider.member.enums.OpenType;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * @author laiy
 * create at 2020-02-17 10:04
 * @Description
 */
@Data
@Schema(description = "会员登录")
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class MemberLoginReq extends ApiBaseReq {
    @Schema(description = "登陆模式")
    private LoginModel loginModel = LoginModel.PASSWORD;

    //密码登录-------------------------------------------------------
    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "手机号码")
    private String mobilePhone;

    @Schema(description = "登录密码")
    private String password;

    //开放平台登录---------------------------------------------------------
    @Schema(description = "平台类型")
    private OpenType openType;

    @Schema(description = "OPENID")
    private String openId;

    @Schema(description = "UNIONID，有值优先使用")
    private String unionId;

    //刷新TOKEN------------------------------------------------------------
    @Schema(description = "Token")
    private String token;

    @Schema(description = "登录IP")
    @InjectField(value = MemberApiContextInjectExprConstant.INJECT_REQUEST_IP_EXPR)
    private String remoteIp;

}
