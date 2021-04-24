package com.oak.cms.services.article;

import com.oak.cms.services.article.info.ArticleInfo;
import com.oak.cms.services.article.req.CreateArticleReq;
import com.oak.cms.services.article.req.DeleteArticleReq;
import com.oak.cms.services.article.req.EditArticleReq;
import com.oak.cms.services.article.req.QueryArticleReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 *  文章服务
 *  2020-5-28 15:20:01
 */
public interface ArticleService {


    ApiResp<Long> create(CreateArticleReq req);


    ApiResp<Void> edit(EditArticleReq req);


    ApiResp<Void> delete(DeleteArticleReq req);


    ArticleInfo findById(Long id);


    Pagination<ArticleInfo> query(QueryArticleReq req);


}
