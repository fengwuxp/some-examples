package com.oak.member.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @Classname WithdrawalAccountType
 * @Description 会员提现账户类型
 * @Date 2020/6/1 11:32
 * @Created by 44487
 */
@Schema(description = "提现账户类型")
public enum BlankType implements DescriptiveEnum {

    @Schema(description = "银行卡类型")
    BANK_CARD,

    @Schema(description = "支付宝类型")
    ALI_PAY,

    @Schema(description = "微信类型")
    WECHAT_PAY

}
