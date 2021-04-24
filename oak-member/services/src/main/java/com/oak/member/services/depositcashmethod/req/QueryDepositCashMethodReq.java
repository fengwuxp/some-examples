package com.oak.member.services.depositcashmethod.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 *  查询提现方式
 *  2020-6-8 16:51:49
 */
@Schema(description = "查询提现方式")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryDepositCashMethodReq extends ApiBaseQueryReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "提现方式代码")
    private String code;


    @Schema(description = "名称")
    private String name;

    @Schema(description = "是否线上转账")
    private Boolean online;


    @Schema(description = "最小创建时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建时间")
    @Lte("createTime")
    private Date maxCreateTime;


    public QueryDepositCashMethodReq() {
    }

    public QueryDepositCashMethodReq(Long id) {
        this.id = id;
    }
}
