package com.oak.aliyun.oss.configuration;

import com.alibaba.cloud.context.AliCloudAuthorizationMode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author wxup
 */
@Schema(description = "阿里云oss 配置")
@Data
public class OssProperties {


    /**
     * Prefix of OSSConfigurationProperties.
     */
     static final String PREFIX = "alibaba.cloud.oss";

    /**
     * Authorization Mode, please see <a href=
     * "https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.6.659.29f145dc3KOwTh">oss
     * docs</a>.
     */
    @Value("${" + PREFIX + ".authorization-mode:AK_SK}")
    @Schema(description = "授权模式")
    private AliCloudAuthorizationMode authorizationMode = AliCloudAuthorizationMode.STS;

    /**
     * Endpoint, please see <a href=
     * "https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.6.659.29f145dc3KOwTh">oss
     * docs</a>.
     */
    @Schema(description = "阿里云OSS Endpoint")
    private String endpoint;

    /**
     * 阿里云OSS BucketName
     */
    @Schema(description = "阿里云OSS bucketName")
    private String bucketName;

    /**
     * 阿里云OSS上传根路径
     */
    @Schema(description = "阿里云OSS上传根路径，默认:'/'")
    private String basePath = "/";

    /**
     * Sts token, please see <a href=
     * "https://help.aliyun.com/document_detail/32010.html?spm=a2c4g.11186623.6.659.29f145dc3KOwTh">oss
     * docs</a>.
     */
    private StsToken sts;

    @Data
    public static class StsToken {

        /**
         * 阿里云oss stsAccessKeyId
         */
        @Schema(description = "阿里云oss STS AccessKeyId")
        private String accessKey;

        /**
         * 阿里云STSAccessSecret
         */
        @Schema(description = "阿里云oss STS AccessSecret")
        private String secretKey;

        /**
         * 阿里云OSS角色串
         */
        @Schema(description = "阿里云OSS角色串")
        private String roleArn;


//        @Schema(description = "阿里云oss STS 安全令牌")
//        private String securityToken;


    }

}
