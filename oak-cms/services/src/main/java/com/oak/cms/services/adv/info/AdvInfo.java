package com.oak.cms.services.adv.info;

import com.oak.api.services.area.info.AreaInfo;
import com.oak.cms.enums.AdvertisingTargetType;
import com.oak.cms.enums.DeliveryScopeType;
import com.oak.cms.services.advposition.info.AdvPositionInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * 广告信息
 * 2020-5-28 15:02:29
 */
@Schema(description = "广告信息")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = { "advPositionInfo"})
public class AdvInfo implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "广告位id")
    private Long apId;

    @Schema(description = "广告位")
    private AdvPositionInfo advPositionInfo;

    @Schema(description = "广告标题")
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
    private Integer orderIndex;

    @Schema(description = "广告拥有者")
    private Long memberId;

    @Schema(description = "会员用户名")
    private String memberName;

    @Schema(description = "广告点击数")
    private Integer clickNum;

    @Schema(description = "广告曝光数")
    private Integer exposureNum;

    @Schema(description = "归属地区id")
    private String areaId;

    @Schema(description = "城市归属省份id")
    private String provinceId;

    @Schema(description = "广告投放地区")
    private AreaInfo areaInfo;

    @Schema(description = "投放对象")
    private AdvertisingTargetType advertisingTargetType;

    @Schema(description = "投放范围")
    private DeliveryScopeType deliveryScopeType;

    @Schema(description = "投放范围（多个投放范围）")
    private String deliveryScopeTypeList;

    @Schema(description = "归属城市id列表（多选,元素之间用‘,’分割）")
    private String cityIdList;

    @Schema(description = "是否启用")
    private Boolean enabled;

    @Schema(description = "创建日期")
    private Date crateTime;

    @Schema(description = "更新日期")
    private Date updateTime;


}
