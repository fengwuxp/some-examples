package com.oak.member.security.develop.req;

import com.oak.member.logic.develop.model.OpenRegisterModel;
import com.oak.member.logic.enums.OpenType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;


/**
 * @author wxup
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(description = "通过openId注册用户的请求")
public class OpenIdRegisterReq extends BaseRegisterReq implements OpenRegisterModel {

    @Schema(description = "平台类型")
    @NonNull
    private OpenType openType;

    @Schema(description = "openId")
    @NonNull
    private String openId;

    @Schema(description = "unionId")
    private String unionId;

    @Schema(description = "手机号")
    private String mobilePhone;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "是否关注")
    private Boolean subscribe;

}
