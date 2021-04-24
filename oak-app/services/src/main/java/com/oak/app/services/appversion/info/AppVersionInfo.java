package com.oak.app.services.appversion.info;

import com.oak.api.enums.ClientType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


/**
 * app版本
 * 2020-7-4 17:56:49
 */
@Schema(description = "app版本")
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(exclude = {})
public class AppVersionInfo implements Serializable {

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

    @Schema(description = "是否是最新版本")
    private Boolean latest;

    @Schema(description = "是否强制升级")
    private Boolean forcibly;

    @Schema(description = "是否apk下载")
    private Boolean apkDownload;

    @Schema(description = "下载URL")
    private String url;

    @Schema(description = "更新页面url")
    private String updatePageUrl;

    @Schema(description = "最低支持从哪个版本进行更新")
    private Integer minimumSupportedVersion;

    @Schema(description = "创建人")
    private String creator;

    @Schema(description = "版本说明")
    private String note;

    @Schema(description = "发布时间")
    private Date publicTime;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date lastUpdateTime;


}
