package com.oak.messages.pusher.request;


import com.oak.messages.enums.SendMode;
import com.oak.messages.enums.SimpleMessagePushType;
import com.oak.messages.request.PushTemplateMessageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author wuxp
 */
@Data
@Schema(description = "简单的模板消息推送请求")
public class SimplePushTemplateMessageRequest implements PushTemplateMessageRequest {

    @Schema(description = "模板消息id")
    @NotNull
    private String templateId;

    @Schema(description = "模板命名参数")
    private Map<String, String> namedParams;

    @Schema(description = "模板变量参数")
    private String[] positionParams;

    @Schema(description = "推送消息方式，默认：异步推送")
    @NotNull
    private SendMode sendMode = SendMode.ASYNC;

    @Schema(description = "推送消息目标")
    @NotNull
    private Long memberId;

    @Schema(description = "客户端类型")
    private String clientType;
}
