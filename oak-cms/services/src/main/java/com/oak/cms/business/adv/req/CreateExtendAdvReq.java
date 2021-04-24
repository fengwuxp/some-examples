package com.oak.cms.business.adv.req;

import com.oak.api.model.ApiBaseReq;
import com.oak.cms.enums.AdvertisingTargetType;
import com.oak.cms.enums.DeliveryScopeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 *  创建Adv
 *  2020-5-28 15:02:29
 */
@Schema(description = "创建基础广告信息")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateExtendAdvReq extends ApiBaseReq {

    @Schema(description = "广告位编号")
    @NotNull
    private String apCode;

    @Schema(description = "广告标题")
    @NotNull
    private String title;

    @Schema(description = "广告内容")
    private String content;

    @Schema(description = "广告展示缩略图")
    private String thumbnailImg;

    @Schema(description = "广告跳转url")
    private String url;

    @Schema(description = "有效时间-开始")
    private Date startDate;

    @Schema(description = "有效时间-结束")
    private Date endDate;

    @Schema(description = "广告排序")
    @NotNull
    private Integer orderIndex;

    @Schema(description = "广告拥有者")
    private Long memberId;

    @Schema(description = "会员用户名")
    @Size(max = 32)
    private String memberName;

    @Schema(description = "归属地区id")
    @Size(max = 16)
    private String areaId;

    @Schema(description = "城市归属省份id")
    @Size(max = 16)
    private String provinceId;

    @Schema(description = "投放对象")
    private AdvertisingTargetType advertisingTargetType;

    @Schema(description = "投放范围")
    private DeliveryScopeType deliveryScopeType;

    @Schema(description = "投放范围（多个投放范围）")
    @Size(max = 300)
    private String deliveryScopeTypeList;

    @Schema(description = "归属城市id列表（多选,元素之间用‘,’分割）")
    @Size(max = 300)
    private String cityIdList;

    @Schema(description = "是否启用")
    private Boolean enabled;
}
