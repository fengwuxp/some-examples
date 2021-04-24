package com.oak.member.security.develop.req;

import com.oak.api.enums.ClientType;
import com.oak.api.model.ApiBaseReq;
import com.oak.member.enums.Gender;
import com.oak.member.logic.develop.model.MemberFissionModel;
import com.oak.member.logic.enums.FissionType;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author wxup
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(description = "BaseRegisterReq")
public class BaseRegisterReq extends ApiBaseReq implements MemberFissionModel {


    @Schema(description = "客户端类型")
    @NotNull
    protected ClientType clientType;

    @Schema(description = "Email")
    protected String email;

    @Schema(description = "用户名称")
    private String name;

    @Schema(description = "会员昵称")
    protected String nickName;

    @Schema(description = "登录密码")
    protected String password;

    @Schema(description = "区域编码")
    protected String areaId;

    @Schema(description = "区域名称")
    protected String areaName;

    @Schema(description = "地址")
    protected String address;

    @Schema(description = "性别")
    protected Gender gender;

    @Schema(description = "生日")
    protected Date birthday;

    @Schema(description = "头像URL")
    protected String avatarUrl;

    @Schema(description = "推荐类型")
    protected FissionType fissionType;

    @Schema(description = "推荐人编码")
    protected String inviteCode;

    @Schema(description = "渠道编号")
    @NonNull
    @InjectField(INJECT_CHANNEL_CODE)
    protected String channelCode;
}
