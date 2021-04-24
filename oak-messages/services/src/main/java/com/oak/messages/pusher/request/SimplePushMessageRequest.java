package com.oak.messages.pusher.request;

import com.oak.messages.enums.MessageShowType;
import com.oak.messages.enums.SendMode;
import com.oak.messages.enums.SimpleMessagePushType;
import com.oak.messages.request.PushMessageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author wuxp
 */
@Data
@Accessors(chain = true)
@Schema(description = "简单的消息推送请求")
public class SimplePushMessageRequest implements PushMessageRequest {

    @Schema(description = "模板消息id")
    @NotNull
    private String templateId;

    @Schema(description = "消息推送类型")
    @NotNull
    private SimpleMessagePushType pushType;

    @Schema(description = "消息展示类型")
    @NotNull
    private MessageShowType showType;

    @Schema(description = "分组")
    @NotNull
    private String group;

    @Schema(description = "消息的业务分类")
    @NotNull
    private String businessType;

    @Schema(description = "推送消息方法（异步1）")
    @NotNull
    private SendMode sendMode;

    @Schema(description = "推送消息标题")
    @NotNull
    private String title;

    @Schema(description = "推送消息内容")
    @NotNull
    private String content;

    @Schema(description = "推送消息目标")
    @NotNull
    private Long memberId;

    @Schema(description = "推送参数")
    private String params;

    @Schema(description = "跳转视图编码")
    private String viewCode;

    @Schema(description = "视图参数")
    private String[] viewParams;

    @Schema(description = "是否需要持久化改消息")
    private Boolean persistence = Boolean.TRUE;

    @Schema(description = "客户端类型")
    private String clientType;
}
