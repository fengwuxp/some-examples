package com.oak.api.services.area.info;

import com.levin.commons.service.domain.Desc;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 地区信息
 * 2020-6-16 10:25:17
 *
 * @author wxup
 */
@Schema(description = "地区信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {"parentInfo"})
public class AreaInfo implements Serializable {

    @Schema(description = "地区编码")
    private String id;

    @Schema(description = "地区父ID")
    private String parentId;

    @Schema(description = "地区名称")
    private String name;

    @Schema(description = "地区简称")
    private String shortName;

    @Schema(description = "地区全称")
    private String fullName;

    @Schema(description = "经度")
    private Float longitude;

    @Schema(description = "纬度")
    private Float latitude;

    @Schema(description = "地区深度，从0开始，0表示国家")
    private Integer level;

    @Schema(description = "层级排序")
    private String levelPath;

    @Schema(description = "排序")
    private Short sort;

    @Schema(description = "地区启用状态")
    private Boolean status;

    @Schema(description = "省直区县")
    private Boolean directly;

    @Schema(description = "第三方地区编码")
    private String thirdCode;

    @Schema(description = "是否为市区")
    private Boolean urban;

    @Desc(code = "parent")
    @Schema(description = "上级地区")
    private AreaInfo parentInfo;


}
