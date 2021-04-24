package com.oak.rbac.services.role.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "创建role")
public class CreateRoleReq extends ApiBaseReq {

    @Schema(description = "角色名称")
    @NotNull
    private String name;

    @Schema(description = "权限集合")
    @NotNull
    private Long[] permissionIds;

    @Schema(description = "排序")
    @NotNull
    private Integer orderCode;

    @Size(max = 255)
    @Schema(description = "说明")
    private String description;

}
