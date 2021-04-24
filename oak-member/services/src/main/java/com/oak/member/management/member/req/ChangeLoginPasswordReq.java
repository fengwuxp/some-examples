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
 * Date： 2020/7/28 11:30
 *
 * @author ZHM
 */
@Schema(description = "修改密码")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class ChangeLoginPasswordReq extends ApiBaseReq {

    @Schema(description = "会员ID编号")
    @InjectField(value =MemberApiContextInjectExprConstant.INJECT_MEMBER_ID_EXPR, condition = InjectField.FORCE_INJECT)
    @NotNull
    private Long memberId;

    @Schema(description = "新密码")
    private String newPassword;

    @Schema(description = "旧密码")
    private String oldPassword;


}
