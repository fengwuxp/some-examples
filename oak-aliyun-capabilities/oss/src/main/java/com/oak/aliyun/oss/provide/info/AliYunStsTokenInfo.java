package com.oak.aliyun.oss.provide.info;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author wxup
 */
@Schema(description = "阿里云STS访问凭证")
@Data
@Accessors(chain = true)
public class AliYunStsTokenInfo implements Serializable {

    @Schema(description = "授权接入ID")
    private String accessKeyId;

    @Schema(description = "授权接入密钥")
    private String accessKeySecret;

    @Schema(description = "访问token")
    private String securityToken;

    @Schema(description = "访问token有效日期")
    private Long expirationTime;

    @Schema(description = "访问token有效秒数")
    private Long expirationSeconds;

    @Schema(description = "阿里云OSS Endpoint")
    private String endpoint;

    @Schema(description = "阿里云OSS bucketName")
    private String bucketName;

    @Schema(description = "阿里云OSS上传根路径，默认:'/'")
    private String basePath = "/";

}
