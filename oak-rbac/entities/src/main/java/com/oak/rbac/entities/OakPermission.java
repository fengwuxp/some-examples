package com.oak.rbac.entities;

import com.oak.rbac.enums.PermissionValueType;
import com.wuxp.resouces.AntUrlResource;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author wxup
 */
@Table(name = "t_rbac_permission", indexes = {
        @Index(name = "index_rbac_permission_resource_id", columnList = "resource_id")
})
@Entity
@Schema(description = "权限定义表")
@Data
@Accessors(chain = true)
@ToString(exclude = {"roles", "resource"})
@EqualsAndHashCode(of = {"id","value"})
public class OakPermission implements AntUrlResource<Long>, Serializable {

    private static final long serialVersionUID = -2236535431871280682L;

    @Id
    @Schema(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Schema(description = "名称")
    @Column(name = "name", length = 32, nullable = false)
    protected String name;

    @Schema(description = "权限类型")
    @Column(name = "value_type", length = 16, nullable = false)
    @Enumerated(EnumType.STRING)
    private PermissionValueType valueType;

    @Schema(description = "权限值(操作代码)")
    @Column(name = "value", length = 512, nullable = false)
    private String value;

    @Schema(description = "支持的http method，多个使用逗号分隔")
    @Column(name = "http_method", length = 32, nullable = false)
    private String httpMethod;

    @Schema(description = "资源标识")
    @Column(name = "resource_id", length = 32, nullable = false)
    private String resourceId;

    @Schema(description = "管理资源")
    @JoinColumn(name = "resource_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private OakResource resource;

    @Schema(description = "权限关联角色，多对多")
    @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
    private Set<OakRole> roles;

    @Schema(description = "是否允许")
    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Schema(description = "排序")
    @Column(name = "order_code", nullable = false)
    private Integer orderCode;

    @Schema(description = "创建时间")
    @Column(name = "create_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastUpdateTime;

    @Schema(description = "说明")
    @Column(name = "description")
    private String description;

    @Override
    public String getPattern() {
        return this.value;
    }

    @Override
    public Class<?> getClassType() {
        return OakPermission.class;
    }
}
