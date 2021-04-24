package com.oak.member.business.deposit.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.logic.deposit.req.TransferRequest;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Classname BankCardWithdrawRequest
 * @Description 手动转账方式
 * @Date 2020/6/9 2:32
 * @Created by 44487
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
@Schema(description = "手动转账方式")
public class ManualTransferReq extends ApiBaseReq implements TransferRequest {

    @Schema(description = "提现申请记录id编号")
    private Long id;

    @Schema(description = "操作人员")
    @InjectField("#admin.name")
    private String operator;

    @Schema(description = "提现方式")
    private String depositCashCode;

    @Schema(description = "是否成功")
    private Boolean success;

    @Schema(description = "支付结果")
    private String paymentResult;

    @Schema(description = "支付流水号")
    private String paymentSn;

    @Schema(description = "支付凭证图")
    private String payCertificate;

    @Schema(description = "实际提现金额")
    private Integer actualFee;

    @Override
    public Boolean isSuccess() {
        return this.success;
    }
}
