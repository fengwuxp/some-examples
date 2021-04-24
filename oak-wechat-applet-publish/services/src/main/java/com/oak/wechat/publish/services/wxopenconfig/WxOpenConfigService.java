package com.oak.wechat.publish.services.wxopenconfig;

import com.oak.wechat.publish.services.wxopenconfig.info.WxOpenConfigInfo;
import com.oak.wechat.publish.services.wxopenconfig.req.CreateWxOpenConfigReq;
import com.oak.wechat.publish.services.wxopenconfig.req.DeleteWxOpenConfigReq;
import com.oak.wechat.publish.services.wxopenconfig.req.EditWxOpenConfigReq;
import com.oak.wechat.publish.services.wxopenconfig.req.QueryWxOpenConfigReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 *  组织服务
 *  2020-3-2 14:28:21
 */
public interface WxOpenConfigService {


    ApiResp<Long> create(CreateWxOpenConfigReq req);


    ApiResp<Void> edit(EditWxOpenConfigReq req);


    ApiResp<Void> delete(DeleteWxOpenConfigReq req);


    WxOpenConfigInfo findById(Long id);


    Pagination<WxOpenConfigInfo> query(QueryWxOpenConfigReq req);

}
