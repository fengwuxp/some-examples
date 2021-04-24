package com.oak.cms.services.channelarticle;

import com.oak.cms.services.channelarticle.info.ChannelArticleInfo;
import com.oak.cms.services.channelarticle.req.CreateChannelArticleReq;
import com.oak.cms.services.channelarticle.req.DeleteChannelArticleReq;
import com.oak.cms.services.channelarticle.req.EditChannelArticleReq;
import com.oak.cms.services.channelarticle.req.QueryChannelArticleReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 *  专题文章服务
 *  2020-5-28 15:30:28
 */
public interface ChannelArticleService {


    ApiResp<Long> create(CreateChannelArticleReq req);


    ApiResp<Void> edit(EditChannelArticleReq req);


    ApiResp<Void> delete(DeleteChannelArticleReq req);


    ChannelArticleInfo findById(Long id);


    Pagination<ChannelArticleInfo> query(QueryChannelArticleReq req);


    /**
     * 删除 指定 文章id
     * @param articleId
     * @return
     */
    Integer deleteByArticleId(Long articleId);

}
