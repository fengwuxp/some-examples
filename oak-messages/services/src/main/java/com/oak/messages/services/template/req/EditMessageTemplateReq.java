package com.oak.messages.services.template.req;

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
 *  编辑消息模板
 *  2020-7-7 13:58:37
 */
@Schema(description = "编辑消息模板")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditMessageTemplateReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Size(max = 64)
    @Schema(description = "模板名称")
    @Update
    private String name;

    @Size(max = 128)
    @Schema(description = "标题")
    @Update
    private String title;

    @Size(max = 500)
    @Schema(description = "内容")
    @Update
    private String content;

    @Schema(description = "跳转视图编码")
    @Update
    private String viewCode;

    @Schema(description = "跳转视图URL")
    @Update
    private String viewUrl;

    @Schema(description = "扩展数据")
    @Update
    private String extData;

    @Schema(description = "是否启用")
    @Update
    private Boolean enabled;

    @Schema(description = "分组")
    @Update
    private String group;

    @Schema(description = "是否可修改的")
    @Update
    private Boolean modifiable;

    @Schema(description = "外部模板ID")
    @Update
    private String outTemplateId;

    @Schema(description = "排序")
    @Update
    private Integer orderCode;


    public EditMessageTemplateReq() {
    }

    public EditMessageTemplateReq(Long id) {
        this.id = id;
    }
}
