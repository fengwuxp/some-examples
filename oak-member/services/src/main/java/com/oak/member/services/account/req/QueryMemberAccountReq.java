package com.oak.member.services.account.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.member.enums.AccountStatus;
import com.oak.member.enums.VipGrade;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 查询会员账户信息
 * 2020-6-8 10:22:51
 */
@Schema(description = "查询会员账户信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryMemberAccountReq extends ApiBaseQueryReq {

    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "账户状态")
    private AccountStatus status;

    @Schema(description = "用户会员vip级别")
    private VipGrade vipGrade;

    @Schema(description = "加载会员信息")
    @Fetch(value = "member", condition = "#_val==true")
    private Boolean loadMember;

    @Schema(description = "校验码")
    private String checkCode;

    @Schema(description = "最小创建时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建时间")
    @Lte("createTime")
    private Date maxCreateTime;


    public QueryMemberAccountReq() {
    }

    public QueryMemberAccountReq(Long id) {
        this.id = id;
    }
}
