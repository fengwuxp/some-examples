package com.oak.provider.member.services.token.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 会员登录的token信息
 * 2020-2-18 16:22:53
 */
@Schema(description = "会员登录的token信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class MemberTokenInfo implements Serializable {

    private static final long serialVersionUID = 6817843039188216547L;
    @Schema(description = "会员id")
    private Long id;

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
