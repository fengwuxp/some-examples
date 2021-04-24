package com.oak.api.services.client.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.enums.ClientType;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 查询ClientChannel
 * 2020-3-11 11:05:46
 */
@Schema(description = "查询ClientChannel")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryClientChannelReq extends ApiBaseQueryReq {

    @Schema(description = "编号")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "客户端类型")
    private ClientType clientType;

    @Schema(description = "启用")
    private Boolean enabled;

    @Schema(description = "最小创建日期")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建日期")
    @Lte("createTime")
    private Date maxCreateTime;

    public QueryClientChannelReq() {
    }

    public QueryClientChannelReq(String code) {
        this.code = code;
    }
}
