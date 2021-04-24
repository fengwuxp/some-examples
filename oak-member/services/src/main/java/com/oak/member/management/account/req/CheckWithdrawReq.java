package com.oak.member.management.account.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.constant.MemberApiContextInjectExprConstant;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Classname CheckWithdrawReq
 * @Description 提现校验
 * @Date 2020/6/8 15:50
 * @Created by 44487
 *
 * 前置步骤：
 *  提现校验内容
 *
 */

@Schema(description = "会员提现校验请求")
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CheckWithdrawReq extends ApiBaseReq {

    @Schema(description = "用户ID")
    @InjectField(value =MemberApiContextInjectExprConstant.INJECT_MEMBER_ID_EXPR, condition = InjectField.FORCE_INJECT)
    private Long memberId;

}
