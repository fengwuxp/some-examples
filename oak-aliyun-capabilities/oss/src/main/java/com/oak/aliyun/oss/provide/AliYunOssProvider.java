package com.oak.aliyun.oss.provide;


import com.oak.aliyun.oss.provide.info.AliYunStsTokenInfo;
import com.oak.aliyun.oss.provide.req.GetAliYunOssStsTokenReq;
import com.wuxp.api.ApiResp;


/**
 * 阿里云oss 相关接入
 */
public interface AliYunOssProvider {




    /**
     * 获取阿里云 oss sts token
     *{ @doc https://help.aliyun.com/document_detail/100624.html?spm=a2c4g.11186623.6.669.b48d734axCycIX}
     * @param req
     * @return
     */
    ApiResp<AliYunStsTokenInfo> getAliYunStsToken(GetAliYunOssStsTokenReq req);
}
