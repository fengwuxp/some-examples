package com.oak.cms.services.channelimage.info;

import com.oak.cms.services.channel.info.ChannelInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 栏目轮播图
 * 2020-5-28 15:33:23
 */
@Schema(description = "栏目轮播图")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString()
public class ChannelImageInfo implements Serializable {

    @Schema(description = "轮播图ID")
    private Long id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "栏目ID")
    private Long channelId;

    @Schema(description = "栏目信息")
    private ChannelInfo channelInfo;

    @Schema(description = "开始时间")
    private Date beginTime;

    @Schema(description = "结束时间")
    private Date endTime;

    @Schema(description = "是否上线")
    private Boolean enabled;

    @Schema(description = "图片")
    private String image;

    @Schema(description = "跳转url")
    private String url;

    @Schema(description = "排序")
    private Integer orderIndex;

    @Schema(description = "创建日期")
    private Date crateTime;

    @Schema(description = "更新日期")
    private Date updateTime;

    @Schema(description = "备注")
    private String remark;


}
