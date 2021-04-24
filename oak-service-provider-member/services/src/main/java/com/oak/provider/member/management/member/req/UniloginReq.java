package com.oak.provider.member.management.member.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.provider.member.constant.MemberApiContextInjectExprConstant;
import com.oak.provider.member.enums.LoginModel;
import com.oak.provider.member.enums.OpenType;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author laiy
 * create at 2020-02-27 15:27
 * @Description
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UniloginReq extends ApiBaseReq {

    @Schema(description = "登录模式")
    @NotNull
    private LoginModel loginModel;

    @Schema(description = "注册会员类型")
    private String memberType;

    @Schema(description = "注册地区")
    private String areaId;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "手机号码")
    private String mobilePhone;

    @Schema(description = "EMAIL")
    private String email;

    @Schema(description = "昵称")
    private String nickName;

    @Schema(description = "姓名")
    private String realName;

    @Schema(description = "头像URL")
    private String avatarUrl;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "绑定第三方平台类型")
    private OpenType openType;

    @Schema(description = "OPENID")
    private String openId;

    @Schema(description = "UNIONID，有值优先使用")
    private String unionId;

    @Schema(description = "登录IP")
    @InjectField(value = MemberApiContextInjectExprConstant.INJECT_REQUEST_IP_EXPR)
    private String remoteIp;

    @Schema(description = "微信appid")
    @InjectField(value = MemberApiContextInjectExprConstant.INJECT_WECHAT_APPID)
    private String wxAppId;
}
