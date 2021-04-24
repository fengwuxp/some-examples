package com.oak.cms.services.advposition.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.cms.enums.AdvDisplayType;
import com.oak.cms.enums.AdvType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  创建AdvPosition
 *  2020-5-28 15:10:06
 */
@Schema(description = "创建CreateAdvPositionReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateAdvPositionReq extends ApiBaseReq {

    @Schema(description = "引用编码")
    @NotNull
    @Size(max = 32)
    private String code;

    @Schema(description = "广告位置名称")
    @NotNull
    @Size(max = 64)
    private String name;

    @Schema(description = "广告位简介")
    @NotNull
    private String description;

    @Schema(description = "广告类别")
    @NotNull
    private AdvType type;

    @Schema(description = "广告展示方式")
    @NotNull
    private AdvDisplayType displayType;

    @Schema(description = "广告位宽度")
    @NotNull
    private Integer width;

    @Schema(description = "广告位高度")
    @NotNull
    private Integer height;

    @Schema(description = "广告位单价")
    private Integer price;

    @Schema(description = "拥有的广告数")
    private Integer num;

    @Schema(description = "广告位默认内容")
    @NotNull
    @Size(max = 512)
    private String defaultContent;

    @Schema(description = "归属地区id")
    @Size(max = 50)
    private String areaId;

    @Schema(description = "广告规格")
    @Size(max = 64)
    private String spec;

}
