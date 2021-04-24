package com.oak.cms.entities;

import com.oak.api.entities.system.Area;
import com.oak.cms.enums.AdvertisingTargetType;
import com.oak.cms.enums.DeliveryScopeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;


@Entity
@Schema(description = "广告信息")
@Table(name = "t_adv", indexes = {
        @Index(name = "index_adv_ap_id", columnList = "ap_id")
})
@Data
@ToString(exclude = {"area"})
@EqualsAndHashCode(of = {"id"})
public class Adv implements Serializable {

    private static final long serialVersionUID = -7444767900426714890L;

    @Schema(description = "id")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "广告位id")
    @Column(name = "ap_id", nullable = false)
    private Long apId;

    @Schema(description = "广告位")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ap_id", insertable = false, updatable = false)
    private AdvPosition advPosition;

    @Schema(description = "广告标题")
    @Column(name = "title", nullable = false)
    private String title;

    @Schema(description = "广告内容")
    @Column(name = "content")
    private String content;

    @Schema(description = "广告展示缩略图")
    @Column(name = "thumbnail_img")
    private String thumbnailImg;

    @Schema(description = "广告跳转url")
    @Column(name = "`url`")
    private String url;

    @Schema(description = "有效时间-开始")
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", length = 10)
    private Date startDate;

    @Schema(description = "有效时间-结束")
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", length = 10)
    private Date endDate;

    @Schema(description = "广告排序")
    @Column(name = "order_index", nullable = false)
    private Integer orderIndex;

    @Schema(description = "广告拥有者")
    @Column(name = "member_id")
    private Long memberId;

    @Schema(description = "会员用户名")
    @Column(name = "member_name", length = 32)
    private String memberName;

    @Schema(description = "广告点击数")
    @Column(name = "click_num", nullable = false)
    private Integer clickNum;

    @Schema(description = "广告曝光数")
    @Column(name = "exposure_num", nullable = false)
    private Integer exposureNum;

    @Schema(description = "归属地区id")
    @Column(name = "area_id", length = 16)
    private String areaId;

    @Schema(description = "城市归属省份id")
    @Column(name = "province_id", length = 16)
    private String provinceId;

    @Schema(description = "广告投放地区")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id", insertable = false, updatable = false)
    private Area area;

    @Schema(description = "投放对象")
    @Column(name = "advertising_targetType", length = 32)
    @Enumerated(EnumType.STRING)
    private AdvertisingTargetType advertisingTargetType;

    @Schema(description = "投放范围")
    @Column(name = "delivery_scope", length = 32)
    @Enumerated(EnumType.STRING)
    private DeliveryScopeType deliveryScopeType;

    @Schema(description = "投放范围（多个投放范围）")
    @Column(name = "delivery_scope_list", length = 300)
    private String deliveryScopeTypeList;

    @Schema(description = "归属城市id列表（多选,元素之间用‘,’分割）")
    @Column(name = "city_id_list", length = 300)
    private String cityIdList;

    @Schema(description = "是否启用")
    @Column(name = "enabled", nullable = false)
    private Boolean enabled;

    @Schema(description = "创建日期")
    @Column(name = "crate_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date crateTime;

    @Schema(description = "更新日期")
    @Column(name = "update_time", length = 19, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

}
