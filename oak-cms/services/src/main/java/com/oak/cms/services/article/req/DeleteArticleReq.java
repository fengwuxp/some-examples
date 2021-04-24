package com.oak.cms.services.article.req;

import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 *  删除文章
 *  2020-5-28 15:20:01
 */
@Schema(description = "删除文章")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteArticleReq extends ApiBaseReq {

    @Schema(description = "文章ID")
    private Long id;

    @Schema(description = "文章ID集合")
    @In("id")
    private Long[] ids;

    public DeleteArticleReq() {
    }

    public DeleteArticleReq(Long id) {
        this.id = id;
    }

}
