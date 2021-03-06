package com.oak.rbac.services.user.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static com.oak.rbac.entities.OakAdminUser.DEFAULT_BUSINESS_MODULE;


/**
 * 创建OakAdminUser
 * 2020-1-16 18:28:37
 */
@Schema(description = "创建CreateOakAdminUserReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateOakAdminUserReq extends ApiBaseReq {

    @Schema(description = "用户名")
    @NotNull
    @Size(max = 32)
    private String userName;

    @Schema(description = "昵称")
    @Size(max = 32)
    private String nickName;

    @Schema(description = "手机号码")
    @Size(max = 12)
    private String mobilePhone;

    @Schema(description = "邮箱")
    @Size(max = 64)
    private String email;

    @Schema(description = "密码")
    @NotNull
    private String password;

    @Schema(description = "创建人员")
    private Long creatorId;

    @Schema(description = "名称")
    @NotNull
    private String name;

    @Schema(description = "排序代码")
    private Integer orderCode;

    @Size(max = 255)
    @Schema(description = "说明")
    private String description;

    @Schema(description = "是否超管理")
    private Boolean root;

    @Schema(description = "业务模块，默认：default")
    @NotNull
    @Size(max = 32)
    private String businessModule = DEFAULT_BUSINESS_MODULE;

    @Schema(description = "角色ID集合")
    private Long[] roleIds;

}
