package com.oak.cms.services.article.info;

import com.oak.cms.enums.ArticleContentType;
import com.oak.cms.enums.ArticleStatus;
import com.oak.cms.enums.CoverMode;
import com.oak.cms.services.channel.info.ChannelInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.io.Serializable;
import java.util.Date;


/**
 * 文章
 * 2020-5-28 15:20:01
 */
@Schema(description = "文章")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString()
public class ArticleInfo implements Serializable {

    @Schema(description = "文章ID")
    private Long id;

    @Schema(description = "栏目ID")
    private Long channelId;

    @Schema(description = "栏目编号")
    private String channelCode;

    @Schema(description = "栏目信息")
    private ChannelInfo channelInfo;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "文章摘要")
    private String description;

    @Schema(description = "导图模式")
    private CoverMode coverMode;

    @Schema(description = "导图")
    private String coverImage;

    @Schema(description = "排序")
    private Integer orderIndex;

    @Schema(description = "发布人")
    private String author;

    @Schema(description = "发布人ID")
    private Long authorId;

    @Schema(description = "来源")
    private String source;

    @Schema(description = "阅读数")
    private Integer views;

    @Schema(description = "使用帮助")
    private Integer helpType;

    @Schema(description = "赞人数")
    private Integer likes;

    @Schema(description = "评论数")
    private Integer comments;

    @Schema(description = "收藏数")
    private Integer collections;

    @Schema(description = "分享数")
    private Integer shares;

    @Schema(description = "文章模式")
    private ArticleContentType contentType;

    @Schema(description = "文章内容")
    private String content;

    @Schema(description = "(HTML)文章内容")
    private String htmlContent;

    @Schema(description = "附件")
    private String attachment;

    @Schema(description = "是否删除")
    private Boolean deleted;

    @Schema(description = "状态")
    private ArticleStatus status;

    @Schema(description = "发布日期")
    private Date publishTime;

    @Schema(description = "创建日期")
    private Date crateTime;

    @Schema(description = "更新日期")
    private Date updateTime;

    @Schema(description = "关联来源")
    private String sourceCode;

    @Schema(description = "备注")
    private String remark;


}
