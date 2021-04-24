package com.oak.rbac.services.token.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.service.domain.Desc;


import java.io.Serializable;
import java.util.Date;


/**
* 管理员用户登录的token信息
* 2020-7-23 10:27:50
*/
@Schema(description ="管理员用户登录的token信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class OakAdminUserTokenInfo implements Serializable {

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

        @Schema(description = "登录时间")
        private Date loginTime;

        @Schema(description = "token到期日期")
        private Date expirationDate;

        @Schema(description = "刷新token到期日期")
        private Date refreshExpirationDate;


}
