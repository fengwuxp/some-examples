package com.oak.cms.services.articleaction.info;

import com.oak.cms.enums.ArticleActionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * ArticleAction
 * 2020-5-28 15:28:48
 */
@Schema(description = "ArticleAction")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString()
public class ArticleActionInfo implements Serializable {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "文章ID")
    private Long articleId;

    @Schema(description = "互动类型")
    private ArticleActionType actionType;

    @Schema(description = "关联来源")
    private String sourceCode;

    @Schema(description = "创建日期")
    private Date crateTime;

    @Schema(description = "访问的客户端ip")
    private String ip;


}
