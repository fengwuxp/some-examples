package com.oak.cms.services.channel.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.cms.enums.ChannelNextMode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  创建Channel
 *  2020-5-28 15:25:31
 */
@Schema(description = "创建CreateChannelReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateChannelReq extends ApiBaseReq {

    @Schema(description = "栏目编号")
    @NotNull
    @Size(max = 32)
    private String code;

    @Schema(description = "名称")
    @NotNull
    private String name;

    @Schema(description = "图标")
    private String icon;


    @Schema(description = "排序")
    private Integer orderIndex = 1;

    @Schema(description = "上级栏目ID")
    private Long parentId;

    @Schema(description = "下级栏目模式")
    @NotNull
    private ChannelNextMode nextMode;

}
