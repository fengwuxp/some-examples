package com.oak.cms.services.channelarticle.req;

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
 *  查询专题文章
 *  2020-5-28 15:30:27
 */
@Schema(description = "查询专题文章")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryChannelArticleReq extends ApiBaseQueryReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "栏目ID")
    private Long channelId;

    @Schema(description = "加载栏目信息")
    @Fetch(value = "channel", condition = "#_val==true")
    private Boolean loadChannel;

    @Schema(description = "文章ID")
    private Long articleId;

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

    public QueryChannelArticleReq() {
    }

    public QueryChannelArticleReq(Long id) {
        this.id = id;
    }
}
