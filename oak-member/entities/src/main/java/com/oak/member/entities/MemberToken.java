package com.oak.member.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;


/**
 * @author wxup
 */
@Entity
@Schema(description = "会员登录的token信息")
@Table(name = "t_member_token", indexes = {
        @Index(name = "index_member_token_member_id_channel_code", columnList = "member_id,channel_code")
})
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
public class MemberToken implements java.io.Serializable {

    private static final long serialVersionUID = -6312123664440141268L;

    @Schema(description = "id")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "用户id")
    @Column(name = "member_id", nullable = false)
    private Long memberId;

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
    @Column(name = "expiration_date", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @Schema(description = "刷新token到期日期")
    @Column(name = "refresh_expiration_date", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date refreshExpirationDate;


//    @Schema(description = "客户端渠道")
//    @JoinColumn(name = "channel_code")
//    @ManyToOne(fetch = FetchType.EAGER)
//    private ClientChannel clientChannel;


}
