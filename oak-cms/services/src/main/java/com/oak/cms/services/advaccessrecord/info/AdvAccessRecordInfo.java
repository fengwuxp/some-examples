package com.oak.cms.services.advaccessrecord.info;

import com.oak.cms.enums.AdvAccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 广告访问记录
 * 2020-5-28 15:15:29
 */
@Schema(description = "广告访问记录")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class AdvAccessRecordInfo implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "广告id")
    private Long advId;

    @Schema(description = "广告访问类型")
    private AdvAccessType accessType;

    @Schema(description = "曝光或点击日期")
    private Date crateTime;

    @Schema(description = "访问客户端的ip（不同的ip才算是有效点击或者是曝光）")
    private String ip;


}
