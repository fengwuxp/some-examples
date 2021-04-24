package com.oak.aliyun.oss.provide.req;


import com.oak.api.model.ApiBaseReq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author wxup
 */
@Schema(description = "获取阿里云sts token")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GetAliYunOssStsTokenReq extends ApiBaseReq {

    @Schema(description = "endpoint")
    private String endpoint = "sts.aliyuncs.com";

    @Schema(description = "接入区域")
    private String regionId;

    @Schema(description = "用户接入账号")
    @NotNull
    private String accessKeyId;

    @Schema(description = "操作人")
    private Long operatorId;

    @Schema(description = "用户接入密钥")
    @NotNull
    private String accessKeySecret;

    @Schema(description = "角色")
    @NotNull
    private String roleArn;

    @Schema(description = "策略（若为空，则用户将获得该角色下所有权限）")
    private String policy;

    @Schema(description = "凭证有效秒数")
    @Min(30L)
    @Max(2592000000L)
    private Long durationSeconds;

    @Schema(description = "app id")
    @NotNull
    private String appId;


}
