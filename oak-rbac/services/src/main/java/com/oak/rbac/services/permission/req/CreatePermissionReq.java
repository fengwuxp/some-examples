package com.oak.rbac.services.permission.req;


import com.oak.api.model.ApiBaseReq;
import com.oak.rbac.enums.PermissionValueType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;


@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "创建权限")
public class CreatePermissionReq extends ApiBaseReq {

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限值")
    private String value;

    @Schema(description = "支持的http method，多个使用逗号分隔",example = "GET,POST")
    private String httpMethod;

    @Schema(description = "权限类型")
    private PermissionValueType valueType;

    @Schema(description = "资源标识")
    private String resourceId;

    @Schema(description = "排序")
    private Integer orderCode = 0;

    @Size(max = 255)
    @Schema(description = "说明")
    private String description;

}
