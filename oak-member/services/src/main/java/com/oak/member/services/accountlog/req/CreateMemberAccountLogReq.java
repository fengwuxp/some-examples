package com.oak.member.services.accountlog.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.member.enums.AccountOperatorValueType;
import com.oak.member.enums.AccountStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  创建MemberAccountLog
 *  2020-6-5 16:20:13
 */
@Schema(description = "创建CreateMemberAccountLogReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateMemberAccountLogReq extends ApiBaseReq {

    @Schema(description = "业务类型")
    @NotNull
    @Size(max = 32)
    private String businessType;

    @Schema(description = "关联的目标记录")
    @Size(max = 64)
    private String targetId;

    @Schema(description = "操作值类型")
    @NotNull
    private AccountOperatorValueType valueType;

    @Schema(description = "当前业务操作影响到的值，正数的表示增加，负数表示减少，不影响使用则为：0")
    @NotNull
    private Integer operatorValue;

    @Schema(description = "当前值")
    private Integer currentValue;

    @Schema(description = "当前业务操作影响到的冻结值，正数的表示增加，负数表示减少，不影响使用则为：0")
    private Integer frozenValue;

    @Schema(description = "当前冻结的值")
    private Integer currentFrozenValue;

    @Schema(description = "会员ID")
    @NotNull
    private Long memberId;

    @Schema(description = "账户状态")
    @NotNull
    private AccountStatus status;

    @Schema(description = "操作者")
    @NotNull
    @Size(max = 16)
    private String operator;

    @Schema(description = "日志描述")
    @Size(max = 512)
    private String description;


}
