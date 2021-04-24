package com.oak.app.services.appversion.req;

import com.oak.api.enums.ClientType;
import com.oak.api.model.ApiBaseReq;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  创建AppVersion
 *  2020-7-4 17:56:49
 */
@Schema(description = "创建CreateAppVersionReq的请求")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CreateAppVersionReq extends ApiBaseReq {

    @Schema(description = "应用名称")
    @NotNull
    @Size(max = 32)
    private String name;

    @Schema(description = "显示版本")
    @NotNull
    @Size(max = 32)
    private String version;

    @Schema(description = "版本号")
    @NotNull
    private Integer appCodeNum;

    @Schema(description = "应用编码")
    @Size(max = 16)
    private String appCoding;

    @Schema(description = "客户端平台类型")
    @NotNull
    private ClientType platformType;

    @Schema(description = "是否上架")
    @NotNull
    private Boolean putAway;

    @Schema(description = "是否强制升级")
    @NotNull
    private Boolean forcibly;

    @Schema(description = "是否apk下载")
    private Boolean apkDownload;

    @Schema(description = "下载URL")
    @NotNull
    private String url;

    @Schema(description = "更新页面url")
    private String updatePageUrl;

    @Schema(description = "最低支持从哪个版本进行更新")
    private Integer minimumSupportedVersion;

    @Schema(description = "创建人")
    @Size(max = 16)
    @InjectField("#admin.name")
    private String creator;

    @Schema(description = "版本说明")
    @NotNull
    @Size(max = 200)
    private String note;

}
