package com.oak.cms.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "广告访问类型")
public enum AdvAccessType implements DescriptiveEnum {

    @Schema(description = "点击")
    CLICK,

    @Schema(description = "浏览")
    BROWSE
}
