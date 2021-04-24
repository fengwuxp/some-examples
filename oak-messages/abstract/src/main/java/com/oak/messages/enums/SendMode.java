package com.oak.messages.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author wuxp
 */

@Schema(description = "发送模式")
public enum SendMode implements DescriptiveEnum {

    @Schema(description = "同步")
    SYNC,

    @Schema(description = "异步")
    ASYNC;

}
