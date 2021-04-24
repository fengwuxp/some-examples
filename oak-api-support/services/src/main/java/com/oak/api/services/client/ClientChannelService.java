package com.oak.api.services.client;

import com.oak.api.services.client.info.ClientChannelInfo;
import com.oak.api.services.client.req.CreateClientChannelReq;
import com.oak.api.services.client.req.EditClientChannelReq;
import com.oak.api.services.client.req.QueryClientChannelReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;



/**
 *  ClientChannel服务
 *  2020-3-11 11:05:47
 */
public interface ClientChannelService {

    String CLIENT_CHANNEL_CACHE_NAME = "CLIENT_CHANNEL_CODE";

    String create(CreateClientChannelReq req);

    ApiResp<Void> edit(EditClientChannelReq req);

    ClientChannelInfo findById(String code);

    Pagination<ClientChannelInfo> query(QueryClientChannelReq req);

}
