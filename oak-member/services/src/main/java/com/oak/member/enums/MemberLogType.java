package com.oak.member.enums;

import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author wxup
 */

@Schema(description = "用户日志类型")
public enum MemberLogType implements DescriptiveEnum {

    @Schema(description = "注册")
    REGISTER,

    @Schema(description = "登录")
    LOGIN,

    @Schema(description = "退出")
    LOGOUT
}
