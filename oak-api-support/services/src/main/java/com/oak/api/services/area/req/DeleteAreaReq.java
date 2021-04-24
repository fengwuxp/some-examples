package com.oak.api.services.area.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  删除地区信息
 *  2020-6-16 10:25:17
 */
@Schema(description = "删除地区信息")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteAreaReq extends ApiBaseReq {

    @Schema(description = "地区编码")
    private String id;

    @Schema(description = "地区编码集合")
    @In("id")
    private String[] ids;

    public DeleteAreaReq() {
    }

    public DeleteAreaReq(String id) {
        this.id = id;
    }

}
