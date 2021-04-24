package com.oak.member.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author wuxp
 */
@Schema(description = "发票类型")
public enum InvoiceType implements DescriptiveEnum {

    @Schema(description = "普通发票")
    COMMON,

    @Schema(description = "增值税发票")
    VAT;
}
