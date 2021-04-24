package com.oak.cms.services.articleaction.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.oak.cms.enums.ArticleActionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑ArticleAction
 *  2020-5-28 15:28:48
 */
@Schema(description = "编辑ArticleAction")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditArticleActionReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "文章ID")
    @Update
    private Long articleId;

    @Schema(description = "互动类型")
    @Update
    private ArticleActionType actionType;

    @Size(max = 64)
    @Schema(description = "关联来源")
    @Update
    private String sourceCode;

    @Schema(description = "创建日期")
    @Update
    private Date crateTime;

    @Schema(description = "访问的客户端ip")
    @Update
    private String ip;

    public EditArticleActionReq() {
    }

    public EditArticleActionReq(Long id) {
        this.id = id;
    }
}
