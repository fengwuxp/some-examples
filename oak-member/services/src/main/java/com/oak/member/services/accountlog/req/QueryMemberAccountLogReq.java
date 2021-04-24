package com.oak.member.services.accountlog.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.member.enums.AccountOperatorValueType;
import com.oak.member.enums.AccountStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 *  查询会员账户信息日志
 *  2020-6-5 16:20:13
 */
@Schema(description = "查询会员账户信息日志")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class QueryMemberAccountLogReq extends ApiBaseQueryReq {

    @Schema(description = "日志ID")
    private Long id;

    @Schema(description = "流水号")
    private String sn;

    @Schema(description = "业务类型")
    private String businessType;

    @Schema(description = "关联的目标记录")
    private String targetId;

    @Schema(description = "操作值类型")
    private AccountOperatorValueType valueType;

    @Schema(description = "会员ID")
    private Long memberId;

    @Schema(description = "加载会员")
    @Fetch(value = "member", condition = "#_val==true")
    private Boolean loadMember;

    @Schema(description = "账户状态")
    private AccountStatus status;

    @Schema(description = "操作者")
    private String operator;

    @Schema(description = "最小日志日期")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大日志日期")
    @Lte("createTime")
    private Date maxCreateTime;

    public QueryMemberAccountLogReq() {
    }

    public QueryMemberAccountLogReq(Long id) {
        this.id = id;
    }
}
