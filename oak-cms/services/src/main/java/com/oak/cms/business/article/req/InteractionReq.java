package com.oak.cms.business.article.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.cms.enums.ArticleActionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Classname InteractionReq
 * @Description 确定文章交互类型
 * @Date 2020/6/1 8:57
 * @Created by 44487
 */


@Schema(description = "创建CreateArticleActionReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class InteractionReq extends ApiBaseReq {

    @Schema(description = "文章ID")
    @NotNull
    private Long articleId;

    @Schema(description = "互动类型")
    @NotNull
    private ArticleActionType actionType;

    @Schema(description = "访问的客户端ip")
    private String ip;

}
