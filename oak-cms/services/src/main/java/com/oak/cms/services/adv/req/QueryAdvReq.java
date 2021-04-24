package com.oak.cms.services.adv.req;

import com.levin.commons.dao.annotation.Contains;
import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
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

public class QueryAdvReq extends ApiBaseQueryReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "广告位id")
    private Long apId;

    @Schema(description = "加载广告位")
    @Fetch(value = "advPosition", condition = "#_val==true")
    private Boolean loadAdvPosition;

    @Schema(description = "广告标题")
    @Contains
    private String title;

    @Schema(description = "最小有效时间-开始")
    @Gte("startDate")
    private Date minStartDate;

    @Schema(description = "最大有效时间-开始")
    @Lte("startDate")
    private Date maxStartDate;

    @Schema(description = "最小有效时间-结束")
    @Gte("endDate")
    private Date minEndDate;

    @Schema(description = "最大有效时间-结束")
    @Lte("endDate")
    private Date maxEndDate;

    @Schema(description = "广告拥有者")
    private Long memberId;

    @Schema(description = "会员用户名")
    @Contains
    private String memberName;

    @Schema(description = "归属地区id")
    private String areaId;

    @Schema(description = "城市归属省份id")
    private String provinceId;

    @Schema(description = "加载广告投放地区")
    @Fetch(value = "area", condition = "#_val==true")
    private Boolean loadArea;


    @Schema(description = "是否启用")
    private Boolean enabled;

    @Schema(description = "最小创建日期")
    @Gte("crateTime")
    private Date minCrateTime;

    @Schema(description = "最大创建日期")
    @Lte("crateTime")
    private Date maxCrateTime;

    public QueryAdvReq() {
    }

    public QueryAdvReq(Long id) {
        this.id = id;
    }
}
