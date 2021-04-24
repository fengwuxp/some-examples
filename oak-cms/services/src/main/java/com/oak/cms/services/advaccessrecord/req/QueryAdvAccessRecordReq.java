package com.oak.cms.services.advaccessrecord.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.model.ApiBaseQueryReq;
import com.oak.cms.enums.AdvAccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;
/**
 *  查询广告访问记录
 *  2020-5-28 15:15:29
 */
@Schema(description = "查询广告访问记录")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryAdvAccessRecordReq extends ApiBaseQueryReq {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "广告id")
    private Long advId;

    @Schema(description = "广告访问类型")
    private AdvAccessType accessType;

    @Schema(description = "最小曝光或点击日期")
    @Gte("crateTime")
    private Date minCrateTime;

    @Schema(description = "最大曝光或点击日期")
    @Lte("crateTime")
    private Date maxCrateTime;

    @Schema(description = "访问客户端的ip（不同的ip才算是有效点击或者是曝光）")
    private String ip;

    public QueryAdvAccessRecordReq() {
    }

    public QueryAdvAccessRecordReq(Long id) {
        this.id = id;
    }
}
