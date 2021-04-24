package com.oak.cms.services.channelarticle.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 *  编辑专题文章
 *  2020-5-28 15:30:27
 */
@Schema(description = "编辑专题文章")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditChannelArticleReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "栏目ID")
    @Update
    private Long channelId;

    @Schema(description = "文章ID")
    @Update
    private Long articleId;

    @Schema(description = "排序")
    @Update
    private Integer orderIndex;

    @Schema(description = "创建日期")
    @Update
    private Date crateTime;

    public EditChannelArticleReq() {
    }

    public EditChannelArticleReq(Long id) {
        this.id = id;
    }
}
