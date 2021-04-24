package com.oak.member.enums.fission;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author wxup
 */
@Schema(description ="用户发展方式")
public enum DevelopMode implements DescriptiveEnum {

    @Schema(description ="微信")
    WE_CHAT,

    @Schema(description ="QQ")
    QQ,

    @Schema(description ="H5")
    H5,

    @Schema(description ="APP")
    APP,

    @Schema(description ="默认")
    SYSTEM;

}
