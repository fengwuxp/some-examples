package com.oak.cms.services.advposition.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.oak.cms.enums.AdvDisplayType;
import com.oak.cms.enums.AdvType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑广告位信息
 *  2020-5-28 15:10:06
 */
@Schema(description = "编辑广告位信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditAdvPositionReq extends ApiBaseReq {

    @Schema(description = "广告位置id")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Size(max = 32)
    @Schema(description = "引用编码")
    @Update
    private String code;

    @Size(max = 64)
    @Schema(description = "广告位置名称")
    @Update
    private String name;

    @Schema(description = "广告位简介")
    @Update
    private String description;

    @Schema(description = "广告类别")
    @Update
    private AdvType type;

    @Schema(description = "广告展示方式")
    @Update
    private AdvDisplayType displayType;

    @Schema(description = "广告位是否启用")
    @Update
    private Boolean enabled;

    @Schema(description = "广告位宽度")
    @Update
    private Integer width;

    @Schema(description = "广告位高度")
    @Update
    private Integer height;

    @Schema(description = "广告位单价")
    @Update
    private Integer price;

    @Schema(description = "拥有的广告数")
    @Update
    private Integer num;

    @Schema(description = "广告位点击率")
    @Update
    private Integer clickNum;

    @Size(max = 512)
    @Schema(description = "广告位默认内容")
    @Update
    private String defaultContent;

    @Size(max = 50)
    @Schema(description = "归属地区id")
    @Update
    private String areaId;

    @Size(max = 64)
    @Schema(description = "广告规格")
    @Update
    private String spec;

    @Schema(description = "创建日期")
    @Update
    private Date crateTime;

    public EditAdvPositionReq() {
    }

    public EditAdvPositionReq(Long id) {
        this.id = id;
    }
}
