package com.oak.wechat.publish.services.service;

import com.oak.wechat.publish.services.dto.OnekeyUploadCodeDTO;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenMaSubmitAuditResult;

/**
 * @Classname IWechatOpenService
 * @Description TODO
 * @Date 2020/3/17 18:57
 * @Created by 44487
 */
public interface IWechatOpenService {

    String getAccessTokenByAppId(String appId) throws WxErrorException;

    WxOpenMaSubmitAuditResult oneKeyUploadCode(OnekeyUploadCodeDTO onekeyUploadCode) throws WxErrorException;

}
