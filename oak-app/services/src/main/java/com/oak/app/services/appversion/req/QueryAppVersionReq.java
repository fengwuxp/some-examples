package com.oak.app.services.appversion.req;

import com.levin.commons.dao.annotation.Gte;
import com.levin.commons.dao.annotation.Lte;
import com.oak.api.enums.ClientType;
import com.oak.api.model.ApiBaseQueryReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 查询app版本
 * 2020-7-4 17:56:49
 */
@Schema(description = "查询app版本")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)

public class QueryAppVersionReq extends ApiBaseQueryReq {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "应用名称")
    private String name;

    @Schema(description = "显示版本")
    private String version;

    @Schema(description = "版本号")
    private Integer appCodeNum;

    @Schema(description = "应用编码")
    private String appCoding;

    @Schema(description = "客户端平台类型")
    private ClientType platformType;

    @Schema(description = "是否上架")
    private Boolean putAway;

    @Schema(description = "是否强制升级")
    private Boolean forcibly;

    @Schema(description = "是否apk下载")
    private Boolean apkDownload;

    @Schema(description = "最低支持从哪个版本进行更新")
    private Integer minimumSupportedVersion;

    @Schema(description = "最小发布时间")
    @Gte("publicTime")
    private Date minPublicTime;

    @Schema(description = "最大发布时间")
    @Lte("publicTime")
    private Date maxPublicTime;

    @Schema(description = "最小创建时间")
    @Gte("createTime")
    private Date minCreateTime;

    @Schema(description = "最大创建时间")
    @Lte("createTime")
    private Date maxCreateTime;

    public QueryAppVersionReq() {
    }

    public QueryAppVersionReq(Long id) {
        this.id = id;
    }
}
