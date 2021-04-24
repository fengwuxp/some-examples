package com.oak.cms.services.advaccessrecord.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.cms.enums.AdvAccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  创建AdvAccessRecord
 *  2020-5-28 15:15:29
 */
@Schema(description = "创建CreateAdvAccessRecordReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateAdvAccessRecordReq extends ApiBaseReq {

    @Schema(description = "广告id")
    @NotNull
    private Long advId;

    @Schema(description = "广告访问类型")
    @NotNull
    private AdvAccessType accessType;

    @Schema(description = "访问客户端的ip（不同的ip才算是有效点击或者是曝光）")
    @NotNull
    @Size(max = 20)
    private String ip;

}
