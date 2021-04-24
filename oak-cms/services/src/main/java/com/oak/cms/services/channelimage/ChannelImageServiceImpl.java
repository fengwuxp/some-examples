package com.oak.cms.services.channelimage;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.cms.entities.ChannelImage;
import com.oak.cms.services.channelimage.info.ChannelImageInfo;
import com.oak.cms.services.channelimage.req.CreateChannelImageReq;
import com.oak.cms.services.channelimage.req.DeleteChannelImageReq;
import com.oak.cms.services.channelimage.req.EditChannelImageReq;
import com.oak.cms.services.channelimage.req.QueryChannelImageReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 栏目轮播图服务
 * 2020-5-28 15:33:23
 */
@Service
@Slf4j
public class ChannelImageServiceImpl implements ChannelImageService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateChannelImageReq req) {


        ChannelImage entity = new ChannelImage();
        BeanUtils.copyProperties(req, entity);

        Date date = new Date();
        entity.setUpdateTime(date);
        entity.setCrateTime(date);

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditChannelImageReq req) {


        ChannelImage entity = jpaDao.find(ChannelImage.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("栏目轮播图数据不存在");
        }

        UpdateDao<ChannelImage> updateDao = jpaDao.updateTo(ChannelImage.class).appendByQueryObj(req);

        updateDao.appendColumn("updateTime", new Date());
        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新栏目轮播图失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteChannelImageReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(ChannelImage.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(ChannelImage.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除栏目轮播图");
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ChannelImageInfo findById(Long id) {

        QueryChannelImageReq queryReq = new QueryChannelImageReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<ChannelImageInfo> query(QueryChannelImageReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, ChannelImage.class, ChannelImageInfo.class, req);

    }
}
