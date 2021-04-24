package com.oak.cms.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;



@Schema(description = "投放目标")
public enum AdvertisingTargetType implements DescriptiveEnum {

    @Schema(description = "商户")
    MERCHANT,

    @Schema(description = "客户")
    CUSTOMER,

    @Schema(description = "付款设备")
    PAY_EQUIPMENT;
}
