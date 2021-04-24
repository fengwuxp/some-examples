package com.oak.member.management.account.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.constant.MemberApiContextInjectExprConstant;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Schema(description = "提现申请的req")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ApplyWithdrawalReq extends ApiBaseReq {

    @Schema(description = "会员id")
    @InjectField(value = MemberApiContextInjectExprConstant.INJECT_MEMBER_ID_EXPR, condition = InjectField.FORCE_INJECT)
    private Long memberId;

    @Schema(description = "操作者")
    @InjectField(MemberApiContextInjectExprConstant.INJECT_MEMBER_USER_NAME)
    private String operator;

    @Schema(description = "提现金额，单位：分")
    private Integer amount;

    @Schema(description = "提现方式编码")
    private String depositCashCode;

    @Schema(description ="收款银行")
    private String bankName;

    @Schema(description ="收款账号")
    private String bankNo;

    @Schema(description ="开户人姓名")
    private String bankUser;

    @Schema(description = "提现备注")
    private String remark;

    @Schema(description = "业务类型")
    private String businessType;

}
