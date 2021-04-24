package com.oak.member.services.secure.info;

import com.levin.commons.service.domain.Desc;
import com.oak.member.services.member.info.MemberInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * MemberSecure
 * 2020-2-6 16:00:47
 */
@Schema(description = "MemberSecure")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"memberInfo",})
public class MemberSecureInfo implements Serializable {

    @Schema(description = "会员ID")
    private Long id;

    @Schema(description = "用于登录密码加密的盐")
    private String loginCryptoSalt;

    @Schema(description = "登录密码")
    private String loginPassword;

    @Schema(description = "用于支付密码加密的盐")
    private String payCryptoSalt;

    @Schema(description = "支付密码")
    private String payPassword;

    @Schema(description = "登录次数")
    private Integer loginTimes;

    @Schema(description = "最后登录时间")
    private Date lastLoginTime;

    @Schema(description = "最后登录IP")
    private String lastLoginIp;

    @Schema(description = "登录密码设置日期")
    private Date loginPwdUpdateTime;

    @Schema(description = "支付密码设置日期")
    private Date payPwdUpdateTime;

    @Schema(description = "需要更改登录密码")
    private Boolean changeLoginPassword;

    @Schema(description = "需要修改支付密码")
    private Boolean changePayPassword;

    @Schema(description = "最后登录密码失败时间")
    private Date lastLoginFailTime;

    @Schema(description = "登录密码失败数")
    private Integer loginPwdFail;

    @Schema(description = "最后登录密码锁定时间")
    private Date lastLoginFailLockTime;

    @Schema(description = "最后支付密码失败时间")
    private Date lastPayFailTime;

    @Schema(description = "支付密码失败数")
    private Integer payPwdFail;

    @Schema(description = "最后支付密码锁定时间")
    private Date lastPayFailLockTime;

    @Desc(value = "",code = "member")
    @Schema(description = "会员")
    private MemberInfo memberInfo;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;


}
