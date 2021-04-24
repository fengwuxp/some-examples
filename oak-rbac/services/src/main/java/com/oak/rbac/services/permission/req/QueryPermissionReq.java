package com.oak.rbac.services.permission.req;

import com.levin.commons.dao.annotation.Contains;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.rbac.enums.PermissionValueType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;


@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "查询权限")
public class QueryPermissionReq extends ApiBaseQueryReq {

    @Schema(name = "id")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "名称模糊查询")
    @Contains(value = "name")
    private String likeName;

    @Schema(description = "资源标识")
    private String resourceId;

    @Schema(description = "权限类型")
    private PermissionValueType valueType;

    @Schema(description = "权限值")
    private String value;

    @Schema(description = "是否加载角色列表")
    @Fetch(value = "roles", condition = "#_val==true")
    private Boolean fetchRole;

    @Schema(description = "是否允许")
    private Boolean enabled = true;

    @Size(max = 255)
    @Schema(description = "说明")
    @Update
    private String description;

}
