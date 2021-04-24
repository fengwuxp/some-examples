package com.oak.api.services.client;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.entities.system.ClientChannel;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.api.services.client.info.ClientChannelInfo;
import com.oak.api.services.client.req.CreateClientChannelReq;
import com.oak.api.services.client.req.EditClientChannelReq;
import com.oak.api.services.client.req.QueryClientChannelReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 *  ClientChannel服务
 *  2020-3-11 11:05:47
 */
@Service
@Slf4j
public class ClientChannelServiceImpl implements ClientChannelService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    @Transactional
    public String create(CreateClientChannelReq req) {


        ClientChannel entity = new ClientChannel();
        BeanUtils.copyProperties(req, entity);

            entity.setCreateTime(new Date());
            entity.setUpdateTime(new Date());

        jpaDao.create(entity);

        return entity.getCode();
    }

    @Caching(
            evict = {
                    @CacheEvict(value = CLIENT_CHANNEL_CACHE_NAME, key = "#req.code", condition = "#req.code!=null")
            }
    )
    @Override
    public ApiResp<Void> edit(EditClientChannelReq req) {


        ClientChannel entity = jpaDao.find(ClientChannel.class, req.getCode());
        if (entity == null) {
            return  RestfulApiRespFactory.error("ClientChannel数据不存在");
        }

        UpdateDao<ClientChannel> updateDao = jpaDao.updateTo(ClientChannel.class).appendByQueryObj(req);

        updateDao.appendColumn("updateTime", new Date());
        int update = updateDao.update();
        if (update < 1) {
            return  RestfulApiRespFactory.error("更新ClientChannel失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Cacheable(value = CLIENT_CHANNEL_CACHE_NAME,
            key = "#code",
            condition = "#code !=null",
            unless = "#result==null")
    @Override
    public ClientChannelInfo findById(String code) {

        QueryClientChannelReq queryReq = new QueryClientChannelReq();
        queryReq.setCode(code);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<ClientChannelInfo> query(QueryClientChannelReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao,ClientChannel.class,ClientChannelInfo.class,req);

    }
}
