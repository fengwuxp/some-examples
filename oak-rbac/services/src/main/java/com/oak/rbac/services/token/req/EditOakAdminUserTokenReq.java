package com.oak.rbac.services.token.req;

import com.levin.commons.dao.annotation.update.Update;
import com.levin.commons.dao.annotation.*;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑管理员用户登录的token信息
 *  2020-7-23 10:27:50
 */
@Schema(description = "编辑管理员用户登录的token信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditOakAdminUserTokenReq extends ApiBaseReq {

    @Schema(description = "id")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "管理员Id")
    @Update
    private Long userId;

    @Size(max = 16)
    @Schema(description = "客户端渠道号")
    @Update
    private String channelCode;

    @Size(max = 512)
    @Schema(description = "登录令牌")
    @Update
    private String token;

    @Size(max = 512)
    @Schema(description = "刷新令牌")
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

    public EditOakAdminUserTokenReq() {
    }

    public EditOakAdminUserTokenReq(Long id) {
        this.id = id;
    }
}
