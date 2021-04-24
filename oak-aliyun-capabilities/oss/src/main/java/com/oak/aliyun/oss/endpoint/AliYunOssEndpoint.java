package com.oak.aliyun.oss.endpoint;

import com.oak.aliyun.oss.configuration.OssProperties;
import com.oak.aliyun.oss.provide.AliYunOssProvider;
import com.oak.aliyun.oss.provide.info.AliYunStsTokenInfo;
import com.oak.aliyun.oss.provide.req.GetAliYunOssStsTokenReq;
import com.wuxp.api.ApiRequest;
import com.wuxp.api.ApiResp;
import com.wuxp.api.context.InjectField;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * oss 端点
 *
 * @author wxup
 */
@RestController
@RequestMapping("/alibaba_cloud_oss")
@Slf4j
public class AliYunOssEndpoint {

    @Autowired
    private AliYunOssProvider aliYunOssProvider;

    @Autowired
    private OssProperties aliYunOssProperties;

    @Operation(summary = "获取阿里云oss sts token", description = "获取阿里云oss sts toke")
    @GetMapping(value = "/get_sts_token")
    public ApiResp<AliYunStsTokenInfo> getStsToken(Long operationId, @InjectField(ApiRequest.INJECT_APP_ID_KEY) String appId) {
        GetAliYunOssStsTokenReq req = new GetAliYunOssStsTokenReq();
        req.setOperatorId(operationId);
        OssProperties.StsToken stsToken = aliYunOssProperties.getSts();
        req.setAccessKeyId(stsToken.getAccessKey());
        req.setAccessKeySecret(stsToken.getSecretKey());
        req.setRoleArn(stsToken.getRoleArn());
        req.setAppId(appId);
        return aliYunOssProvider.getAliYunStsToken(req);
    }


    /**
     * 阿里云oss 文件上传回调
     *
     * @param params 回调参数
     * @return 回调处理结果
     */
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    @Hidden
    public Map<String, String> callback(@RequestBody Map<String, String> params) {

        log.info("oss 上传回调{}", params);

        Map<String, String> result = new HashMap<>();
        result.put("Status", "OK");
        return result;
    }

}
