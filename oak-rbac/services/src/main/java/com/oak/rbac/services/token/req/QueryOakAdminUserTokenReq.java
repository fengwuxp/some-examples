package com.oak.rbac.services.token.req;

import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import com.levin.commons.dao.annotation.misc.Fetch;

import java.util.Date;

/**
 * 查询管理员用户登录的token信息
 * 2020-7-23 10:27:50
 */
@Schema(description = "查询管理员用户登录的token信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryOakAdminUserTokenReq extends ApiBaseQueryReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "管理员Id")
    private Long userId;

    @Schema(description = "客户端渠道号")
    private String channelCode;

    @Schema(description = "登录令牌")
    private String token;

    @Schema(description = "刷新令牌")
    private String refreshToken;

    @Schema(description = "最小token到期日期")
    @Gte("expirationDate")
    private Date minExpirationDate;

    @Schema(description = "最大token到期日期")
    @Lte("expirationDate")
    private Date maxExpirationDate;


    public QueryOakAdminUserTokenReq() {
    }

    public QueryOakAdminUserTokenReq(Long id) {
        this.id = id;
    }
}
