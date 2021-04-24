package com.oak.cms.business.channel;

import com.oak.cms.business.channel.req.CreateChannelExtendReq;
import com.oak.cms.business.channel.req.QueryChannelExtendReq;
import com.oak.cms.services.channel.info.ChannelInfo;
import com.oak.cms.services.channel.req.DeleteChannelReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;

/**
 * @Classname ChannelBusinessService
 * @Description 专栏 业务方法
 * @Date 2020/5/29 14:35
 * @Created by 44487
 */
public interface ChannelBusinessService {

    /**
     * 查询
     */
    ApiResp<Pagination<ChannelInfo>> queryChannel(QueryChannelExtendReq queryChannelExtendReq);

    /**
     * 添加
     */
    ApiResp<Long> createChannel(CreateChannelExtendReq createChannelExtendReq);

    /**
     * 删除
     */
    ApiResp<Void> deleteChannel(DeleteChannelReq deleteChannelReq);



}
