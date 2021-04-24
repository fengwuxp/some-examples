package com.oak.member.enums;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author wxup
 */
@Schema(description = "会员审核状态")
public enum MemberVerifyStatus implements DescriptiveEnum {

    @Schema(description = "待审核")
    PENDING,

    @Schema(description = "审核通过")
    APPROVED,

    @Schema(description = "审核拒绝")
    REFUSE;

}
