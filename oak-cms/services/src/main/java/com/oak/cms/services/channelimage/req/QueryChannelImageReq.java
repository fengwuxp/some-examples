package com.oak.cms.services.channelimage.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 *  查询栏目轮播图
 *  2020-5-28 15:33:23
 */
@Schema(description = "查询栏目轮播图")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryChannelImageReq extends ApiBaseQueryReq {

    @Schema(description = "轮播图ID")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "栏目ID")
    private Long channelId;

    @Schema(description = "最小开始时间")
    @Gte("beginTime")
    private Date minBeginTime;

    @Schema(description = "最大开始时间")
    @Lte("beginTime")
    private Date maxBeginTime;

    @Schema(description = "最小结束时间")
    @Gte("endTime")
    private Date minEndTime;

    @Schema(description = "最大结束时间")
    @Lte("endTime")
    private Date maxEndTime;

    @Schema(description = "是否上线")
    private Boolean enabled;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "跳转url")
    private String url;

    @Schema(description = "排序")
    private Integer orderIndex;

    @Schema(description = "最小创建日期")
    @Gte("crateTime")
    private Date minCrateTime;

    @Schema(description = "最大创建日期")
    @Lte("crateTime")
    private Date maxCrateTime;

    @Schema(description = "最小更新日期")
    @Gte("updateTime")
    private Date minUpdateTime;

    @Schema(description = "最大更新日期")
    @Lte("updateTime")
    private Date maxUpdateTime;

    @Schema(description = "备注")
    private String remark;

    public QueryChannelImageReq() {
    }

    public QueryChannelImageReq(Long id) {
        this.id = id;
    }
}
