package com.oak.rbac.services.token.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;



/**
 *  创建OakAdminUserToken
 *  2020-7-23 10:27:50
 */
@Schema(description = "创建CreateOakAdminUserTokenReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateOakAdminUserTokenReq extends ApiBaseReq {

    @Schema(description = "管理员Id")
    @NotNull
    private Long userId;

    @Schema(description = "客户端渠道号")
    @NotNull
    @Size(max = 16)
    private String channelCode;

    @Schema(description = "登录令牌")
    @NotNull
    @Size(max = 512)
    private String token;

    @Schema(description = "刷新令牌")
    @NotNull
    @Size(max = 512)
    private String refreshToken;

    @Schema(description = "登录时间")
    @NotNull
    private Date loginTime;

    @Schema(description = "token到期日期")
    private Date expirationDate;

    @Schema(description = "刷新token到期日期")
    private Date refreshExpirationDate;

}
