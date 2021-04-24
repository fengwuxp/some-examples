package com.oak.provider.member.management.third.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"sessionKey"})
public class WxSessionInfo implements Serializable {

    private static final long serialVersionUID = 542174714561963911L;

    @Schema(description = "session_key")
    private String sessionKey;

    @Schema(description = "openid")
    private String openid;

    @Schema(description = "unionid")
    private String unionid;
}
