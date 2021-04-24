package com.oak.cms.business.channel.req;

import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  查询栏目
 *  2020-5-28 15:25:31
 */
@Schema(description = "查询栏目")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryChannelExtendReq extends ApiBaseQueryReq {

    @Schema(description = "栏目ID")
    private Long id;

    @Schema(description = "栏目编号")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "上级栏目ID")
    private Long parentId;

    @Schema(description = "上级栏目Code")
    private String parentCode;

    @Schema(description = "加载上级栏目信息")
    @Fetch(value = "parent", condition = "#_val==true")
    private Boolean loadParent;

    @Schema(description = "是否可用")
    private Boolean enabled;

    public QueryChannelExtendReq() {
    }

    public QueryChannelExtendReq(Long id) {
        this.id = id;
    }
}
