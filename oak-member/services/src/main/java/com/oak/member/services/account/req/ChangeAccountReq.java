package com.oak.member.services.account.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author wxup
 */
@Schema(description = "变更账号信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class ChangeAccountReq extends ApiBaseReq {

    @Schema(description = "用户id")
    @NotNull
    private Long id;

    @Schema(description = "调整的金额（单位分）正数表示添加，负数表示减少")
    private Integer money;

    @Schema(description = "调整的冻结余额（单位分）,正数表示添加，负数表示减少")
    private Integer frozenMoney;

    @Schema(description = "调整的积分,正数表示添加，负数表示减少")
    private Integer points;

    @Schema(description = "调整的冻结积分，正数表示添加，负数表示减少")
    private Integer frozenPoints;

    @Schema(description = "调整的代金券，正数表示添加，负数表示减少")
    private Integer coupon;

    @Schema(description = "调整的冻结代金券，正数表示添加，负数表示减少")
    private Integer frozenCoupon;

    @Schema(description = "业务类型")
    @NotNull
    @Size(max = 32)
    private String businessType;

    @Schema(description = "关联的目标记录")
    @Size(max = 64)
    private String targetId;

    @Schema(description = "日志描述")
    @Size(max = 512)
    private String description;

    @Schema(description = "操作者")
    @NotNull
    @Size(max = 16)
    private String operator;

}
