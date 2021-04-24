package com.oak.provider.member.services.personal.req;

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
 *  查询个人实名信息
 *  2020-3-5 17:08:12
 */
@Schema(description = "查询个人实名信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryMemberPersonalReq extends ApiBaseQueryReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "身份证号")
    private String idNumber;

    @Schema(description = "最小证件生效日期")
    @Gte("beginDate")
    private Date minBeginDate;

    @Schema(description = "最大证件生效日期")
    @Lte("beginDate")
    private Date maxBeginDate;

    @Schema(description = "最小证件截止日期")
    @Gte("endDate")
    private Date minEndDate;

    @Schema(description = "最大证件截止日期")
    @Lte("endDate")
    private Date maxEndDate;

    @Schema(description = "核验人员")
    private String checkOperator;

    @Schema(description = "最小核验日期")
    @Gte("checkTime")
    private Date minCheckTime;

    @Schema(description = "最大核验日期")
    @Lte("checkTime")
    private Date maxCheckTime;

    @Schema(description = "核验结果")
    private Boolean checkResult;

    @Schema(description = "最小登记日期")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大登记日期")
    @Lte("createTime")
    private Date maxCreateTime;

    public QueryMemberPersonalReq() {
    }

    public QueryMemberPersonalReq(Long id) {
        this.id = id;
    }
}
