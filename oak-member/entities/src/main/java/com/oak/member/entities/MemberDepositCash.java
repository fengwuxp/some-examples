package com.oak.member.entities;

import com.oak.member.enums.WithdrawStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

/**
 * @Classname WithdrawLog
 * @Description 会员提现记录
 * @Date 2020/5/9 15:33
 * @Created by 44487
 */
@Entity
@Schema(description = "会员账户提现记录")
@Table(name = "t_deposit_cash", indexes = {
        @Index(name = "index_deposit_cash_member_id", columnList = "member_id")
})
@Data
@EqualsAndHashCode(of = "id")
@Accessors(chain = true)
@ToString()
public class MemberDepositCash implements Serializable {

    private static final long serialVersionUID = -6595762160651382929L;

    @Id
    @Schema(description = "id")
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "提现单号")
    @Column(name = "sn", nullable = false, length = 32)
    private String sn;

    @Schema(description = "提现金额 单位（分）")
    @Column(name = "amount", nullable = false)
    private Integer amount = 0;

    @Schema(description = "手续费用")
    @Column(name = "handling_fee", nullable = false)
    private Integer handlingFee = 0;

    @Schema(description = "实际到账金额")
    @Column(name = "actual_amount", nullable = false)
    private Integer actualAmount = 0;

    @Schema(description = "提现状态")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WithdrawStatus withdrawStatus;

    @Schema(description = "申请时间")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "apply_date", nullable = false)
    private Date applyDate;

    @Schema(description = "审核时间")
    @Column(name = "audit_date", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date auditDate;

    @Schema(description = "支付凭证（指的是图片的路径）")
    @Column(name = "pay_certificate")
    private String payCertificate;

    @Schema(description = "支付流水号")
    @Column(name = "pay_sn", length = 64)
    private String paySn;

    @Schema(description = "审核内容(提现说明)")
    @Column(name = "audit_content", length = 64)
    private String auditContent;

    @Schema(description = "关联会员id")
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Schema(description = "是否线上转账")
    @Column(name = "is_online", nullable = false)
    private Boolean online = true;

    @Schema(description = "提现目标账户名")
    @Column(name = "account_name", nullable = false, length = 32)
    private String accountName;

    @Schema(description = "提现目标账户")
    @Column(name = "account_number", nullable = false, length = 32)
    private String accountNumber;

    @Schema(description = "支付时间")
    @Column(name = "pay_date", length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date payDate;

    @Schema(description = "日志日期")
    @Column(name = "create_time", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false, length = 19)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date lastUpdateTime;

    @Schema(description = "选定提现方式编号")
    @Column(name = "withdraw_code", nullable = false, length = 32)
    private String withdrawCode;

}
