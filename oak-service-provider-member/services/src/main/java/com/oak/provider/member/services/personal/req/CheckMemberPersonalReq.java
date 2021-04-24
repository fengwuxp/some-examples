package com.oak.provider.member.services.personal.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.provider.member.constant.MemberApiContextInjectExprConstant;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @author laiy
 * create at 2020-03-06 10:18
 * @Description
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CheckMemberPersonalReq extends ApiBaseReq {

    @Schema(description = "会员ID")
    @InjectField(value = MemberApiContextInjectExprConstant.INJECT_MEMBER_ID_EXPR, condition = InjectField.FORCE_INJECT)
    private Long id;

    @Schema(description = "核验人员")
    @NotNull
    private String checkOperator;

    @Schema(description = "核验结果")
    @NotNull
    private Boolean checkResult;

    @Schema(description = "核验结果信息")
    @NotNull
    private String checkInfo;

}
