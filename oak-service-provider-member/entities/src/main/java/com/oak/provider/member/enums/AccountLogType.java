package com.oak.provider.member.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description =  "账号日志类型")
public enum AccountLogType implements DescriptiveEnum {

    @Schema(description = "余额充值")
    RECHARGE,

    @Schema(description = "余额扣减")
    DEDUCT,

    @Schema(description = "余额冻结")
    FREEZE,

    @Schema(description = "余额解冻")
    UNFREEZE

}
