package com.oak.cms.services.article;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.cms.entities.Article;
import com.oak.cms.services.article.info.ArticleInfo;
import com.oak.cms.services.article.req.CreateArticleReq;
import com.oak.cms.services.article.req.DeleteArticleReq;
import com.oak.cms.services.article.req.EditArticleReq;
import com.oak.cms.services.article.req.QueryArticleReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 文章服务
 * 2020-5-28 15:20:01
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateArticleReq req) {


        Article entity = new Article();
        BeanUtils.copyProperties(req, entity);

        Date date = new Date();
        entity.setUpdateTime(date);
        entity.setPublishTime(date);
        entity.setCrateTime( date );

        jpaDao.create(entity);


        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditArticleReq req) {


        Article entity = jpaDao.find(Article.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("文章数据不存在");
        }

        UpdateDao<Article> updateDao = jpaDao.updateTo(Article.class).appendByQueryObj(req);

        updateDao.appendColumn("updateTime", new Date());
        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新文章失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteArticleReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(Article.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(Article.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除文章");
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ArticleInfo findById(Long id) {

        QueryArticleReq queryReq = new QueryArticleReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<ArticleInfo> query(QueryArticleReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, Article.class, ArticleInfo.class, req);

    }



}
