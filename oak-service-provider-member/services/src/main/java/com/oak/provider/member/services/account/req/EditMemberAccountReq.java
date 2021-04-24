package com.oak.provider.member.services.account.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.oak.provider.member.enums.AccountStatus;
import com.oak.provider.member.enums.VipGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑会员账户信息
 *  2020-2-6 15:42:46
 */
@Schema(description = "编辑会员账户信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditMemberAccountReq extends ApiBaseReq {

    @Schema(description = "id")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "可用余额（单位分）")
    @Update
    private Integer money;

    @Schema(description = "冻结余额（单位分）")
    @Update
    private Integer frozenMoney;

    @Schema(description = "积分")
    @Update
    private Integer points;

    @Schema(description = "冻结积分")
    @Update
    private Integer frozenPoints;

    @Schema(description = "代金券")
    @Update
    private Integer coupon;

    @Schema(description = "冻结代金券")
    @Update
    private Integer frozenCoupon;

    @Schema(description = "账户状态")
    @Update
    private AccountStatus status;

    @Schema(description = "用户会员vip级别")
    @Update
    private VipGrade vipGrade;

    //@Schema(description = "校验码")
    //@Update
    //private String checkCode;

    @Schema(description = "排序代码")
    @Update
    private Integer orderCode;

    @Schema(description = "是否允许")
    @Update
    private Boolean enable;

    @Schema(description = "是否可编辑")
    @Update
    private Boolean editable;

    @Schema(description = "更新时间")
    @Update
    private Date lastUpdateTime;

    @Size(max = 1000)
    @Schema(description = "备注")
    @Update
    private String remark;

    public EditMemberAccountReq() {
    }

    public EditMemberAccountReq(Long id) {
        this.id = id;
    }
}
