package com.oak.cms.business.article;

import com.oak.cms.business.article.req.ArticleExtendReq;
import com.oak.cms.business.article.req.InteractionReq;
import com.oak.cms.services.article.info.ArticleInfo;
import com.oak.cms.services.article.req.CreateArticleReq;
import com.oak.cms.services.article.req.DeleteArticleReq;
import com.oak.cms.services.articleaction.req.CreateArticleActionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;

/**
 * @Classname ArticleBusinessService
 * @Description 文章管理业务接口
 * @Date 2020/5/29 14:28
 * @Created by 44487
 */
public interface ArticleBusinessService {

     /**
      * 文章查询接口
      * @param articleExtendReq
      * @return
      */
     ApiResp<Pagination<ArticleInfo>> queryArticle(ArticleExtendReq articleExtendReq);

     /**
      * 创建文章
      */
     ApiResp<Long> createArticle(CreateArticleReq createArticleReq);

     /**
      * 删除文章
      */
     ApiResp<Void> deleteArticle(DeleteArticleReq deleteArticleReq);

     /**
      * 文章交互操作
      */
     ApiResp<Void > articleInteractive(CreateArticleActionReq createArticleActionReq);

     /**
      * 确定文章的交互类型
      * @return
      */
     ApiResp<Boolean> determineArticleInteractive( InteractionReq req );

}
