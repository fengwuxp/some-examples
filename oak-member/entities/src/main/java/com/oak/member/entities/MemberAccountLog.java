package com.oak.member.entities;

import com.oak.member.enums.AccountOperatorValueType;
import com.oak.member.enums.AccountStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

/**
 * @author wxup
 */
@Schema(description = "会员账户信息日志")
@Entity
@Table(name = "t_member_account_log", indexes = {
        @Index(name = "index_member_account_log_member_id", columnList = "member_id"),
        @Index(name = "index_member_account_log_sn", columnList = "sn"),
        @Index(name = "index_member_account_type_and_id", columnList = "business_type"),
        @Index(name = "index_member_account_type_and_id", columnList = "target_id")
})
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"member"})
public class MemberAccountLog implements java.io.Serializable {

    private static final long serialVersionUID = -5168172827598435580L;

    @Id
    @Schema(description = "日志ID")
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "流水号")
    @Column(name = "sn", nullable = false, length = 32)
    private String sn;

    @Schema(description = "业务类型", example = "例如：提现、充值、购买消费等")
    @Column(name = "business_type", length = 32, nullable = false)
    private String businessType;

    @Schema(description = "关联的目标记录")
    @Column(name = "target_id", length = 64)
    private String targetId;

    @Schema(description = "操作值类型")
    @Column(name = "operator_value_type", length = 16, nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountOperatorValueType valueType;

    @Schema(description = "当前业务操作影响到的值，正数的表示增加，负数表示减少，不影响使用则为：0")
    @Column(name = "operator_value", nullable = false)
    private Integer operatorValue;

    @Schema(description = "被当前业务操作影响到的当前值")
    @Column(name = "current_value", length = 32, nullable = false)
    private Integer currentValue;

    @Schema(description = "当前业务操作影响到的冻结值，正数的表示增加，负数表示减少，不影响使用则为：0")
    @Column(name = "frozen_value", nullable = false)
    private Integer frozenValue;

    @Schema(description = "当前冻结的值")
    @Column(name = "current_frozen_value", length = 32, nullable = false)
    private Integer currentFrozenValue;

    @Schema(description = "会员ID")
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Schema(description = "会员")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @Schema(description = "账户状态")
    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Schema(description = "操作者")
    @Column(name = "operator", nullable = false, length = 16)
    private String operator;

    @Schema(description = "日志描述")
    @Column(name = "description", length = 512)
    private String description;

//    @Schema(description = "校验码")
//    @Column(name = "check_code")
//    private String checkCode;

    @Schema(description = "日志日期")
    @Column(name = "create_time", nullable = false, length = 19)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

}
