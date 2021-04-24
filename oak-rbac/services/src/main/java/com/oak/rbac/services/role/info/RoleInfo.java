package com.oak.rbac.services.role.info;

import com.oak.rbac.services.permission.info.PermissionInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@Schema(description = "角色")
public class RoleInfo implements Serializable {
    private static final long serialVersionUID = -7114546018928573966L;

    @Schema(name = "id")
    private Long id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "排序代码")
    protected Integer orderCode;

    @Schema(description = "是否启用")
    protected Boolean enabled;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;

    @Schema(description = "权限列表")
    private Set<PermissionInfo> permissions;

    @Schema(description = "说明")
    private String description;
}
