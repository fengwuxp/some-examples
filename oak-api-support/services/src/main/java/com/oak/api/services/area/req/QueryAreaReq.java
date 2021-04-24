package com.oak.api.services.area.req;

import com.levin.commons.dao.annotation.Contains;
import com.levin.commons.dao.annotation.misc.Fetch;
import com.oak.api.entities.system.E_Area;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 查询地区信息
 * 2020-6-16 10:25:17
 */
@Schema(description = "查询地区信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class QueryAreaReq extends ApiBaseQueryReq {

    @Schema(description = "地区编码")
    private String id;

    @Schema(description = "地区父ID")
    private String parentId;

    @Schema(description = "地区启用状态")
    private Boolean status;

    @Schema(description = "地区名称")
    private String name;

    @Schema(description = "地区名称")
    @Contains(E_Area.name)
    private String likeName;

    @Schema(description = "省直区县")
    private Boolean directly;

    @Schema(description = "第三方地区编码")
    private String thirdCode;

    @Schema(description = "是否为市区")
    private Boolean urban;

    @Schema(description = "查询的层级")
    private Integer level;

    @Schema(description = "加载上级地区")
    @Fetch(value = "parent", condition = "#_val==true")
    private Boolean loadParent;

    public QueryAreaReq() {
    }

    public QueryAreaReq(String id) {
        this.id = id;
    }
}
