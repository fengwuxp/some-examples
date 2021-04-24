package com.oak.member.management.member.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.constant.MemberApiContextInjectExprConstant;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author ChenXiaoBin
 * on 2020-07-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class ForgetPasswordReq extends ApiBaseReq {

    @Schema(description = "会员ID", hidden = true)
    @InjectField(value = MemberApiContextInjectExprConstant.INJECT_MEMBER_ID_EXPR, condition = InjectField.FORCE_INJECT)
    private Long memberId;

    @Schema(description = "手机号")
    @NotNull
    private String mobilePhone;

    @Schema(description	=	"新密码")
    @NotNull
    private String newPassword;

    @Schema(defaultValue = "验证码")
    @NotNull
    private String captcha;
}
