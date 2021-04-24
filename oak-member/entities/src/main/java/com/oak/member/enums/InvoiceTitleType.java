package com.oak.member.enums;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author wuxp
 */
@Schema(description = "发票抬头类型")
public enum InvoiceTitleType implements DescriptiveEnum {

    @Schema(description = "企业")
    ENTERPRISE_UNIT,

    @Schema(description = "机关事业单位")
    ORGAN_UNIT,

    @Schema(description = "个人")
    PERSONAL
}
