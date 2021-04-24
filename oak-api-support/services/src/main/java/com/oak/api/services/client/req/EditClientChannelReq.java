package com.oak.api.services.client.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  编辑ClientChannel
 *  2020-3-11 11:05:46
 * @author wxup
 */
@Schema(description = "编辑ClientChannel")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditClientChannelReq extends ApiBaseReq {

    @Schema(description = "编号")
    @NotNull
    @Eq(require = true)
    private String code;

    @Size(max = 128)
    @Schema(description = "名称")
    @Update
    private String name;

    @Schema(description = "排序")
    @Update
    private Integer orderIndex;

    @Schema(description = "启用")
    @Update
    private Boolean enabled;

    public EditClientChannelReq() {
    }

    public EditClientChannelReq(String code) {
        this.code = code;
    }
}
