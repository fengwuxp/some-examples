package com.oak.app.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Created by DELL on 2016/5/18.
 */
@Schema(description = "客户端类型")
public enum ViewType implements DescriptiveEnum {

    @Schema(description = "WEB")
    WEB,
    @Schema(description = "H5")
    H5,
    @Schema(description = "原生应用")
    NATIVE,
    @Schema(description = "安卓应用")
    ANDROID,
    @Schema(description = "IOS应用")
    IOS,
    @Schema(description = "混合应用")
    HYBRID,
    @Schema(description = "WEEX应用")
    WEEX,
    @Schema(description = "微信小程序")
    WXMA;

}
