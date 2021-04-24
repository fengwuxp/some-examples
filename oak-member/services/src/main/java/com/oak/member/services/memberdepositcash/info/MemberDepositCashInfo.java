package com.oak.member.services.memberdepositcash.info;

import com.levin.commons.dao.annotation.Ignore;
import com.oak.member.enums.WithdrawStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
* 会员账户提现记录
* 2020-6-8 23:10:52
*/
@Schema(description ="会员账户提现记录")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class MemberDepositCashInfo implements Serializable {

        @Schema(description = "id")
        private Long id;

        @Schema(description = "提现单号")
        private String sn;

        @Schema(description = "提现金额 单位（分）")
        private Integer amount;

        @Schema(description = "手续费用")
        private Integer handlingFee;

        @Schema(description = "实际到账金额")
        private Integer actualAmount;

        @Schema(description = "提现状态")
        private WithdrawStatus withdrawStatus;

        @Schema(description = "申请时间")
        private Date applyDate;

        @Schema(description = "审核时间")
        private Date auditDate;

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

        @Schema(description = "支付时间")
        private Date payDate;

        @Schema(description = "日志日期")
        private Date createTime;

        @Schema(description = "更新时间")
        private Date lastUpdateTime;

        @Schema(description = "选定提现方式编号")
        private String withdrawCode;

        @Schema(description = "会员名称")
        @Ignore
        private String memberName;

        @Schema(description = "会员手机号")
        @Ignore
        private String memberPhone;
}
