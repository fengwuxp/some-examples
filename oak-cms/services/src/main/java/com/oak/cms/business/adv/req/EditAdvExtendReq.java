package com.oak.cms.business.adv.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.cms.enums.AdvertisingTargetType;
import com.oak.cms.enums.DeliveryScopeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
public class EditAdvExtendReq extends ApiBaseReq {

    @Schema(description = "id")
    @NotNull
    private Long id;

    @Schema(description = "广告标题")
    private String title;

    @Schema(description = "广告内容")
    private String content;

    @Schema(description = "广告跳转url")
    private String url;

    @Schema(description = "广告展示缩略图")
    private String thumbnailImg;

    @Schema(description = "有效时间-开始")
    private Date startDate;

    @Schema(description = "有效时间-结束")
    private Date endDate;

    @Schema(description = "广告排序")
    private Integer orderIndex;
    
    @Size(max = 16)
    @Schema(description = "归属地区id")
    private String areaId;

    @Size(max = 16)
    @Schema(description = "城市归属省份id")
    private String provinceId;

    @Schema(description = "投放对象")
    private AdvertisingTargetType advertisingTargetType;

    @Schema(description = "投放范围")
    private DeliveryScopeType deliveryScopeType;

    @Size(max = 300)
    @Schema(description = "投放范围（多个投放范围）")
    private String deliveryScopeTypeList;

    @Size(max = 300)
    @Schema(description = "归属城市id列表（多选,元素之间用‘,’分割）")
    private String cityIdList;

    @Schema(description = "是否启用")
    private Boolean enabled;

    public EditAdvExtendReq() {
    }

    public EditAdvExtendReq(Long id) {
        this.id = id;
    }
}
