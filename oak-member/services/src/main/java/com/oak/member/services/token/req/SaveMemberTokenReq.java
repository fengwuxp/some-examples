package com.oak.member.services.token.req;

import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * 创建MemberToken
 * 2020-2-18 16:22:53
 */
@Schema(description = "创建CreateMemberTokenReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SaveMemberTokenReq extends ApiBaseReq {

    @Schema(description = "用户id")
    @NotNull
    @Ignore
    private Long memberId;

    @Schema(description = "客户端渠道号")
    @NotNull
    @Ignore
    private String channelCode;

    @Schema(description = "登录令牌")
    @Size(max = 512)
    @Update
    private String token;

    @Schema(description = "刷新令牌")
    @Size(max = 512)
    @Update
    private String refreshToken;

    @Schema(description = "登录时间")
    @Update
    private Date loginTime;

    @Schema(description = "token到期日期")
    @Update
    private Date expirationDate;

    @Schema(description = "刷新token到期日期")
    @Update
    private Date refreshExpirationDate;



}
