package com.oak.cms.services.article.req;

import com.levin.commons.dao.annotation.Contains;
import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.cms.enums.ArticleStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 *  查询文章
 *  2020-5-28 15:20:01
 */
@Schema(description = "查询文章")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryArticleReq extends ApiBaseQueryReq {

    @Schema(description = "文章ID")
    private Long id;

    @Schema(description = "栏目ID")
    private Long channelId;

    @Schema(description = "栏目编号")
    private String channelCode;

    @Schema(description = "标题")
    @Contains
    private String title;

    @Schema(description = "来源")
    private String source;

    @Schema(description = "状态")
    private ArticleStatus status;

    @Schema(description = "最小发布日期")
    @Gte("publishTime")
    private Date minPublishTime;

    @Schema(description = "最大发布日期")
    @Lte("publishTime")
    private Date maxPublishTime;

    @Schema(description = "关联来源")
    @Contains
    private String sourceCode;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "排序")
    private Integer orderIndex ;

    public QueryArticleReq() {
    }

    public QueryArticleReq(Long id) {
        this.id = id;
    }
}
