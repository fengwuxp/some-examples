package com.oak.rbac.services.menu.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.oak.rbac.enums.MenuIAction;
import com.oak.rbac.enums.MenuShowType;
import com.oak.rbac.enums.MenuType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * 编辑菜单
 * 2020-1-14 16:32:16
 *
 * @author chenPC
 */
@Schema(description = "编辑菜单")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditMenuReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "图标")
    @Update
    private String icon;

    @Schema(description = "动作")
    @Update
    private MenuIAction action;

    @Schema(description = "菜单类型")
    @Update
    private MenuType type;

    @Schema(description = "菜单的显示类型")
    @Update
    private MenuShowType showType;

    @Size(max = 256)
    @Schema(description = "动作值")
    @Update
    private String value;

    @Size(max = 512)
    @Schema(description = "动作参数")
    @Update
    private String param;

    @Schema(description = "名称")
    @Update
    private String name;

    @Schema(description = "父ID")
    private Long parentId;

    @Schema(description = "排序代码")
    @Update
    private Integer orderCode;

    @Schema(description = "是否允许")
    @Update
    private Boolean enabled;

    @Schema(description = "菜单对应组件路径")
    @Update
    private String component;

    @Size(max = 255)
    @Schema(description = "说明")
    @Update
    private String description;

    public EditMenuReq() {
    }

    public EditMenuReq(Long id) {
        this.id = id;
    }
}
