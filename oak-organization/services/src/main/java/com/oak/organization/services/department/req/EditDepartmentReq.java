package com.oak.organization.services.department.req;

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
import java.util.Date;


/**
 *  编辑部门
 *  2020-1-19 14:02:11
 */
@Schema(description = "编辑部门")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditDepartmentReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "组织id")
    @Update
    private Long organizationId;

    @Schema(description = "层级")
    @Update
    private Integer level;

    @Schema(description = "层级排序")
    @Update
    private String levelPath;

    @Schema(description = "父ID")
    @Update
    private Long parentId;

    @Schema(description = "ID路径")
    @Update
    private String idPath;

    @Schema(description = "是否删除")
    @Update
    private Boolean deleted;

    @Schema(description = "名称")
    @Update
    private String name;

    @Schema(description = "排序代码")
    @Update
    private Integer orderCode;

    @Schema(description = "是否允许")
    @Update
    private Boolean enable;

    @Schema(description = "是否可编辑")
    @Update
    private Boolean editable;

    @Schema(description = "更新时间")
    @Update
    private Date lastUpdateTime;

    @Size(max = 1000)
    @Schema(description = "备注")
    @Update
    private String remark;

    public EditDepartmentReq() {
    }

    public EditDepartmentReq(Long id) {
        this.id = id;
    }
}
