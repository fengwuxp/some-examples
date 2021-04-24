package com.oak.member.services.memberdepositcash.req;

import com.levin.commons.dao.annotation.Contains;
import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Ignore;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.member.enums.WithdrawStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 *  查询会员账户提现记录
 *  2020-6-8 23:10:52
 */
@Schema(description = "查询会员账户提现记录")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryMemberDepositCashReq extends ApiBaseQueryReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "提现单号")
    @Contains
    private String sn;

    @Schema(description = "提现金额 单位（分）")
    private Integer amount;

    @Schema(description = "手续费用")
    private Integer handlingFee;

    @Schema(description = "实际到账金额")
    private Integer actualAmount;

    @Schema(description = "提现状态")
    private WithdrawStatus withdrawStatus;

    @Schema(description = "最小申请时间")
    @Gte("applyDate")
    private Date minApplyDate;

    @Schema(description = "最大申请时间")
    @Lte("applyDate")
    private Date maxApplyDate;

    @Schema(description = "最小审核时间")
    @Gte("auditDate")
    private Date minAuditDate;

    @Schema(description = "最大审核时间")
    @Lte("auditDate")
    private Date maxAuditDate;

    @Schema(description = "支付凭证（指的是图片的路径）")
    private String payCertificate;

    @Schema(description = "支付流水号")
    private String paySn;

    @Schema(description = "审核内容(提现说明)")
    private String auditContent;

    @Schema(description = "关联会员id")
    private Long memberId;

    @Schema(description = "是否线上转账")
    private Boolean online;

    @Schema(description = "提现目标账户名")
    private String accountName;

    @Schema(description = "提现目标账户")
    private String accountNumber;

    @Schema(description = "额外搜索内容：搜索包括SN编号，用户手机号，用户名")
    @Ignore
    private String searchName;

    @Schema(description = "是否加载会员部分信息")
    @Ignore
    private Boolean loadMemberInfo = false;


    public QueryMemberDepositCashReq() {
    }

    public QueryMemberDepositCashReq(Long id) {
        this.id = id;
    }
}
