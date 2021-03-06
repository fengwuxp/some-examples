package com.oak.provider.member.management.third.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 微信小程序用户手机号
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GetWxMaPhoneNumberReq extends ApiBaseReq {

    @Schema(description = "sessionKey")
    private String sessionKey;

    @Schema(description = "加密数据")
    private String encryptedData;

    @Schema(description = "ivStr")
    private String ivStr;

}
