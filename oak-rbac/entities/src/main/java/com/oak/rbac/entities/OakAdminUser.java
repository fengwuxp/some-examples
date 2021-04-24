package com.oak.rbac.entities;

import com.levin.commons.service.domain.Desc;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author wxup
 */
@Table(name = "t_rbac_admin", indexes = {
        @Index(name = "index_rbac_admin_user_name", columnList = "user_name"),
        @Index(name = "index_rbac_admin_mobile_phone", columnList = "mobile_phone"),
        @Index(name = "index_rbac_admin_e_mail", columnList = "e_mail"),
        @Index(name = "index_rbac_admin_name", columnList = "name")
})
@Entity
@Schema(description = "管理员用户")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"id"})
public class OakAdminUser implements Serializable {


    private static final long serialVersionUID = -1616934267363899850L;

    public static final String DEFAULT_BUSINESS_MODULE = "default";

    @Schema(description = "管理员ID")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Desc("名称")
    @Column(name = "name", length = 32, nullable = false)
    protected String name;

    @Schema(description = "用户名")
    @Column(name = "user_name", nullable = false, length = 32)
    private String userName;

    @Schema(description = "昵称")
    @Column(name = "nick_name", length = 32)
    private String nickName;

    @Schema(description = "手机号码")
    @Column(name = "mobile_phone", length = 12)
    private String mobilePhone;

    @Schema(description = "邮箱")
    @Column(name = "e_mail", length = 64)
    private String email;

    @Schema(description = "密码")
    @Column(name = "password", nullable = false)
    private String password;

    @Schema(description = "用于密码加密的盐")
    @Column(name = "crypto_salt", nullable = false, length = 64)
    private String cryptoSalt;

    @Schema(description = "是否超管理")
    @Column(name = "root", nullable = false)
    private Boolean root = false;

    @Schema(description = "业务模块，默认：default")
    @Column(name = "business_module", nullable = false, length = 32)
    private String businessModule;

    @Schema(description = "创建人员")
    @Column(name = "create_id")
    private Long creatorId;

    @Schema(description = "创建人员名称")
    @Column(name = "create_name", length = 32)
    private String creatorName;

    @Schema(description = "账号锁定到期时间")
    @Column(name = "lock_expired")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lockExpired;

    @Schema(description = "最后登录时间")
    @Column(name = "last_login_time")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastLoginTime;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            // 用来指定中间表的名称
            name = "t_rbac_user_role",
            // joinColumns,当前对象在中间表中的外键
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            // inverseJoinColumns，对方对象在中间表的外键
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")}
    )
    @Schema(description = "关联的角色列表")
    private Set<OakRole> roles;

    @Schema(description = "是否删除")
    @Column(name = "is_deleted", nullable = false)
    private Boolean deleted;

    @Schema(description = "是否允许")
    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Schema(description = "创建时间")
    @Column(name = "create_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastUpdateTime;

    @Schema(description = "登录令牌")
    @Size(max = 512)
    private String token;

    @Schema(description = "token失效时间")
    private Date tokenExpired;
}
