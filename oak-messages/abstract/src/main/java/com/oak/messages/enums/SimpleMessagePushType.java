package com.oak.messages.enums;

import com.oak.messages.MessagePushType;
import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * @author wuxp
 */
@Schema(description = "简单的消息推送类型")
public enum SimpleMessagePushType implements DescriptiveEnum, MessagePushType {


    @Schema(description = "站内通知消息")
    STATION("notice"),

    @Schema(description = "推送消息")
    PUSH("push"),

    @Schema(description = "微信服务号推送")
    WE_CHAT_MP("we_chat_mp"),

    @Schema(description = "微信小程序推送")
    WE_CHAT_MA("we_chat_ma");

    final String code;

    SimpleMessagePushType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }
}
