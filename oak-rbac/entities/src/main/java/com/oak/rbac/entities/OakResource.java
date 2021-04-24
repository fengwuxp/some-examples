package com.oak.rbac.entities;

import com.oak.rbac.enums.ResourceType;
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
@Table(name = "t_rbac_resource", indexes = {
        @Index(name = "index_rbac_resource_module_name", columnList = "module_name"),
        @Index(name = "index_rbac_resource_module_code", columnList = "module_code"),
})
@Entity
@Schema(description = "系统资源")
@Data
@Accessors(chain = true)
@ToString(exclude = "permissions")
@EqualsAndHashCode(of = {"id"})
public class OakResource implements Serializable {

    private static final long serialVersionUID = 6566419064985790620L;

    @Id
    @Schema(name = "资源编码")
    @Column(name = "id", nullable = false, length = 32)
    private String id;

    @Schema(description = "名称")
    @Column(name = "name", length = 32, nullable = false)
    protected String name;

    @Schema(description = "资源类型")
    @Column(name = "type", length = 32, nullable = false)
    @Enumerated(EnumType.STRING)
    private ResourceType type;

    @Schema(description = "模块名称")
    @Column(name = "module_name", length = 64, nullable = false)
    private String moduleName;

    @Schema(description = "模块代码")
    @Column(name = "module_code", length = 64, nullable = false)
    private String moduleCode;

    @Schema(description = "资源操作列表")
    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "resource",
            fetch = FetchType.EAGER)
    private Set<OakPermission> permissions;

    @Schema(description = "是否删除")
    @Column(name = "is_deleted", nullable = false)
    private Boolean deleted;

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
}
