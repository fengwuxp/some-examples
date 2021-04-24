package com.oak.cms.business.adv.req;

import com.levin.commons.dao.annotation.Contains;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *  查询广告信息
 *  2020-5-28 15:02:29
 */
@Schema(description = "查询广告信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class QueryAdvExtendReq extends ApiBaseQueryReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "广告位id")
    private Long apId;

    @Schema(description = "广告位编号")
    private String apCode;

    @Schema(description = "加载广告位")
    @Fetch(value = "advPosition", condition = "#_val==true")
    private Boolean loadAdvPosition;

    @Schema(description = "广告标题")
    @Contains
    private String title;

    @Schema(description = "广告有效开始时间")
    private Date startDate;

    @Schema(description = "广告有效结束时间")
    private Date endDate;

    @Schema(description = "是否启用")
    private Boolean enabled;

    public QueryAdvExtendReq() {
    }

    public QueryAdvExtendReq(Long id) {
        this.id = id;
    }
}
