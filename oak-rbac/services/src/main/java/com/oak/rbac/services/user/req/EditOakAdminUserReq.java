package com.oak.rbac.services.user.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.Ignore;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * 编辑管理员用户
 * 2020-1-16 18:28:37
 */
@Schema(description = "编辑管理员用户")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditOakAdminUserReq extends ApiBaseReq {

    @Schema(description = "管理员ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Size(max = 32)
    @Schema(description = "昵称")
    @Update
    private String nickName;

    @Size(max = 12)
    @Schema(description = "手机号码")
    @Update
    private String mobilePhone;

    @Size(max = 64)
    @Schema(description = "邮箱")
    @Update
    private String email;

    @Schema(description = "名称")
    @Update
    private String name;

    @Schema(description = "排序代码")
    @Update
    private Integer orderCode;

    @Schema(description = "是否允许")
    @Update
    private Boolean enabled;

    @Size(max = 255)
    @Schema(description = "备注")
    @Update
    private String description;

    @Schema(description = "密码")
    @Size(min = 6)
    @Ignore
    private String password;


    @Schema(description = "账号锁定到期时间")
    @Update
    private Date lockExpired;

    @Schema(description = "最后登录时间")
    @Update
    private Date lastLoginTime;

    @Schema(description = "角色ID集合")
    @Ignore
    private Long[] roleIds;

    public EditOakAdminUserReq() {
    }

    public EditOakAdminUserReq(Long id) {
        this.id = id;
    }
}
