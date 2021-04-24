package com.oak.rbac.services.token.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.In;
import javax.validation.constraints.Size;
import com.levin.commons.dao.annotation.*;

/**
 *  删除管理员用户登录的token信息
 *  2020-7-23 10:27:50
 */
@Schema(description = "删除管理员用户登录的token信息")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteOakAdminUserTokenReq extends ApiBaseReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "id集合")
    @In("id")
    private Long[] ids;

    public DeleteOakAdminUserTokenReq() {
    }

    public DeleteOakAdminUserTokenReq(Long id) {
        this.id = id;
    }

}
