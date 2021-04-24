package com.oak.member.enums.fission;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author wxup
 */

@Schema(description = "用户分销员状态")
public enum MemberCommissionStatus implements DescriptiveEnum {

    @Schema(description = "待审核")
    WAIT,

    @Schema(description = "已通过")
    PASS,

    @Schema(description = "已关闭")
    CLOSE;


}
