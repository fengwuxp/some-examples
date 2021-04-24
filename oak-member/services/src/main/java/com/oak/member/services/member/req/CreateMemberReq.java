package com.oak.member.services.member.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.enums.Gender;
import com.oak.member.enums.MemberVerifyStatus;
import com.oak.member.logic.enums.OpenType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * 创建Member
 * 2020-2-6 15:32:43
 *
 * @author wxup
 */
@Schema(description = "创建CreateMemberReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMemberReq extends ApiBaseReq {

    @Schema(description = "会员编号")
    @Size(max = 20)
    private String no;

    @Schema(description = "手机号")
    @Size(max = 11)
    private String mobilePhone;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "用户名")
    @NotNull
    @Size(max = 20)
    private String userName;

    @Schema(description = "会员昵称")
    @Size(max = 100)
    private String nickName;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "区域编码")
    @Size(max = 50)
    private String areaId;

    @Schema(description = "区域名称")
    private String areaName;

    @Schema(description = "性别")
    private Gender gender;

    @Schema(description = "生日")
    private Date birthday;

    @Schema(description = "头像URL")
    private String avatarUrl;

    @Schema(description = "推广码")
    private String inviteCode;

    @Schema(description = "注册来源")
    @NotNull
    @Size(max = 32)
    private String channelCode;

    @Schema(description = "审核状态")
    @NotNull
    private MemberVerifyStatus verify;

    @Schema(description = "手机认证")
    private Boolean mobileAuth;

    @Schema(description = "实名认证")
    @NotNull
    private Boolean idAuth;

    @Schema(description = "注册来源")
    @NotNull
    @Size(max = 32)
    private String regSource;

    @Schema(description = "服务提供商的标识")
    private Long serviceProviderId;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;

    // 第三方
    @Schema(description = "平台类型")
    private OpenType openType;

    @Schema(description = "OPENID")
    private String openId;

    @Schema(description = "UNIONID")
    private String unionId;

    @Schema(description = "绑定信息")
    private String bindInfo;


}
