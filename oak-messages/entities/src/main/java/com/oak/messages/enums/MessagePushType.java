package com.oak.messages.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author wuxp
 */
@Schema(description = "消息推送类型")
public enum MessagePushType implements DescriptiveEnum {

    @Schema(description = "站内推送")
    STATION,

    @Schema(description = "信鸽推送")
    XIN_GE,

    @Schema(description = "极光推送")
    JG,

    @Schema(description = "小米推送")
    XIAO_MI
}
