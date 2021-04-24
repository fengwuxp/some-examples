package com.oak.cms.services.articleaction.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.cms.enums.ArticleActionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 *  查询ArticleAction
 *  2020-5-28 15:28:48
 */
@Schema(description = "查询ArticleAction")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryArticleActionReq extends ApiBaseQueryReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "文章ID")
    private Long articleId;

    @Schema(description = "互动类型")
    private ArticleActionType actionType;

    @Schema(description = "关联来源")
    private String sourceCode;

    @Schema(description = "最小创建日期")
    @Gte("crateTime")
    private Date minCrateTime;

    @Schema(description = "最大创建日期")
    @Lte("crateTime")
    private Date maxCrateTime;

    @Schema(description = "访问的客户端ip")
    private String ip;

    public QueryArticleActionReq() {
    }

    public QueryArticleActionReq(Long id) {
        this.id = id;
    }
}
