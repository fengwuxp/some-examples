package com.oak.cms.services.articleaction;

import com.oak.cms.services.articleaction.info.ArticleActionInfo;
import com.oak.cms.services.articleaction.req.CreateArticleActionReq;
import com.oak.cms.services.articleaction.req.DeleteArticleActionReq;
import com.oak.cms.services.articleaction.req.EditArticleActionReq;
import com.oak.cms.services.articleaction.req.QueryArticleActionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 *  ArticleAction服务
 *  2020-5-28 15:28:48
 */
public interface ArticleActionService {


    ApiResp<Long> create(CreateArticleActionReq req);


    ApiResp<Void> edit(EditArticleActionReq req);


    ApiResp<Void> delete(DeleteArticleActionReq req);


    ArticleActionInfo findById(Long id);


    Pagination<ArticleActionInfo> query(QueryArticleActionReq req);


    /**
     * 删除 指定 文章id
     * @param articleId
     * @return
     */
    Integer deleteByArticleId(Long articleId);

}
