package com.oak.messages.services.template.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.messages.enums.MessageShowType;
import com.oak.messages.enums.SimpleMessagePushType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 创建MessageTemplate
 * 2020-7-7 13:58:37
 */
@Schema(description = "创建CreateMessageTemplateReq的请求")
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMessageTemplateReq extends ApiBaseReq {

    @Schema(description = "名称")
    @NotNull
    @Size(max = 64)
    private String name;

    @Schema(description = "消息类型")
    @NotNull
    private SimpleMessagePushType pushType;

    @Schema(description = "消息展示类型")
    @NotNull
    private MessageShowType showType;

    @Schema(description = "消息的业务分类")
    @NotNull
    private String businessType;

    @Schema(description = "标题")
    @NotNull
    @Size(max = 64)
    private String title;

    @Schema(description = "编号")
    @NotNull
    @Size(max = 32)
    private String code;

    @Schema(description = "内容")
    @NotNull
    @Size(max = 500)
    private String content;

    @Schema(description = "跳转视图编码")
    private String viewCode;

    @Schema(description = "跳转视图URL")
    private String viewUrl;

    @Schema(description = "扩展数据")
    private String extData;

    @Schema(description = "分组")
    @NotNull
    private String group;

    @Schema(description = "外部模板ID")
    private String outTemplateId;

    @Schema(description = "推送后的信息是否需要持久化")
    @NotNull
    private Boolean persistence;

}
