package com.oak.cms.services.channelarticle.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;



/**
 *  创建ChannelArticle
 *  2020-5-28 15:30:27
 */
@Schema(description = "创建CreateChannelArticleReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateChannelArticleReq extends ApiBaseReq {

    @Schema(description = "栏目ID")
    @NotNull
    private Long channelId;

    @Schema(description = "文章ID")
    @NotNull
    private Long articleId;
}
