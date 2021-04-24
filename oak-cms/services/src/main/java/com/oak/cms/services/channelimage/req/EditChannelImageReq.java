package com.oak.cms.services.channelimage.req;

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
 *  编辑栏目轮播图
 *  2020-5-28 15:33:23
 */
@Schema(description = "编辑栏目轮播图")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditChannelImageReq extends ApiBaseReq {

    @Schema(description = "轮播图ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "标题")
    @Update
    private String title;

    @Schema(description = "栏目ID")
    @Update
    private Long channelId;

    @Schema(description = "开始时间")
    @Update
    private Date beginTime;

    @Schema(description = "结束时间")
    @Update
    private Date endTime;

    @Schema(description = "是否上线")
    @Update
    private Boolean enabled;

    @Schema(description = "图片")
    @Update
    private String image;

    @Schema(description = "跳转url")
    @Update
    private String url;

    @Schema(description = "排序")
    @Update
    private Integer orderIndex;

    @Schema(description = "创建日期")
    @Update
    private Date crateTime;

    @Size(max = 500)
    @Schema(description = "备注")
    @Update
    private String remark;

    public EditChannelImageReq() {
    }

    public EditChannelImageReq(Long id) {
        this.id = id;
    }
}
