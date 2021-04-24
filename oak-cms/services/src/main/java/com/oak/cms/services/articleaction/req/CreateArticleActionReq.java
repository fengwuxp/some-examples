package com.oak.cms.services.articleaction.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.cms.enums.ArticleActionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  创建ArticleAction
 *  2020-5-28 15:28:48
 */
@Schema(description = "创建CreateArticleActionReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateArticleActionReq extends ApiBaseReq {

    @Schema(description = "文章ID")
    @NotNull
    private Long articleId;

    @Schema(description = "互动类型")
    @NotNull
    private ArticleActionType actionType;

    @Schema(description = "关联来源")
    @NotNull
    @Size(max = 64)
    private String sourceCode;

    @Schema(description = "访问的客户端ip")
    private String ip;

}
