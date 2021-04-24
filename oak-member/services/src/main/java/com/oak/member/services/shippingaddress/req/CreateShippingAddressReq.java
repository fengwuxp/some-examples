package com.oak.member.services.shippingaddress.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.constant.MemberApiContextInjectExprConstant;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 *  创建ShippingAddress
 *  2020-6-11 14:27:51
 * @author chen
 */
@Schema(description = "创建收获地址")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateShippingAddressReq extends ApiBaseReq {


    @Schema(description = "收获地址ID")
    @NotNull
    private String areaId;

    @Schema(description = "用户ID", hidden = true)
    @InjectField(value=MemberApiContextInjectExprConstant.INJECT_MEMBER_ID_EXPR, condition = InjectField.FORCE_INJECT)
    private Long memberId;

    @Schema(description = "收件人")
    @NotNull
    private String recipient;

    @Schema(description = "电话")
    @NotNull
    private String phone;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "默认选中标记")
    private Boolean theDefault;

}
