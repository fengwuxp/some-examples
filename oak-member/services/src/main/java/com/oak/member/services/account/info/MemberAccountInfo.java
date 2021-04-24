package com.oak.member.services.account.info;

import com.oak.member.enums.AccountStatus;
import com.oak.member.enums.VipGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;


/**
 * 会员账户信息
 * 2020-6-8 10:22:50
 */
@Schema(description = "会员账户信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class MemberAccountInfo implements Serializable {

    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "可用余额（单位分）")
    private Integer money;

    @Schema(description = "冻结余额（单位分）")
    private Integer frozenMoney;

    @Schema(description = "积分")
    private Integer points;

    @Schema(description = "冻结积分")
    private Integer frozenPoints;

    @Schema(description = "代金券")
    private Integer coupon;

    @Schema(description = "冻结代金券")
    private Integer frozenCoupon;

    @Schema(description = "账户状态")
    private AccountStatus status;

    @Schema(description = "用户会员vip级别")
    private VipGrade vipGrade;

    @Schema(description = "校验码")
    private String checkCode;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;

    @Schema(description = "是否具有提现银行卡")
    private Boolean haveBankCard;
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
