package com.oak.cms.services.channel.req;

import com.levin.commons.dao.annotation.Contains;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.cms.enums.ChannelNextMode;
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

public class QueryChannelReq extends ApiBaseQueryReq {

    @Schema(description = "栏目ID")
    private Long id;

    @Schema(description = "栏目编号")
    private String code;

    @Schema(description = "名称")
    @Contains
    private String name;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "排序")
    private Integer orderIndex;

    @Schema(description = "上级栏目ID")
    private Long parentId;

    @Schema(description = "加载上级栏目信息")
    @Fetch(value = "parent", condition = "#_val==true")
    private Boolean loadParent;


    @Schema(description = "下级栏目模式")
    private ChannelNextMode nextMode;

    @Schema(description = "启用分域筛查，栏目下内容将按分域筛查")
    private Boolean enableDomain;

    @Schema(description = "是否需要审批，栏目下内容是否需要审批")
    private Boolean needApprove;

    @Schema(description = "是否可用")
    private Boolean enabled;

    @Schema(description = "是否删除")
    private Boolean deleted;

    public QueryChannelReq() {
    }

    public QueryChannelReq(Long id) {
        this.id = id;
    }
}
