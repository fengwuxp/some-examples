package com.oak.member.enums.fission;


import com.wuxp.basic.enums.DescriptiveEnum;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * @author wxup
 */
@Schema(description = "用户发展类型")
public enum DevelopType implements DescriptiveEnum {

    @Schema(description = "代理发展")
    AGENT,

    @Schema(description = "用户发展")
    MEMBER,

    @Schema(description = "自已注册")
    SELF,

    @Schema(description = "平台注册")
    SYSTEM;

}
