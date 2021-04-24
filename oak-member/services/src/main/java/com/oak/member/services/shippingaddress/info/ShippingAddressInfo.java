package com.oak.member.services.shippingaddress.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * 收获地址
 * 2020-6-11 14:27:51
 */
@Schema(description = "收获地址")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class ShippingAddressInfo implements Serializable {

    @Schema(description = "会员ID")
    private Long id;

    @Schema(description = "收获地址ID")
    private String areaId;

    @Schema(description = "用户ID")
    private Long memberId;

    @Schema(description = "收件人")
    private String recipient;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "详细地址")
    private String address;

    @Schema(description = "默认选中标记 (初始化为false)")
    private Boolean theDefault;

    @Schema(description = "收货地址区县")
    private String areaName;

    @Schema(description = "地址分组")
    private String groupName;

    @Schema(description = "位置-经度")
    private Double longitude;

    @Schema(description = "位置-纬度")
    private Double latitude;

    @Schema(description = "关联编码")
    private String sourceCode;

    @Schema(description = "邮编")
    private String zipCode;

    @Schema(description = "是否删除")
    private Boolean isDelete;


}
