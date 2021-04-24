package com.oak.member.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Classname WithdrawStatus
 * @Description 提现状态
 * @Date 2020/5/9 16:07
 * @Created by 44487
 */
@Schema(description = "提现状态")
public enum WithdrawStatus implements DescriptiveEnum {

    @Schema(description = "待审核")
    APPLY,

    @Schema(description = "提现成功")
    SUCCESS,

    @Schema(description = "审核拒绝")
    FAIL;
}
