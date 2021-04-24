package com.oak.cms.services.channelimage.req;

import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;



/**
 *  创建ChannelImage
 *  2020-5-28 15:33:23
 */
@Schema(description = "创建CreateChannelImageReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateChannelImageReq extends ApiBaseReq {

    @Schema(description = "标题")
    @NotNull
    private String title;

    @Schema(description = "栏目ID")
    @NotNull
    private Long channelId;

    @Schema(description = "开始时间")
    @NotNull
    private Date beginTime;

    @Schema(description = "结束时间")
    @NotNull
    private Date endTime;

    @Schema(description = "图片")
    @NotNull
    private String image;

    @Schema(description = "跳转url")
    private String url;

    @Schema(description = "备注")
    @Size(max = 500)
    private String remark;

}
