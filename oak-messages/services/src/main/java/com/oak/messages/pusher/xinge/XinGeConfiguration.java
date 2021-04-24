package com.oak.messages.pusher.xinge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 信鸽推送配置
 *
 * @author wuxp
 */
@Data
@Schema(description = "信鸽推送配置")
public class XinGeConfiguration {

    @Schema(description = "安卓 AccessId")
    private String androidAccessId;

    @Schema(description = "安卓AccessKey")
    private String androidAccessKey;

    @Schema(description = "安卓秘钥")
    private String androidSecretKey;

    @Schema(description = "iso AccessId")
    private String iosAccessId;

    @Schema(description = "iosAccessKey")
    private String iosAccessKey;

    @Schema(description = "ios秘钥")
    private String iosSecretKey;

    @Schema(description = "开发模式，仅支持ios")
    private Boolean dev = false;
}
