package com.oak.rbac.services.user.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.rbac.constant.RbacInjectVar;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Date： 2020/8/3 16:35
 *
 * @author ZHM
 */

@Schema(description = "修改密码请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ChangePasswordReq extends ApiBaseReq {

    @Schema(description = "用户ID")
    @InjectField(RbacInjectVar.INJECT_RBAC_USER_ID)
    private Long adminId;

    @Schema(description = "旧密码")
    private String oldPassword;

    @Schema(description = "新密码")
    private String newPassword;

}
