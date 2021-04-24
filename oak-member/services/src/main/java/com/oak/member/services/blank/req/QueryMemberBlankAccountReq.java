package com.oak.member.services.blank.req;

import com.levin.commons.dao.annotation.Contains;
import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.member.entities.E_MemberBlankAccount;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 查询用户银行账号信息表
 * 2020-6-8 10:07:37
 */
@Schema(description = "查询用户银行账号信息表")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class QueryMemberBlankAccountReq extends ApiBaseQueryReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "账户关联会员id")
    private Long memberId;

    @Schema(description = "账户类型")
    private String blankType;

    @Schema(description = "按照账户类型模糊查询")
    @Contains(E_MemberBlankAccount.blankType)
    private String likeType;

    @Schema(description = "开户行名称")
    private String bankName;

    @Schema(description = "按照开户行名称模糊查询")
    @Contains(E_MemberBlankAccount.T_bankName)
    private String linkBankName;

    @Schema(description = "是否删除")
    private Boolean deleted;

    @Schema(description = "最小日志日期")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大日志日期")
    @Lte("createTime")
    private Date maxCreateTime;

    public QueryMemberBlankAccountReq() {
    }

    public QueryMemberBlankAccountReq(Long id) {
        this.id = id;
    }
}
