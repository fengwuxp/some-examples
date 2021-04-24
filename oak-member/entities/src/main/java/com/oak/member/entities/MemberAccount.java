package com.oak.member.entities;

import com.oak.member.enums.AccountStatus;
import com.oak.member.enums.VipGrade;
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
@Schema(description = "会员账户信息")
@Entity
@Table(name = "t_member_account")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(of = "id")
@ToString(exclude = {"member"})
public class MemberAccount implements Serializable {

    private static final long serialVersionUID = 374796685682830177L;


    @Id
    @Schema(description = "用户id")
    private Long id;


    @Schema(description = "可用余额（单位分）")
    @Column(name = "money", nullable = false)
    private Integer money;


    @Schema(description = "冻结余额（单位分）")
    @Column(name = "frozen_money", nullable = false)
    private Integer frozenMoney;


    @Schema(description = "积分")
    @Column(name = "points", nullable = false)
    private Integer points;

    @Schema(description = "冻结积分")
    @Column(name = "frozen_points", nullable = false)
    private Integer frozenPoints;


    @Schema(description = "代金券")
    @Column(name = "coupon", nullable = false)
    private Integer coupon;

    @Schema(description = "冻结代金券")
    @Column(name = "frozen_coupon", nullable = false)
    private Integer frozenCoupon;

    @Schema(description = "账户状态")
    @Column(name = "status", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Schema(description = "用户会员vip级别")
    @Column(name = "vip_grade", nullable = false, length = 16)
    @Enumerated(EnumType.STRING)
    private VipGrade vipGrade;

    @Schema(description = "会员信息")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable = false, updatable = false, foreignKey = @ForeignKey(name = "id"))
    private Member member;

    @Schema(description = "校验码")
    @Column(name = "check_code", nullable = false)
    private String checkCode;

    @Schema(description = "创建时间")
    @Column(name = "create_time", nullable = false, length = 19)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false, length = 19)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date lastUpdateTime;

    @Schema(description = "数据版本号")
    @Version()
    @Column(name = "version_no", nullable = false)
    private Long version;

    public MemberAccount() {
    }

    /**
     * 可用余额
     *
     * @return
     */
    @Transient
    public Integer getAvailableMoney() {
        return money - frozenMoney;
    }

    /**
     * 可用积分
     *
     * @return
     */
    @Transient
    public Integer getAvailablePoints() {
        return points - frozenPoints;
    }

    /**
     * 可用代金券金额
     *
     * @return
     */
    @Transient
    public Integer getAvailableCoupon() {
        return coupon - frozenCoupon;
    }


}
