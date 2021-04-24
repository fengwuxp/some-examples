package com.oak.cms.services.channelimage.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  删除栏目轮播图
 *  2020-5-28 15:33:23
 */
@Schema(description = "删除栏目轮播图")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteChannelImageReq extends ApiBaseReq {

    @Schema(description = "轮播图ID")
    private Long id;

    @Schema(description = "轮播图ID集合")
    @In("id")
    private Long[] ids;

    public DeleteChannelImageReq() {
    }

    public DeleteChannelImageReq(Long id) {
        this.id = id;
    }

}
