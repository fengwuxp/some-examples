package com.oak.member.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

/**
 * @author wxup
 */
@Entity
@Schema(description = "会员安全信息")
@Table(name = "t_member_secure")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"loginPassword", "payPassword", "loginCryptoSalt", "payCryptoSalt", "member"})
public class MemberSecure implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description ="会员ID")
    @Id
    private Long id;

    @Schema(description = "用于登录密码加密的盐")
    @Column(name = "login_crypto_salt", length = 64)
    private String loginCryptoSalt;

    @Schema(description ="登录密码")
    @Column(name = "login_password", nullable = false)
    private String loginPassword;

    @Schema(description = "用于支付密码加密的盐")
    @Column(name = "pay_crypto_salt", length = 64)
    private String payCryptoSalt;

    @Schema(description ="支付密码")
    @Column(name = "pay_password", nullable = false)
    private String payPassword;

    @Schema(description ="登录次数")
    @Column(name = "login_times")
    private Integer loginTimes = 0;

    @Schema(description ="最后登录时间")
    @Column(name = "last_login_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginTime;

    @Schema(description ="最后登录IP")
    @Column(name = "last_login_ip", length = 16)
    private String lastLoginIp;

    @Schema(description ="登录密码设置日期")
    @Column(name = "login_pwd_update_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date loginPwdUpdateTime;

    @Schema(description ="支付密码设置日期")
    @Column(name = "pay_pwd_update_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date payPwdUpdateTime;

    @Schema(description ="需要更改登录密码")
    @Column(name = "change_password")
    private Boolean changeLoginPassword = false;

    @Schema(description ="需要修改支付密码")
    @Column(name = "change_pay_password")
    private Boolean changePayPassword = false;

    @Schema(description ="最后登录密码失败时间")
    @Column(name = "last_login_fail_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginFailTime;

    @Schema(description ="登录密码失败数")
    @Column(name = "login_pwd_fail")
    private Integer loginPwdFail = 0;

    @Schema(description ="最后登录密码锁定时间")
    @Column(name = "last_login_fail_lock_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginFailLockTime;

    @Schema(description ="最后支付密码失败时间")
    @Column(name = "last_pay_fail_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPayFailTime;

    @Schema(description ="支付密码失败数")
    @Column(name = "pay_pwd_fail")
    private Integer payPwdFail = 0;

    @Schema(description ="最后支付密码锁定时间")
    @Column(name = "last_pay_fail_lock_time", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPayFailLockTime;

    @Schema(description ="会员")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Member member;

    @Schema(description ="创建时间")
    @Column(name = "create_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false, length = 19)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date lastUpdateTime;

}
