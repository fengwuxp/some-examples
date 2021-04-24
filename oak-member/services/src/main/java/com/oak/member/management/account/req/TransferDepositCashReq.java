package com.oak.member.management.account.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Classname TransferDepositCashReq
 * @Description 转账提现申请
 * @Date 2020/6/9 2:50
 * @Created by 44487
 */


@Schema(description = "会员提现校验请求")
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TransferDepositCashReq extends ApiBaseReq {

    @Schema(description = "提现ID")
    @NotNull
    private Long id;

    @Schema(description ="操作员")
    @NotNull
    private String operator;

    @Schema(description ="转帐成功，如果是false表示转账失败")
    private Boolean success;

    @Schema(description ="支付凭证")
    private String payCertificate;

    @Schema(description ="结果信息")
    private String transferResult;

    @Schema(description ="转帐返回流水号")
    private String paymentSn;

    @Schema(description = "提现实际产生的手续费")
    private Integer actualFee;

    @Schema(description = "业务类型")
    private String businessType;

}
