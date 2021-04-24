package com.oak.member.entities;

import com.levin.commons.service.domain.Desc;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

/**
 * @author ChenXiaoBin
 * on 2020-04-23
 */
@Schema(description = "用户收获地址")
@Entity
@Data
@EqualsAndHashCode(of = "id")
@Table(name = "t_shipping_address", indexes = {
        @Index(name = "index_shipping_address_member_id", columnList = "member_id")
})
@Accessors(chain = true)
public class ShippingAddress implements Serializable {

    private static final long serialVersionUID = -3150202864641130550L;

    @Schema(description = "ID")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "用户ID")
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Schema(description = "收件人名称")
    @Column(name = "recipient", nullable = false, length = 32)
    private String recipient;

    @Schema(description = "收件人电话")
    @Column(name = "phone", nullable = false, length = 12)
    private String phone;

    @Schema(description = "收获地址ID")
    @Column(name = "area_id", nullable = false, length = 32)
    private String areaId;

    @Schema(description = "详细地址")
    @Column(name = "address", nullable = false)
    private String address;

    @Schema(description = "收货地址区县")
    @Column(name = "area_name", nullable = false)
    private String areaName;

    @Schema(description = "默认选中标记 (初始化为false)")
    @Column(name = "is_default", nullable = false)
    private Boolean theDefault = false;

    @Schema(description = "位置-经度")
    @Column(name = "longitude")
    private Double longitude;

    @Schema(description = "位置-纬度")
    @Column(name = "latitude")
    private Double latitude;

    @Schema(description = "地址分组")
    @Column(name = "group_name", length = 32)
    private String groupName;

    @Schema(description = "关联编码")
    @Column(name = "source_code", length = 32)
    private String sourceCode;

    @Desc(value = "邮编")
    @Column(name = "zip_code", length = 10)
    private String zipCode;

    @Schema(description = "是否删除")
    @Column(name = "is_deleted", nullable = false)
    private Boolean deleted;

    @Schema(description = "创建时间")
    @Column(name = "create_time", nullable = false, length = 19)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false, length = 19)
    @Temporal(value = TemporalType.TIMESTAMP)
    protected Date lastUpdateTime;
}
