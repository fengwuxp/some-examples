package com.oak.cms.services.channel.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import com.oak.cms.enums.ChannelNextMode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  编辑栏目
 *  2020-5-28 15:25:31
 */
@Schema(description = "编辑栏目")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditChannelReq extends ApiBaseReq {

    @Schema(description = "栏目ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Size(max = 32)
    @Schema(description = "栏目编号")
    @Update
    private String code;

    @Schema(description = "名称")
    @Update
    private String name;

    @Schema(description = "图标")
    @Update
    private String icon;

    @Schema(description = "排序")
    @Update
    private Integer orderIndex;

    @Schema(description = "上级栏目ID")
    @Update
    private Long parentId;

    @Schema(description = "层级")
    @Update
    private Integer level;

    @Schema(description = "层级排序")
    @Update
    private String levelPath;

    @Schema(description = "层级关系")
    @Update
    private String path;

    @Schema(description = "下级栏目模式")
    @Update
    private ChannelNextMode nextMode;

    @Schema(description = "是否可用")
    @Update
    private Boolean enabled;

    @Schema(description = "是否删除")
    @Update
    private Boolean deleted;

    @Schema(description = "创建日期")
    @Update
    private Date crateTime;

    @Schema(description = "栏目下属文章数量（如果是父类，下面归属多个子类的话，则会把所有的子类的文章数量都计算在内）")
    @Update
    private Integer numberOfArticles ;

    public EditChannelReq() {
    }

    public EditChannelReq(Long id) {
        this.id = id;
    }
}
