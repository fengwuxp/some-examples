package com.oak.member.wechat.ma.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author wxup
 */
@Schema(description = "微信回话信息")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = {"sessionKey"})
public class WxSessionInfo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "session_key")
    private String sessionKey;

    @Schema(description = "openId")
    private String openId;

    @Schema(description = "unionId")
    private String unionId;
}
