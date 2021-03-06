package com.oak.provider.member.services.accountlog.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.oak.provider.member.enums.AccountStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  编辑会员账户信息日志
 *  2020-2-18 14:06:41
 */
@Schema(description = "编辑会员账户信息日志")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditMemberAccountLogReq extends ApiBaseReq {

    @Schema(description = "日志ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Size(max = 100)
    @Schema(description = "操作类型")
    @Update
    private String type;

    @Schema(description = "会员ID")
    @Update
    private Long memberId;

    @Schema(description = "调整余额")
    @Update
    private Integer money;

    @Schema(description = "当前余额")
    @Update
    private Integer currMoney;

    @Schema(description = "调整冻结余额")
    @Update
    private Integer frozenMoney;

    @Schema(description = "当前冻结余额")
    @Update
    private Integer currFrozenMoney;

    @Schema(description = "积分")
    @Update
    private Integer points;

    @Schema(description = "当前可用积分")
    @Update
    private Integer currPoints;

    @Schema(description = "调整冻结积分")
    @Update
    private Integer frozenPoints;

    @Schema(description = "当前冻结积分")
    @Update
    private Integer currFrozenPoints;

    @Schema(description = "调整代金券")
    @Update
    private Integer coupon;

    @Schema(description = "当前代金券金额")
    @Update
    private Integer currCoupon;

    @Schema(description = "调整冻结代金券")
    @Update
    private Integer frozenCoupon;

    @Schema(description = "当前冻结代金券金额")
    @Update
    private Integer currFrozenCoupon;

    @Schema(description = "账户状态")
    @Update
    private AccountStatus status;

    @Size(max = 100)
    @Schema(description = "操作者")
    @Update
    private String operator;

    @Size(max = 1000)
    @Schema(description = "日志描述")
    @Update
    private String description;

    @Size(max = 100)
    @Schema(description = "订单流水号")
    @Update
    private String orderSn;

    @Schema(description = "校验码")
    @Update
    private String checkCode;

    public EditMemberAccountLogReq() {
    }

    public EditMemberAccountLogReq(Long id) {
        this.id = id;
    }
}
