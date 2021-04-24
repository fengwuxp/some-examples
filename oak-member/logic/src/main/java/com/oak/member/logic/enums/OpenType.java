package com.oak.member.logic.enums;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author wxup
 */
@Schema(description = "开放平台类型")
public enum OpenType implements DescriptiveEnum {

    @Schema(description = "微信公众平台")
    WE_CHAT,

    @Schema(description = "微信开放平台")
    WE_CHAT_OPEN,

    @Schema(description = "微信小程序")
    WE_CHAT_MA,

    @Schema(description = "支付宝")
    ALI_PAY,

    @Schema(description = "QQ")
    QQ,

    @Schema(description = "腾讯微博")
    TENCENT_WEIBO,

    @Schema(description = "微博")
    WEIBO,

    @Schema(description = "APPLE")
    APPLE;


}
