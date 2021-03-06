package com.oak.payment.services.payment.req;

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
 *  编辑支付单对象
 *  2020-2-6 11:21:49
 */
@Schema(description = "编辑支付单对象")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditPaymentReq extends ApiBaseReq {

    @Schema(description = "支付交易流水号")
    @NotNull
    @Eq(require = true)
    private String sn;

    @Size(max = 32)
    @Schema(description = "支付单号，支付订单号的sn")
    @Update
    private String payOrderSn;

    @Size(max = 100)
    @Schema(description = "支付方式名称")
    @Update
    private String paymentMethodName;

    @Schema(description = "支付类型")
    @Update
    private PaymentType type;

    @Size(max = 200)
    @Schema(description = "支付说明")
    @Update
    private String note;

    @Size(max = 100)
    @Schema(description = "付款人姓名")
    @Update
    private String payerName;

    @Schema(description = "付款账号")
    @Update
    private String payerAccount;

    @Size(max = 20)
    @Schema(description = "付款人手机号")
    @Update
    private String payerMobilePhone;

    @Schema(description = "支付金额")
    @Update
    private Integer amount;

    @Schema(description = "支付手续费")
    @Update
    private Integer fee;

    @Schema(description = "已退金额")
    @Update
    private Integer refundAmount;

    @Size(max = 50)
    @Schema(description = "支付方式")
    @Update
    private String paymentMethod;

    @Schema(description = "退款优先级")
    @Update
    private Integer refundPriority;

    @Schema(description = "付款时间")
    @Update
    private Date payTime;

    @Schema(description = "交易完成时间")
    @Update
    private Date finishedTime;

    @Size(max = 50)
    @Schema(description = "支付操作者")
    @Update
    private String operator;

    @Schema(description = "交易结果")
    @Update
    private PaymentStatus status;

    @Size(max = 100)
    @Schema(description = "第三方或银行交易流水号")
    @Update
    private String returnSn;

    @Size(max = 100)
    @Schema(description = "交易返回码")
    @Update
    private String returnCode;

    @Size(max = 500)
    @Schema(description = "交易返回信息")
    @Update
    private String returnInfo;

    @Schema(description = "交易返回凭证")
    @Update
    private String returnStub;

    @Size(max = 50)
    @Schema(description = "对账状态")
    @Update
    private String checkStatus;

    @Size(max = 50)
    @Schema(description = "对账结果")
    @Update
    private String checkResult;

    @Schema(description = "对账时间")
    @Update
    private Date checkTime;

    @Schema(description = "校验码")
    @Update
    private String checkCode;

    @Schema(description = "退款单号")
    @Update
    private String refundSn;

    @Schema(description = "第三方退款单号")
    @Update
    private String outRefundSn;

    public EditPaymentReq() {
    }

    public EditPaymentReq(String sn) {
        this.sn = sn;
    }
}
