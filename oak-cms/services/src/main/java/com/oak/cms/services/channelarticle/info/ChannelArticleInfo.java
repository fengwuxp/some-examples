package com.oak.cms.services.channelarticle.info;

import com.oak.cms.services.article.info.ArticleInfo;
import com.oak.cms.services.channel.info.ChannelInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 专题文章
 * 2020-5-28 15:30:27
 */
@Schema(description = "专题文章")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString()
public class ChannelArticleInfo implements Serializable {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "栏目ID")
    private Long channelId;

    @Schema(description = "栏目信息")
    private ChannelInfo channelInfo;

    @Schema(description = "文章ID")
    private Long articleId;

    @Schema(description = "文章信息")
    private ArticleInfo articleInfo;

    @Schema(description = "排序")
    private Integer orderIndex;

    @Schema(description = "创建日期")
    private Date crateTime;

    @Schema(description = "更新日期")
    private Date updateTime;


}
