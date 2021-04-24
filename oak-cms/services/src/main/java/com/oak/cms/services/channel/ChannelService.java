package com.oak.cms.services.channel;

import com.oak.cms.services.channel.info.ChannelInfo;
import com.oak.cms.services.channel.req.CreateChannelReq;
import com.oak.cms.services.channel.req.DeleteChannelReq;
import com.oak.cms.services.channel.req.EditChannelReq;
import com.oak.cms.services.channel.req.QueryChannelReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 *  栏目服务
 *  2020-5-28 15:25:31
 */
public interface ChannelService {


    ApiResp<Long> create(CreateChannelReq req);


    ApiResp<Void> edit(EditChannelReq req);


    ApiResp<Void> delete(DeleteChannelReq req);


    ChannelInfo findById(Long id);


    Pagination<ChannelInfo> query(QueryChannelReq req);

    /**
     * 通过栏目编号（code）找到对应的栏目信息
     * @param code
     * @return
     */
    ChannelInfo queryByCode(String code);

    /**
     * 栏目文章数量编辑操作
     * @param channelId
     * @param isIncrease
     * @return
     */
    ApiResp<Void> articleNumberChange( Long channelId,boolean isIncrease );

}
