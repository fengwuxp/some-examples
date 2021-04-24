package com.oak.messages.services.template;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.messages.entities.MessageTemplate;
import com.oak.messages.services.template.info.MessageTemplateInfo;
import com.oak.messages.services.template.req.CreateMessageTemplateReq;
import com.oak.messages.services.template.req.DeleteMessageTemplateReq;
import com.oak.messages.services.template.req.EditMessageTemplateReq;
import com.oak.messages.services.template.req.QueryMessageTemplateReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 消息模板服务
 * 2020-7-7 13:58:37
 */
@Service
@Slf4j
public class MessageTemplateServiceImpl implements MessageTemplateService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateMessageTemplateReq req) {

        MessageTemplate entity = new MessageTemplate();
        BeanUtils.copyProperties(req, entity);
        Date date = new Date();
        entity.setCreateTime(date);
        entity.setLastUpdateTime(date);
        entity.setEnabled(true);
        entity.setModifiable(true);
        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditMessageTemplateReq req) {


        MessageTemplate entity = jpaDao.find(MessageTemplate.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("消息模板数据不存在");
        }

        UpdateDao<MessageTemplate> updateDao = jpaDao.updateTo(MessageTemplate.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新消息模板失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteMessageTemplateReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r = jpaDao.deleteFrom(MessageTemplate.class).appendByQueryObj(req).delete() > 0;

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public MessageTemplateInfo findById(Long id) {

        QueryMessageTemplateReq queryReq = new QueryMessageTemplateReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MessageTemplateInfo> query(QueryMessageTemplateReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, MessageTemplate.class, MessageTemplateInfo.class, req);

    }
}
