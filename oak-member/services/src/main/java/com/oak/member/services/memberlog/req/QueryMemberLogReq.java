package com.oak.member.services.memberlog.req;

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
 *  查询会员日志
 *  2020-6-3 14:07:02
 */
@Schema(description = "查询会员日志")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryMemberLogReq extends ApiBaseQueryReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "会员ID")
    private Long memberId;

    @Schema(description = "会员显示名称")
    private String showName;

    @Schema(description = "操作类型")
    private String type;

    @Schema(description = "IP")
    private String ip;

    @Schema(description = "操作者")
    private String operator;

    @Schema(description = "操作描述")
    private String description;

    @Schema(description = "最小操作日期")
    @Gte("operatingTime")
    private Date minOperatingTime;

    @Schema(description = "最大操作日期")
    @Lte("operatingTime")
    private Date maxOperatingTime;

    @Schema(description = "最小创建时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建时间")
    @Lte("createTime")
    private Date maxCreateTime;

    @Schema(description = "最小更新时间")
    @Gte("lastUpdateTime")
    private Date minLastUpdateTime;

    @Schema(description = "最大更新时间")
    @Lte("lastUpdateTime")
    private Date maxLastUpdateTime;

    public QueryMemberLogReq() {
    }

    public QueryMemberLogReq(Long id) {
        this.id = id;
    }
}
