package com.oak.cms.initator;

import com.oak.cms.enums.ChannelNextMode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname Channel
 * @Description 栏目初始化对象
 * @Date 2020/5/29 14:45
 * @Created by 44487
 */
@Schema(description = "文章栏目初始化对象")
@Data
public class ChannelInitializeSettings implements Serializable {

    @Schema(description = "栏目编号")
    private String code;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "下级栏目模式")
    private ChannelNextMode nextMode;

    @Schema(description = "下级栏目内容")
    private List<ChannelInitializeSettings> lowerChannel;

}
