package com.oak.cms.services.channelarticle;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.cms.entities.ChannelArticle;
import com.oak.cms.services.channelarticle.info.ChannelArticleInfo;
import com.oak.cms.services.channelarticle.req.CreateChannelArticleReq;
import com.oak.cms.services.channelarticle.req.DeleteChannelArticleReq;
import com.oak.cms.services.channelarticle.req.EditChannelArticleReq;
import com.oak.cms.services.channelarticle.req.QueryChannelArticleReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 *  专题文章服务
 *  2020-5-28 15:30:28
 */
@Service
@Slf4j
public class ChannelArticleServiceImpl implements ChannelArticleService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateChannelArticleReq req) {


        ChannelArticle entity = new ChannelArticle();
        BeanUtils.copyProperties(req, entity);

        Date date = new Date();
        entity.setUpdateTime(date);
        entity.setCrateTime(date);

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditChannelArticleReq req) {


        ChannelArticle entity = jpaDao.find(ChannelArticle.class, req.getId());
        if (entity == null) {
            return  RestfulApiRespFactory.error("专题文章数据不存在");
        }

        UpdateDao<ChannelArticle> updateDao = jpaDao.updateTo(ChannelArticle.class).appendByQueryObj(req);

        updateDao.appendColumn("updateTime", new Date());
        int update = updateDao.update();
        if (update < 1) {
            return  RestfulApiRespFactory.error("更新专题文章失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteChannelArticleReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return  RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(ChannelArticle.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(ChannelArticle.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除专题文章");
        }

        if (!r) {
            return  RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ChannelArticleInfo findById(Long id) {

        QueryChannelArticleReq queryReq = new QueryChannelArticleReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<ChannelArticleInfo> query(QueryChannelArticleReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao,ChannelArticle.class,ChannelArticleInfo.class,req);

    }

    @Override
    public Integer deleteByArticleId(Long articleId) {

        int delete = jpaDao.deleteFrom(ChannelArticle.class, "ca")
                .eq("articleId", articleId)
                .delete();

        return delete;
    }
}
