package com.oak.cms.services.articleaction;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.cms.entities.ArticleAction;
import com.oak.cms.services.articleaction.info.ArticleActionInfo;
import com.oak.cms.services.articleaction.req.CreateArticleActionReq;
import com.oak.cms.services.articleaction.req.DeleteArticleActionReq;
import com.oak.cms.services.articleaction.req.EditArticleActionReq;
import com.oak.cms.services.articleaction.req.QueryArticleActionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;


/**
 *  ArticleAction服务
 *  2020-5-28 15:28:48
 */
@Service
@Slf4j
public class ArticleActionServiceImpl implements ArticleActionService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateArticleActionReq req) {

        long count = jpaDao.selectFrom(ArticleAction.class, "ac")
                .eq("articleId", req.getArticleId())
                .eq("actionType", req.getActionType())
                .eq("ip", req.getIp()).count();

        if( count > 0  ){
            return RestfulApiRespFactory.error(MessageFormat.format("该文章已经在该客户端({0})上{1}",req.getIp(),req.getActionType().toString()));
        }

        ArticleAction entity = new ArticleAction();
        BeanUtils.copyProperties(req, entity);

        entity.setCrateTime( new Date() );
        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditArticleActionReq req) {


        ArticleAction entity = jpaDao.find(ArticleAction.class, req.getId());
        if (entity == null) {
            return  RestfulApiRespFactory.error("ArticleAction数据不存在");
        }

        UpdateDao<ArticleAction> updateDao = jpaDao.updateTo(ArticleAction.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return  RestfulApiRespFactory.error("更新ArticleAction失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteArticleActionReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return  RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(ArticleAction.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(ArticleAction.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除ArticleAction");
        }

        if (!r) {
            return  RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ArticleActionInfo findById(Long id) {

        QueryArticleActionReq queryReq = new QueryArticleActionReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<ArticleActionInfo> query(QueryArticleActionReq req) {
        return SimpleCommonDaoHelper.queryObject(jpaDao,ArticleAction.class,ArticleActionInfo.class,req);
    }

    @Override
    public Integer deleteByArticleId(Long articleId) {

        int delete = jpaDao.deleteFrom(ArticleAction.class, "ac")
                .eq("articleId", articleId)
                .delete();
        return delete;
    }
}
