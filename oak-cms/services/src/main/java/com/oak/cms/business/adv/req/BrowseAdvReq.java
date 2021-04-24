package com.oak.cms.business.adv.req;

import com.oak.api.model.ApiBaseQueryReq;
import com.oak.cms.enums.AdvAccessType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Classname BrowseAdvReq
 * @Description 浏览广告请求对象
 * @Date 2020/5/28 19:48
 * @Created by 44487
 *
 * 浏览广告信息或者是点击广告信息使用该对象请求
 *
 */

@Schema(description = "浏览或者点击广告请求对象")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class BrowseAdvReq extends ApiBaseQueryReq {


    @Schema(description = "广告id")
    @NotNull
    private Long advId;

    @Schema(description = "广告访问类型")
    @NotNull
    private AdvAccessType accessType;

    @Schema(description = "访问客户端的ip（不同的ip才算是有效点击或者是曝光）")
    @NotNull
    @Size(max = 20)
    private String ip;


}
