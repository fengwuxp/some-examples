package com.oak.member.security.develop.req;

import com.oak.member.logic.develop.model.MobileRegisterModel;
import com.oak.member.logic.enums.OpenType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 * @author wxup
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(description = "通过手机号码注册用户的请求")
public class MobileRegisterReq extends BaseRegisterReq implements MobileRegisterModel {


    private static final long serialVersionUID = -2841823245973497260L;


    @Schema(description = "手机号")
    @NotNull
    private String mobilePhone;

    @Schema(defaultValue = "验证码")
    @NotNull
    private String captcha;

    @Schema(description = "平台类型")
    private OpenType openType;

    @Schema(description = "openId")
    private String openId;

    @Schema(description = "unionId")
    private String unionId;


    @Override
    public String getUserName() {
        return this.mobilePhone;
    }
}
