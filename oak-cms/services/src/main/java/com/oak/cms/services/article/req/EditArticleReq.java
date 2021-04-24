package com.oak.cms.services.article.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.oak.cms.enums.ArticleContentType;
import com.oak.cms.enums.ArticleStatus;
import com.oak.cms.enums.CoverMode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑文章
 *  2020-5-28 15:20:01
 */
@Schema(description = "编辑文章")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditArticleReq extends ApiBaseReq {

    @Schema(description = "文章ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "标题")
    @Update
    private String title;

    @Size(max = 4000)
    @Schema(description = "文章摘要")
    @Update
    private String description;

    @Schema(description = "导图模式")
    @Update
    private CoverMode coverMode;

    @Size(max = 1000)
    @Schema(description = "导图")
    @Update
    private String coverImage;

    @Schema(description = "来源")
    @Update
    private String source;

    @Schema(description = "阅读数")
    @Update
    private Integer views;

    @Schema(description = "使用帮助")
    @Update
    private Integer helpType;

    @Schema(description = "赞人数")
    @Update
    private Integer likes;

    @Schema(description = "评论数")
    @Update
    private Integer comments;

    @Schema(description = "收藏数")
    @Update
    private Integer collections;

    @Schema(description = "分享数")
    @Update
    private Integer shares;

    @Schema(description = "文章模式")
    @Update
    private ArticleContentType contentType;

    @Schema(description = "文章内容")
    @Update
    private String content;

    @Schema(description = "(HTML)文章内容")
    @Update
    private String htmlContent;

    @Schema(description = "附件")
    @Update
    private String attachment;

    @Schema(description = "状态")
    @Update
    private ArticleStatus status;

    @Schema(description = "发布日期")
    @Update
    private Date publishTime;


    @Schema(description = "关联来源")
    @Update
    private String sourceCode;

    @Schema(description = "备注")
    @Update
    private String remark;

    public EditArticleReq() {
    }

    public EditArticleReq(Long id) {
        this.id = id;
    }
}
