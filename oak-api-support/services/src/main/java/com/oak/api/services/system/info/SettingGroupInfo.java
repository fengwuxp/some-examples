package com.oak.api.services.system.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 设置分组
 * 2018-3-30 20:39:48
 */
@Schema(description = "设置分组")
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"name"})
@ToString(exclude = {})
public class SettingGroupInfo implements Serializable {

    @Schema(description = "分组名称")
    private String name;

    @Schema(description = "是否显示")
    private Boolean show;

    @Schema(description = "排序")
    private Integer orderIndex;


}
