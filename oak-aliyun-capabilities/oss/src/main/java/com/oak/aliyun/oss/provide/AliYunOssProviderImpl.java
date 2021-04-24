package com.oak.aliyun.oss.provide;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.oak.aliyun.oss.configuration.OssProperties;
import com.oak.aliyun.oss.provide.info.AliYunStsTokenInfo;
import com.oak.aliyun.oss.provide.req.GetAliYunOssStsTokenReq;
import com.oak.api.helper.SettingValueHelper;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Objects;


/**
 * 阿里云 oss 文档：https://help.aliyun.com/document_detail/31883.html
 *
 * @author wxup
 */
@Slf4j
@Component
public class AliYunOssProviderImpl implements AliYunOssProvider, InitializingBean, BeanFactoryAware {

    /**
     * 匿名用户模式
     */
    private static final String ANONYMOUS_OPERATOR = "operator-anonymous";

    /**
     * 默认 sts token 的有效秒数
     */
    private static final long DEFAULT_EXPIRATION_TIMES = 1800L;

    /**
     * 产品名称
     */
    private static final String PRODUCT_NAME = "Sts";

    /**
     * 阿里云sts token 有效时长 单位：秒数
     */
    private static final String ALI_YUN_OSS_STS_TOKEN_EXPIRATION_TIMES_KEY = "aliyun.oss.sts.expiration_times";


    /**
     * 缓存 key
     */
    private static final String STS_TOKEN_CACHE_NAME = "ALI_YUN_STS_TOKEN_CACHE";

    /**
     * 提前 刷新时间
     */
    private static final long REFRESH_ADVANCE_TIMES = 180 * 1000;

    @Autowired
    private SettingValueHelper settingValueHelper;

    @Autowired
    private OssProperties ossProperties;

    private CacheManager cacheManager;

    private BeanFactory beanFactory;


    @Override
    public ApiResp<AliYunStsTokenInfo> getAliYunStsToken(GetAliYunOssStsTokenReq req) {
        Long expirationTimes = settingValueHelper.getSettingValue(ALI_YUN_OSS_STS_TOKEN_EXPIRATION_TIMES_KEY, DEFAULT_EXPIRATION_TIMES);
        String roleSessionName = req.getOperatorId() != null ? MessageFormat.format("operator-{0}", req.getOperatorId()) : ANONYMOUS_OPERATOR;
        String key = MessageFormat.format("{0}{1}{2}{3}", req.getAccessKeyId(), req.getRoleArn(), req.getPolicy() != null ? req.getPolicy() : "", roleSessionName);
        key = MessageFormat.format("{0}_{1}", req.getAppId(), DigestUtils.md5DigestAsHex(key.getBytes(StandardCharsets.UTF_8)));
        if (this.cacheManager == null) {
            return this.getStsToken(req, roleSessionName, expirationTimes);
        }

        // 支持缓存
        Cache cache = Objects.requireNonNull(cacheManager.getCache(STS_TOKEN_CACHE_NAME));
        AliYunStsTokenInfo info = cache
                .get(key, AliYunStsTokenInfo.class);
        boolean needToAliYun = info == null || System.currentTimeMillis() > info.getExpirationTime() - REFRESH_ADVANCE_TIMES;
        if (needToAliYun) {
            // 如果过期了
            ApiResp<AliYunStsTokenInfo> stsToken = this.getStsToken(req, roleSessionName, expirationTimes);
            if (!stsToken.isSuccess()) {
                return stsToken;
            }
            info = stsToken.getData();
            cache.put(key, info);
            return stsToken;
        }
        return RestfulApiRespFactory.ok(info);

    }


    private ApiResp<AliYunStsTokenInfo> getStsToken(GetAliYunOssStsTokenReq req, String roleSessionName, Long expirationTimes) {
        try {
            DefaultProfile.addEndpoint(req.getRegionId() == null ? "" : req.getRegionId(), PRODUCT_NAME, req.getEndpoint());
            IClientProfile profile = DefaultProfile.getProfile("", req.getAccessKeyId(), req.getAccessKeySecret());
            DefaultAcsClient client = new DefaultAcsClient(profile);
            AssumeRoleRequest request = new AssumeRoleRequest();
            request.setMethod(MethodType.POST);
            request.setRoleArn(req.getRoleArn());
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(req.getPolicy());
            request.setDurationSeconds(req.getDurationSeconds() == null ? expirationTimes : req.getDurationSeconds());
            AssumeRoleResponse response = client.getAcsResponse(request);
            AliYunStsTokenInfo info = new AliYunStsTokenInfo();
            info.setAccessKeyId(response.getCredentials().getAccessKeyId());
            info.setAccessKeySecret(response.getCredentials().getAccessKeySecret());
            info.setSecurityToken(response.getCredentials().getSecurityToken());
            info.setExpirationTime(System.currentTimeMillis() + expirationTimes * 1000);
            info.setExpirationSeconds(expirationTimes);
            info.setBucketName(ossProperties.getBucketName());
            info.setBasePath(ossProperties.getBasePath());
            info.setEndpoint(ossProperties.getEndpoint());
            return RestfulApiRespFactory.ok(info);
        } catch (ClientException clientException) {
            log.warn(MessageFormat.format("获取阿里云STS访问凭证发生异常：{0}->{1}", clientException.getErrCode(), clientException.getErrMsg()));
            return RestfulApiRespFactory.error(clientException.getMessage());
        }


    }

    @Override
    public void afterPropertiesSet() throws Exception {

        try {
            this.cacheManager = this.beanFactory.getBean(CacheManager.class);
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}
