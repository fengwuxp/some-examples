package com.oak.cms.services.channel.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  删除栏目
 *  2020-5-28 15:25:31
 */
@Schema(description = "删除栏目")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteChannelReq extends ApiBaseReq {

    @Schema(description = "栏目ID")
    private Long id;

    @Schema(description = "栏目ID集合")
    @In("id")
    private Long[] ids;

    public DeleteChannelReq() {
    }

    public DeleteChannelReq(Long id) {
        this.id = id;
    }

}
