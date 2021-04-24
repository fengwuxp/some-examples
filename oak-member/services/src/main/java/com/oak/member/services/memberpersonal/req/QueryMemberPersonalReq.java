package com.oak.member.services.memberpersonal.req;

import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.Accessors;
import com.levin.commons.dao.annotation.*;
import com.levin.commons.dao.annotation.misc.Fetch;
import java.util.Date;
/**
 *  查询个人实名信息
 *  2020-9-9 11:56:03
 */
@Schema(description = "查询个人实名信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryMemberPersonalReq extends ApiBaseQueryReq {

    @Schema(description = "用户id")
    private Long id;

    @Schema(description = "真实姓名")
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

    @Schema(description = "正面身份证照")
    private String facePic;

    @Schema(description = "反面身份证照")
    private String backPic;

    @Schema(description = "手持身份证照")
    private String handPic;

    @Schema(description = "核验人员")
    private String checkOperator;

    @Schema(description = "最小核验日期")
    @Gte("checkTime")
    private Date minCheckTime;

    @Schema(description = "最大核验日期")
    @Lte("checkTime")
    private Date maxCheckTime;

    @Schema(description = "核验结果，为空表示未核验")
    private Boolean checkResult;

    @Schema(description = "核验结果信息")
    private String checkInfo;

    @Schema(description = "最小创建日期")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建日期")
    @Lte("createTime")
    private Date maxCreateTime;

    @Schema(description = "最小更新时间")
    @Gte("lastUpdateTime")
    private Date minLastUpdateTime;

    @Schema(description = "最大更新时间")
    @Lte("lastUpdateTime")
    private Date maxLastUpdateTime;

    public QueryMemberPersonalReq() {
    }

    public QueryMemberPersonalReq(Long id) {
        this.id = id;
    }
}
