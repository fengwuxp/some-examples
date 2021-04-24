package com.oak.app.services.appversion.req;

import com.levin.commons.dao.annotation.Eq;
import com.levin.commons.dao.annotation.update.Update;
import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *  编辑app版本
 *  2020-7-4 17:56:49
 */
@Schema(description = "编辑app版本")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Accessors(chain = true)
public class EditAppVersionReq extends ApiBaseReq {

    @Schema(description = "ID")
    @NotNull
    @Eq(require = true)
    private Long id;

    @Schema(description = "是否上架")
    @Update
    private Boolean putAway;

    @Schema(description = "是否强制升级")
    @Update
    private Boolean forcibly;

    @Schema(description = "是否apk下载")
    @Update
    private Boolean apkDownload;

    @Schema(description = "下载URL")
    @Update
    private String url;

    @Schema(description = "更新页面url")
    @Update
    private String updatePageUrl;

    @Schema(description = "最低支持从哪个版本进行更新")
    @Update
    private Integer minimumSupportedVersion;

    @Size(max = 200)
    @Schema(description = "版本说明")
    @Update
    private String note;

    public EditAppVersionReq() {
    }

    public EditAppVersionReq(Long id) {
        this.id = id;
    }
}
