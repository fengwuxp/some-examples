package com.oak.member.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author wxup
 */
@Schema(description = "操作账号的值类型")
public enum AccountOperatorValueType implements DescriptiveEnum {

    @Schema(description = "余额")
    MONEY,

    @Schema(description = "积分")
    POINTS,

    @Schema(description = "代金券")
    COUPON
}
