package com.oak.cms.enums;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "广告位")
public enum AdvType implements DescriptiveEnum {

    @Schema(description = "图片")
    IMAGE,

    @Schema(description = "文字")
    TEXT,

    @Schema(description = "视频")
    VIDEO,


}
