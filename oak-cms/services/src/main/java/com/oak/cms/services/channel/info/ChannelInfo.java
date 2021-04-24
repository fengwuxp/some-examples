package com.oak.cms.services.channel.info;

import com.oak.cms.enums.ChannelNextMode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 栏目
 * 2020-5-28 15:25:31
 */
@Schema(description = "栏目")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString()
public class ChannelInfo implements Serializable {

    @Schema(description = "栏目ID")
    private Long id;

    @Schema(description = "栏目编号")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "排序")
    private Integer orderIndex;

    @Schema(description = "上级栏目ID")
    private Long parentId;

    @Schema(description = "上级栏目信息")
    private ChannelInfo parentInfo;

    @Schema(description = "层级")
    private Integer level;

    @Schema(description = "层级排序")
    private String levelPath;

    @Schema(description = "层级关系")
    private String path;

    @Schema(description = "下级栏目模式")
    private ChannelNextMode nextMode;

    @Schema(description = "启用分域筛查，栏目下内容将按分域筛查")
    private Boolean enableDomain;

    @Schema(description = "是否需要审批，栏目下内容是否需要审批")
    private Boolean needApprove;

    @Schema(description = "是否可用")
    private Boolean enabled;

    @Schema(description = "是否删除")
    private Boolean deleted;

    @Schema(description = "创建日期")
    private Date crateTime;

    @Schema(description = "更新日期")
    private Date updateTime;

    @Schema(description = "栏目下属文章数量（如果是父类，下面归属多个子类的话，则会把所有的子类的文章数量都计算在内）")
    private Integer numberOfArticles = 0;
}
