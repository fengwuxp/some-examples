package com.oak.member.entities;


import com.oak.api.entities.system.ClientChannel;
import com.oak.member.enums.Gender;
import com.oak.member.enums.MemberVerifyStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import static javax.persistence.GenerationType.AUTO;

/**
 * @author wxup
 */
@Schema(description = "会员信息")
@Entity
@Table(name = "t_member", indexes = {
        @Index(name = "index_member_user_name", columnList = "user_name"),
        @Index(name = "index_member_mobile_phone", columnList = "mobile_phone"),
        @Index(name = "index_member_email", columnList = "email"),
        @Index(name = "index_member_no", columnList = "no"),
        @Index(name = "index_invite_code", columnList = "invite_code"),
})
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"memberAccount", "memberSecure"})
@Accessors(chain = true)
public class Member implements Serializable {

    private static final long serialVersionUID = 9012465308591112972L;
    @Schema(description = "会员ID")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "会员编号")
    @Column(name = "no", nullable = false, unique = true, length = 16)
    private String no;

    @Schema(description = "手机号")
    @Column(name = "mobile_phone", length = 11)
    private String mobilePhone;

    @Schema(description = "用户名")
    @Column(name = "user_name", nullable = false, unique = true, length = 32)
    private String userName;

    @Schema(description = "Email")
    @Column(name = "email", length = 32)
    private String email;

    @Schema(description = "会员昵称")
    @Column(name = "nick_name", length = 32)
    private String nickName;

    @Schema(description = "会员真实姓名")
    @Column(name = "name", length = 32)
    private String name;

    @Schema(description = "性别")
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 16)
    private Gender gender;

    @Schema(description = "生日")
    @Column(name = "birthday", length = 10)
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Schema(description = "手机认证")
    @Column(name = "mobile_auth", nullable = false)
    private Boolean mobileAuth;

    @Schema(description = "实名认证")
    @Column(name = "id_auth", nullable = false)
    private Boolean idAuth;

    @Schema(description = "头像URL")
    @Column(name = "avatar_url")
    private String avatarUrl;

    @Schema(description = "区域编码")
    @Column(name = "area_id", length = 50)
    private String areaId;

    @Schema(description = "区域名称")
    @Column(name = "area_name")
    private String areaName;

    @Schema(description = "注册来源")
    @Column(name = "channel_code", nullable = false, length = 32)
    private String channelCode;

    @Schema(description = "注册来源")
    @JoinColumn(name = "reg_channel", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private ClientChannel clientChannel;

    @Schema(description = "是否未设置登录密码")
    @Column(name = "not_password", nullable = false)
    private Boolean notPassword = false;

    @Schema(description = "是否未设置支付密码")
    @Column(name = "not_pay_password", nullable = false)
    private Boolean notPayPassword = true;

    @Schema(description = "账号冻结期时间")
    @Column(name = "frozen_date", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date frozenDate;

    @Schema(description = "审核状态")
    @Enumerated(EnumType.STRING)
    @Column(name = "verify", nullable = false, length = 12)
    private MemberVerifyStatus verify;

    @Schema(description = "帐户信息")
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private MemberAccount memberAccount;

    @Schema(description = "安全信息")
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private MemberSecure memberSecure;

    @Schema(description = "用户绑定平台列表")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    private Set<MemberOpen> memberOpens;

    @Schema(description = "推广码")
    @Column(name = "invite_code", nullable = false, length = 8)
    private String inviteCode;

    @Schema(description = "是否删除")
    @Column(name = "is_deleted", nullable = false)
    protected Boolean deleted = false;

    @Schema(description = "是否启用")
    @Column(name = "is_enable", nullable = false)
    protected Boolean enabled = true;

    @Schema(description = "创建时间")
    @Column(name = "create_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false, length = 19)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date lastUpdateTime;

    @Schema(description = "服务提供商的标识")
    @Column(name = "service_provider_id")
    private Long serviceProviderId;

}
