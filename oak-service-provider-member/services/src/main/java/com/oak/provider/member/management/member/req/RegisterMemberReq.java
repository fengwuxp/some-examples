package com.oak.provider.member.management.member.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.provider.member.constant.MemberApiContextInjectExprConstant;
import com.oak.provider.member.enums.Gender;
import com.oak.provider.member.enums.MemberVerifyStatus;
import com.oak.provider.member.enums.OpenType;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author laiy
 * create at 2020-02-06 16:15
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(description = "用户注册")
public class RegisterMemberReq extends ApiBaseReq {

    @Schema(description = "手机号")
    private String mobilePhone;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "用户名")
    @NotNull
    private String userName;

    @Schema(description = "会员昵称")
    private String nickName;

    @Schema(description = "区域编码")
    private String areaId;

    @Schema(description = "区域名称")
    private String areaName;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "性别")
    private Gender gender;

    @Schema(description = "生日")
    private Date birthday;

    @Schema(description = "手机认证")
    private Boolean mobileAuth;

    @Schema(description = "头像URL")
    private String avatarUrl;

    @Schema(description = "会员类型")
    private String memberType;

    @Schema(description = "是否未设置登录密码")
    private Boolean notPassword;

    @Schema(description = "登录密码")
    private String loginPassword;

    @Schema(description = "审核状态")
    private MemberVerifyStatus verify;

    // 第三方
    @Schema(description = "平台类型")
    private OpenType openType;

    @Schema(description = "OPENID")
    private String openId;

    @Schema(description = "UNIONID")
    private String unionId;

    @Schema(description = "绑定信息")
    private String bindInfo;

    @Schema(description = "微信appid")
    @InjectField(value = MemberApiContextInjectExprConstant.INJECT_WECHAT_APPID)
    private String wxAppId;

    @Schema(description = "渠道号")
    @InjectField(value = INJECT_CHANNEL_CODE)
    private String channelCode;
}
