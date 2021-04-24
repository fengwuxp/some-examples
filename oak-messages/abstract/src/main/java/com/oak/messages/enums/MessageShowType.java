package com.oak.messages.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 消息展示类型
 *
 * @author wuxp
 */
@Schema(description = "消息展示类型")
public enum MessageShowType implements DescriptiveEnum {

    @Schema(description = "通知栏展示消息")
    NOTIFY,

    @Schema(description = "透传消息/静默消息")
    TRANSPARENT;

}
