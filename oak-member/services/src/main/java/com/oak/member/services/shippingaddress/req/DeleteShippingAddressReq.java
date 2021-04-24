package com.oak.member.services.shippingaddress.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.In;
import com.oak.api.model.ApiBaseReq;
import com.oak.member.constant.MemberApiContextInjectExprConstant;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 *  删除收获地址
 *  2020-6-11 14:27:51
 */
@Schema(description = "删除收获地址")
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class DeleteShippingAddressReq extends ApiBaseReq {

    @Schema(description = "会员ID")
    private Long id;

    @Schema(description = "会员ID集合")
    @In("id")
    private Long[] ids;

    @Schema(description = "用户ID")
    @Eq
    @NotNull
    @InjectField(value =MemberApiContextInjectExprConstant.INJECT_MEMBER_ID_EXPR, condition = InjectField.FORCE_INJECT)
    private Long memberId;

    public DeleteShippingAddressReq() {
    }

    public DeleteShippingAddressReq(Long id) {
        this.id = id;
    }

}
