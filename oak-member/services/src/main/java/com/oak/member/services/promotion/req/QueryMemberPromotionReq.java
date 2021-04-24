package com.oak.member.services.promotion.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.member.logic.enums.PromotionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 *  查询用户推广信息
 *  2020-6-3 21:29:08
 */
@Schema(description = "查询用户推广信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class QueryMemberPromotionReq extends ApiBaseQueryReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "推广用户的Id")
    private Long fromMemberId;

    @Schema(description = "加载推广用户")
    @Fetch(value = "member", condition = "#_val==true")
    private Boolean loadMember;

    @Schema(description = "推广类型")
    private PromotionType type;

    @Schema(description = "被推广用户的no（来源注册时）")
    private String toMemberNo;

    @Schema(description = "被推广用户ID（来源注册时）")
    private Long toMemberId;

    @Schema(description = "最小创建时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建时间")
    @Lte("createTime")
    private Date maxCreateTime;

    public QueryMemberPromotionReq() {
    }

    public QueryMemberPromotionReq(Long id) {
        this.id = id;
    }
}
