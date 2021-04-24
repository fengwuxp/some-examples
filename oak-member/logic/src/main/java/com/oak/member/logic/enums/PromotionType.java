package com.oak.member.logic.enums;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * @author wxup
 */
@Schema(description = "用户推广类型")
public enum PromotionType implements DescriptiveEnum {

    @Schema(description = "微信分享")
    WE_CHAT,

    @Schema(description = "QQ分享")
    QQ,

    @Schema(description = "URL分享")
    URL,

    @Schema(description = "手机扫码")
    APP,

    @Schema(description = "注册推荐")
    REGISTER;

}
