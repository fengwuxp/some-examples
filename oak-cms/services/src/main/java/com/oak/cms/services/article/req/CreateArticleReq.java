package com.oak.cms.services.article.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.cms.enums.ArticleContentType;
import com.oak.cms.enums.ArticleStatus;
import com.oak.cms.enums.CoverMode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  创建Article
 *  2020-5-28 15:20:01
 */
@Schema(description = "创建CreateArticleReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateArticleReq extends ApiBaseReq {

    @Schema(description = "栏目ID")
    private Long channelId;

    @Schema(description = "栏目编号")
    @Size(max = 50)
    private String channelCode;

    @Schema(description = "标题")
    @NotNull
    private String title;

    @Schema(description = "文章摘要")
    @Size(max = 4000)
    private String description;

    @Schema(description = "导图模式")
    @NotNull
    private CoverMode coverMode;

    @Schema(description = "导图")
    @Size(max = 1000)
    private String coverImage;

    @Schema(description = "发布人")
    private String author;

    @Schema(description = "发布人ID")
    private Long authorId;

    @Schema(description = "来源")
    private String source;

    @Schema(description = "使用帮助")
    private Integer helpType;

    @Schema(description = "文章模式")
    @NotNull
    private ArticleContentType contentType;

    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "(HTML)文章内容")
    private String htmlContent;

    @Schema(description = "附件")
    private String attachment;

    @Schema(description = "状态")
    @NotNull
    private ArticleStatus status;

    @Schema(description = "关联来源")
    private String sourceCode;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "排序")
    private Integer orderIndex = 1;

}
