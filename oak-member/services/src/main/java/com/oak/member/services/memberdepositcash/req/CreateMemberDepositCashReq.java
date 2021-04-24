package com.oak.member.services.memberdepositcash.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.enums.WithdrawStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  创建MemberDepositCash
 *  2020-6-8 23:10:52
 */
@Schema(description = "创建CreateMemberDepositCashReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMemberDepositCashReq extends ApiBaseReq {

    @Schema(description = "提现状态")
    @NotNull
    private WithdrawStatus withdrawStatus;

    @Schema(description = "提现金额 单位（分）")
    private Integer amount = 0;

    @Schema(description = "手续费用")
    private Integer handlingFee = 0;

    @Schema(description = "实际到账金额")
    private Integer actualAmount = 0;

    @Schema(description = "申请时间")
    @NotNull
    private Date applyDate;

    @Schema(description = "审核时间")
    private Date auditDate;

    @Schema(description = "支付凭证（指的是图片的路径）")
    private String payCertificate;

    @Schema(description = "支付流水号")
    @Size(max = 64)
    private String paySn;

    @Schema(description = "审核内容(提现说明)")
    @Size(max = 64)
    private String auditContent;

    @Schema(description = "关联会员id")
    @NotNull
    private Long memberId;

    @Schema(description = "提现目标账户名")
    @NotNull
    @Size(max = 32)
    private String accountName;

    @Schema(description = "提现目标账户")
    @NotNull
    @Size(max = 32)
    private String accountNumber;

    @Schema(description = "支付时间")
    private Date payDate;

    @Schema(description = "更新时间")
    @NotNull
    private Date lastUpdateTime;

    @Schema(description = "选定提现方式编号")
    @NotNull
    private String withdrawCode;

}
