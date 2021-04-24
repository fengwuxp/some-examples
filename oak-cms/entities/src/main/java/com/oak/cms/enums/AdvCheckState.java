package com.oak.cms.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "广告审核状态")
public enum AdvCheckState implements DescriptiveEnum {

    @Schema(description = "未审核的")
    UNAUDITED,

    @Schema(description = "审核通过")
    APPROVED,

    @Schema(description = "拒绝")
    REFUSE;


}
