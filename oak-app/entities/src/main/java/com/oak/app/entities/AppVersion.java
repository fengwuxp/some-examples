package com.oak.app.entities;


import com.oak.api.enums.ClientType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.GenerationType.AUTO;

@Schema(description = "app版本")
@Entity
@Table(name = "t_app_version")
@Data
@EqualsAndHashCode(of = {"id"})
@Accessors(chain = true)
public class AppVersion implements Serializable {

    private static final long serialVersionUID = 7031092397605543389L;

    @Schema(description = "ID")
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;

    @Schema(description = "应用名称")
    @Column(name = "name", length = 32, nullable = false)
    private String name;

    @Schema(description = "显示版本")
    @Column(name = "version", length = 32, nullable = false)
    private String version;

    @Schema(description = "版本号")
    @Column(name = "app_code_num", nullable = false)
    private Integer appCodeNum;

    @Schema(description = "应用编码")
    @Column(name = "app_coding", length = 16)
    private String appCoding;

    @Schema(description = "客户端平台类型")
    @Column(name = "platform_type", length = 16)
    @Enumerated(EnumType.STRING)
    private ClientType platformType;

    @Schema(description = "是否上架")
    @Column(name = "is_putaway", nullable = false)
    private Boolean putAway;

    @Schema(description = "是否强制升级")
    @Column(name = "forcibly", nullable = false)
    private Boolean forcibly;

    @Schema(description = "是否apk下载")
    @Column(name = "is_apk_download")
    private Boolean apkDownload;

    @Schema(description = "下载URL")
    @Column(name = "url", nullable = false)
    private String url;

    @Schema(description = "更新页面url")
    @Column(name = "update_page_url")
    private String updatePageUrl;

    @Schema(description = "最低支持从哪个版本进行更新")
    @Column(name = "minimum_supported_version")
    private Integer minimumSupportedVersion;

    @Schema(description = "创建人")
    @Column(name = "creator", length = 16)
    private String creator;

    @Schema(description = "版本说明")
    @Column(name = "note", length = 200, nullable = false)
    private String note;

    @Schema(description = "发布时间")
    @Column(name = "public_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date publicTime;

    @Schema(description = "创建时间")
    @Column(name = "create_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Schema(description = "更新时间")
    @Column(name = "last_update_time", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdateTime;

    @Schema(description = "是否删除")
    @Column(name = "is_deleted", nullable = false)
    private Boolean deleted;

}
