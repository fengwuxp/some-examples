package com.oak.api.services.area.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



/**
 *  创建Area
 *  2020-6-16 10:25:17
 * @author wxup
 */
@Schema(description = "创建CreateAreaReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateAreaReq extends ApiBaseReq {

    @Schema(description = "地区父ID")
    @Size(max = 50)
    private String parentId;

    @Schema(description = "地区名称")
    @NotNull
    @Size(max = 200)
    private String name;

    @Schema(description = "地区简称")
    @NotNull
    @Size(max = 200)
    private String shortName;

    @Schema(description = "地区全称")
    @NotNull
    @Size(max = 500)
    private String fullName;

    @Schema(description = "经度")
    @NotNull
    private Float longitude;

    @Schema(description = "纬度")
    @NotNull
    private Float latitude;

    @Schema(description = "地区深度，从0开始，0表示国家")
    @NotNull
    private Integer level;

    @Schema(description = "层级排序")
    @Size(max = 20)
    private String levelPath;

    @Schema(description = "排序")
    @NotNull
    private Short sort;

    @Schema(description = "地区启用状态")
    @NotNull
    private Boolean status;

    @Schema(description = "第三方地区编码")
    private String thirdCode;

    @Schema(description = "是否为市区")
    private Boolean urban;

}
