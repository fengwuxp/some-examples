package com.oak.member.services.accountlog.info;

import com.oak.member.enums.AccountOperatorValueType;
import com.oak.member.enums.AccountStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 会员账户信息日志
 * 2020-6-5 16:20:13
 */
@Schema(description = "会员账户信息日志")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString()
public class MemberAccountLogInfo implements Serializable {

    @Schema(description = "日志ID")
    private Long id;

    @Schema(description = "流水号")
    private String sn;

    @Schema(description = "业务类型")
    private String businessType;

    @Schema(description = "业务类型简要描述")
    private String businessTypeDesc;

    @Schema(description = "关联的目标记录")
    private String targetId;

    @Schema(description = "操作值类型")
    private AccountOperatorValueType valueType;

    @Schema(description = "当前业务操作影响到的值，正数的表示增加，负数表示减少，不影响使用则为：0")
    private Integer operatorValue;

    @Schema(description = "当前值")
    private Integer currentValue;

    @Schema(description = "当前业务操作影响到的冻结值，正数的表示增加，负数表示减少，不影响使用则为：0")
    private Integer frozenValue;

    @Schema(description = "当前冻结的值")
    private Integer currentFrozenValue;

    @Schema(description = "会员ID")
    private Long memberId;

    @Schema(description = "账户状态")
    private AccountStatus status;

    @Schema(description = "操作者")
    private String operator;

    @Schema(description = "日志描述")
    private String description;

    @Schema(description = "校验码")
    private String checkCode;

    @Schema(description = "日志日期")
    private Date createTime;


}
