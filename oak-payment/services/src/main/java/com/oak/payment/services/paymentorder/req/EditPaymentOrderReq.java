package com.oak.payment.services.paymentorder.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.oak.payment.enums.PaymentStatus;
import com.oak.payment.enums.PaymentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑支付订单对象
 *  2020-2-6 11:31:05
 */
@Schema(description = "编辑支付订单对象")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditPaymentOrderReq extends ApiBaseReq {

    @Schema(description = "支付单号")
    @NotNull
    @Eq(require = true)
    private String sn;

    @Schema(description = "支付类型")
    @Update
    private PaymentType type;

    @Size(max = 32)
    @Schema(description = "支付关联的订单类型")
    @Update
    private String orderTypes;

    @Schema(description = "归属的卖家用户")
    @Update
    private Long sellerId;

    @Schema(description = "购买用户")
    @Update
    private Long buyerId;

    @Schema(description = "应付金额")
    @Update
    private Integer amount;

    @Schema(description = "已付金额")
    @Update
    private Integer paidAmount;

    @Schema(description = "支付订单支付")
    @Update
    private PaymentStatus status;

    @Schema(description = "到期时间")
    @Update
    private Date expirationTime;

    @Schema(description = "已删除")
    @Update
    private Boolean deleted;

    public EditPaymentOrderReq() {
    }

    public EditPaymentOrderReq(String sn) {
        this.sn = sn;
    }
}
