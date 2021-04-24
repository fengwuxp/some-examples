package com.oak.member.services.token.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 查询会员登录的token信息
 * 2020-7-23 14:46:45
 */
@Schema(description = "查询会员登录的token信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryMemberTokenReq extends ApiBaseQueryReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "用户id")
    private Long memberId;

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

    public QueryMemberTokenReq() {
    }

    public QueryMemberTokenReq(Long id) {
        this.id = id;
    }
}
