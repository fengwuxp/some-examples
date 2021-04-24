package com.oak.rbac.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @author wuxp
 */
@Entity
@Schema(description = "管理员用户登录的token信息")
@Table(name = "t_rbac_admin_user_token", indexes = {
        @Index(name = "index_admin_token_user_id_channel_code", columnList = "user_id,channel_code")
})
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
public class OakAdminUserToken implements Serializable {


    private static final long serialVersionUID = 6514429532778452737L;

    @Schema(description = "id")
    @Id
    @GeneratedValue()
    private Long id;

    @Schema(description = "管理员Id")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Schema(description = "客户端渠道号")
    @Column(name = "channel_code", length = 16, nullable = false)
    private String channelCode;

    @Schema(description = "登录令牌")
    @Column(name = "token", nullable = false, length = 512)
    private String token;

    @Schema(description = "刷新令牌")
    @Column(name = "refresh_token", length = 512, nullable = false)
    private String refreshToken;

    @Schema(description = "登录时间")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "login_time", nullable = false)
    private Date loginTime;

    @Schema(description = "token到期日期")
    @Column(name = "expiration_date", length = 19,nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @Schema(description = "刷新token到期日期")
    @Column(name = "refresh_expiration_date", length = 19,nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date refreshExpirationDate;
}
