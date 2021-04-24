package com.oak.member.entities.fission;

import com.oak.member.entities.Member;
import com.oak.member.enums.fission.MemberCommissionStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author wxup
 */
@Entity
@Schema(description = "用户发展下线的佣金")
@Table(name = "t_member_commission")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"member"})
public class MemberCommission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    @Id
    private Long id;

    @Schema(description = "用户信息")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "id"))
    private Member member;

    @Schema(description = "订单笔数")
    @Column(name = "order_num", nullable = false)
    private Integer orderNum = 0;

    @Schema(description = "订单金额（分）")
    @Column(name = "order_amount", nullable = false)
    private Long orderAmount = 0L;

    @Schema(description = "累计佣金金额（分）")
    @Column(name = "amount", nullable = false)
    private Long amount = 0L;

    @Schema(description = "已结算金额（分）")
    @Column(name = "settled_amount", nullable = false)
    private Long settledAmount = 0L;

    @Schema(description = "状态")
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberCommissionStatus status;

    @Schema(description = "状态变更日期")
    @Column(name = "status_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date statusTime;

    @Schema(description = "创建时间")
    @Column(name = "create_time", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false, length = 19)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date lastUpdateTime;
}
