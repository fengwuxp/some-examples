package com.oak.cms.services.channelimage;

import com.oak.cms.services.channelimage.info.ChannelImageInfo;
import com.oak.cms.services.channelimage.req.CreateChannelImageReq;
import com.oak.cms.services.channelimage.req.DeleteChannelImageReq;
import com.oak.cms.services.channelimage.req.EditChannelImageReq;
import com.oak.cms.services.channelimage.req.QueryChannelImageReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 *  栏目轮播图服务
 *  2020-5-28 15:33:23
 */
public interface ChannelImageService {


    ApiResp<Long> create(CreateChannelImageReq req);


    ApiResp<Void> edit(EditChannelImageReq req);


    ApiResp<Void> delete(DeleteChannelImageReq req);


    ChannelImageInfo findById(Long id);


    Pagination<ChannelImageInfo> query(QueryChannelImageReq req);

}
