package com.oak.rbac.entities;

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
@Table(name = "t_rbac_role", indexes = {
        @Index(name = "index_rbac_role_name", columnList = "name")
})
@Entity
@Schema(description = "角色")
@Data
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"permissions"})
@Accessors(chain = true)
public class OakRole implements Serializable {

    private static final long serialVersionUID = 1344679353498978994L;

    @Id
    @Schema(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Schema(description = "名称")
    @Column(name = "name", length = 32, nullable = false)
    protected String name;

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            // 用来指定中间表的名称
            name = "t_rbac_role_permission",
            // joinColumns,当前对象在中间表中的外键
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            // inverseJoinColumns，对方对象在中间表的外键
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")}
    )
    @Schema(description = "关联的权限列表")
    private Set<OakPermission> permissions;


    //配置多对多
    @ManyToMany(mappedBy = "roles")
    private Set<OakAdminUser> users;



}
