package com.oak.cms.business.channel.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 *  编辑栏目
 *  2020-5-28 15:25:31
 */
@Schema(description = "编辑栏目")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditChannelExtendReq extends ApiBaseReq {

    @Schema(description = "栏目ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "名称")
    @Update
    private String name;

    @Schema(description = "排序")
    @Update
    private Integer orderIndex;

    public EditChannelExtendReq() {
    }

    public EditChannelExtendReq(Long id) {
        this.id = id;
    }
}
