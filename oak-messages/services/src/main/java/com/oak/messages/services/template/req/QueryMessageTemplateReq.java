package com.oak.messages.services.template.req;

import com.levin.commons.dao.annotation.Contains;
import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.messages.enums.MessageShowType;
import com.oak.messages.enums.SimpleMessagePushType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 查询消息模板
 * 2020-7-7 13:58:37
 */
@Schema(description = "查询消息模板")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryMessageTemplateReq extends ApiBaseQueryReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "名称")
    @Contains
    private String name;

    @Schema(description = "标题")
    @Contains
    private String title;

    @Schema(description = "消息类型")
    private SimpleMessagePushType pushType;

    @Schema(description = "消息展示类型")
    private MessageShowType showType;

    @Schema(description = "消息的业务分类")
    private String businessType;

    @Schema(description = "编号")
    private String code;

    @Schema(description = "内容")
    private String content;

    @Schema(description = "跳转视图编码")
    private String viewCode;

    @Schema(description = "是否启用")
    private Boolean enabled;

    @Schema(description = "分组")
    private String group;

    @Schema(description = "是否可修改的")
    private Boolean modifiable;

    @Schema(description = "外部模板ID")
    private String outTemplateId;

    @Schema(description = "排序")
    private Integer orderCode;

    @Schema(description = "最小创建时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建时间")
    @Lte("createTime")
    private Date maxCreateTime;


    public QueryMessageTemplateReq() {
    }

    public QueryMessageTemplateReq(Long id) {
        this.id = id;
    }
}
