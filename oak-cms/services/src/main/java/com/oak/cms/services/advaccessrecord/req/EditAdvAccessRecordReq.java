package com.oak.cms.services.advaccessrecord.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.oak.cms.enums.AdvAccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑广告访问记录
 *  2020-5-28 15:15:29
 */
@Schema(description = "编辑广告访问记录")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditAdvAccessRecordReq extends ApiBaseReq {

    @Schema(description = "id")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "广告id")
    @Update
    private Long advId;

    @Schema(description = "广告访问类型")
    @Update
    private AdvAccessType accessType;

    @Schema(description = "曝光或点击日期")
    @Update
    private Date crateTime;

    @Size(max = 20)
    @Schema(description = "访问客户端的ip（不同的ip才算是有效点击或者是曝光）")
    @Update
    private String ip;

    public EditAdvAccessRecordReq() {
    }

    public EditAdvAccessRecordReq(Long id) {
        this.id = id;
    }
}
