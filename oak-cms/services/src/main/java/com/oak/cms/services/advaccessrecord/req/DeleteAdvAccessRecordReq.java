package com.oak.cms.services.advaccessrecord.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  删除广告访问记录
 *  2020-5-28 15:15:29
 */
@Schema(description = "删除广告访问记录")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteAdvAccessRecordReq extends ApiBaseReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "id集合")
    @In("id")
    private Long[] ids;

    public DeleteAdvAccessRecordReq() {
    }

    public DeleteAdvAccessRecordReq(Long id) {
        this.id = id;
    }

}
