package com.oak.api.services.area.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  编辑地区信息
 *  2020-6-16 10:25:17
 */
@Schema(description = "编辑地区信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditAreaReq extends ApiBaseReq {

    @Schema(description = "地区编码")
    @NotNull
    @Eq(require = true)
    private String id;

    @Size(max = 50)
    @Schema(description = "地区父ID")
    @Update
    private String parentId;

    @Size(max = 200)
    @Schema(description = "地区名称")
    @Update
    private String name;

    @Size(max = 200)
    @Schema(description = "地区简称")
    @Update
    private String shortName;

    @Size(max = 500)
    @Schema(description = "地区全称")
    @Update
    private String fullName;

    @Schema(description = "经度")
    @Update
    private Float longitude;

    @Schema(description = "纬度")
    @Update
    private Float latitude;

    @Schema(description = "地区深度，从0开始，0表示国家")
    @Update
    private Integer level;

    @Size(max = 20)
    @Schema(description = "层级排序")
    @Update
    private String levelPath;

    @Schema(description = "排序")
    @Update
    private Short sort;

    @Schema(description = "地区启用状态")
    @Update
    private Boolean status;

    @Schema(description = "省直区县")
    @Update
    private Boolean directly;

    @Schema(description = "第三方地区编码")
    @Update
    private String thirdCode;

    @Schema(description = "是否为市区")
    @Update
    private Boolean urban;

    public EditAreaReq() {
    }

    public EditAreaReq(String id) {
        this.id = id;
    }
}
