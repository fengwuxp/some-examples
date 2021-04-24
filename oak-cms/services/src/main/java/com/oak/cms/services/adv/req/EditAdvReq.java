package com.oak.cms.services.adv.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 *  编辑广告信息
 *  2020-5-28 15:02:29
 */
@Schema(description = "编辑广告信息")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditAdvReq extends ApiBaseReq {

    @Schema(description = "id")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "广告标题")
    @Update
    private String title;

    @Schema(description = "广告内容")
    @Update(condition = "true")
    private String content;

    @Schema(description = "广告展示缩略图")
    @Update
    private String thumbnailImg;

    @Schema(description = "广告跳转url")
    @Update(condition = "true")
    private String url;

    @Schema(description = "有效时间-开始")
    @Update
    private Date startDate;

    @Schema(description = "有效时间-结束")
    @Update
    private Date endDate;

    @Schema(description = "广告排序")
    @Update
    private Integer orderIndex;

    @Schema(description = "广告点击数")
    @Update
    private Integer clickNum;

    @Schema(description = "广告曝光数")
    @Update
    private Integer exposureNum;

    @Schema(description = "是否启用")
    @Update
    private Boolean enabled;

    public EditAdvReq() {
    }

    public EditAdvReq(Long id) {
        this.id = id;
    }
}
