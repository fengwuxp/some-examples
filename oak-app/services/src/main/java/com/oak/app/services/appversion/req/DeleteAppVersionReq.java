package com.oak.app.services.appversion.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  删除app版本
 *  2020-7-4 17:56:49
 */
@Schema(description = "删除app版本")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteAppVersionReq extends ApiBaseReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "ID集合")
    @In("id")
    private Long[] ids;

    public DeleteAppVersionReq() {
    }

    public DeleteAppVersionReq(Long id) {
        this.id = id;
    }

}
