package com.oak.member.services.memberdepositcash.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.oak.member.enums.WithdrawStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑会员账户提现记录
 *  2020-6-8 23:10:52
 */
@Schema(description = "编辑会员账户提现记录")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditMemberDepositCashReq extends ApiBaseReq {

    @Schema(description = "id")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "提现金额 单位（分）")
    @Update
    private Integer amount;

    @Schema(description = "手续费用")
    @Update
    private Integer handlingFee;

    @Schema(description = "实际到账金额")
    @Update
    private Integer actualAmount;

    @Schema(description = "提现状态")
    @Update
    private WithdrawStatus withdrawStatus;

    @Schema(description = "申请时间")
    @Update
    private Date applyDate;

    @Schema(description = "审核时间")
    @Update
    private Date auditDate;

    @Schema(description = "支付凭证（指的是图片的路径）")
    @Update
    private String payCertificate;

    @Size(max = 64)
    @Schema(description = "支付流水号")
    @Update
    private String paySn;

    @Size(max = 64)
    @Schema(description = "审核内容(提现说明)")
    @Update
    private String auditContent;

    @Schema(description = "关联会员id")
    @Update
    private Long memberId;

    @Schema(description = "是否线上转账")
    @Update
    private Boolean online;

    @Size(max = 32)
    @Schema(description = "提现目标账户名")
    @Update
    private String accountName;

    @Size(max = 32)
    @Schema(description = "提现目标账户")
    @Update
    private String accountNumber;

    @Schema(description = "支付时间")
    @Update
    private Date payDate;

    @Schema(description = "更新时间")
    @Update
    private Date lastUpdateTime;

    public EditMemberDepositCashReq() {
    }

    public EditMemberDepositCashReq(Long id) {
        this.id = id;
    }
}
