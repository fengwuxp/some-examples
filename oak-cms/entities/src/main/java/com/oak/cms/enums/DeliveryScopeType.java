package com.oak.cms.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

public enum DeliveryScopeType implements DescriptiveEnum {

    @Schema(description = "平台所有商户")
    ALL_MERCHANT,

    @Schema(description = "一级代理所有商户")
    ONE_LEVEL_MERCHANT,

    @Schema(description = "二级代理所有商户")
    TWO_LEVEL_MERCHANT,

    @Schema(description = "选择城市")
    SELECT_CITY,

    @Schema(description = "收款后页面")
    RECEIVABLED_PAGE,

    @Schema(description = "客户小程序")
    CUSTOMER_MINI_PROGRAM,

    @Schema(description = "付款页面")
    CUSTOMER_PAY_PAGE,

    @Schema(description = "启动页面")
    BOOT_PAGE,

    @Schema(description = "会员支付页")
    MEMBER_PAY,

    @Schema(description = "支付成功页面")
    PAGE_SUCCESS_PAGE;

}
