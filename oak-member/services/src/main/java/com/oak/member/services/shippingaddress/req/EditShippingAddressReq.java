package com.oak.member.services.shippingaddress.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.oak.member.constant.MemberApiContextInjectExprConstant;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  编辑收获地址
 *  2020-6-11 14:27:51
 * @author chen
 */
@Schema(description = "编辑收获地址")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditShippingAddressReq extends ApiBaseReq {


    @Schema(description = "id")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "用户ID")
    @Eq
    @NotNull
    @InjectField(value=MemberApiContextInjectExprConstant.INJECT_MEMBER_ID_EXPR, condition = InjectField.FORCE_INJECT)
    private Long memberId;

    @Schema(description = "收获地址ID")
    @Update
    private String areaId;

    @Size(max = 1000)
    @Schema(description = "备注")
    @Update
    private String remark;

    @Schema(description = "收件人")
    @Update
    private String recipient;

    @Schema(description = "电话")
    @Update
    private String phone;

    @Schema(description = "详细地址")
    @Update
    private String address;

    @Schema(description = "默认选中标记")
    @Update
    private Boolean theDefault;

    public EditShippingAddressReq() {
    }

    public EditShippingAddressReq(Long id) {
        this.id = id;
    }
}
