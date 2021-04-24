package com.oak.messages.services.notice;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.messages.entities.E_NoticeMessage;
import com.oak.messages.entities.NoticeMessage;
import com.oak.messages.services.notice.info.NoticeMessageInfo;
import com.oak.messages.services.notice.req.CreateNoticeMessageReq;
import com.oak.messages.services.notice.req.DeleteNoticeMessageReq;
import com.oak.messages.services.notice.req.EditNoticeMessageReq;
import com.oak.messages.services.notice.req.QueryNoticeMessageReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 通知消息服务
 * 2020-7-7 13:59:04
 */
@Service
@Slf4j
public class NoticeMessageServiceImpl implements NoticeMessageService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateNoticeMessageReq req) {


        NoticeMessage entity = new NoticeMessage();
        BeanUtils.copyProperties(req, entity);

        Date date = new Date();
        entity.setCreateTime(date);
        entity.setLastUpdateTime(date);
        entity.setRead(false);

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditNoticeMessageReq req) {


        NoticeMessage entity = jpaDao.find(NoticeMessage.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("通知消息数据不存在");
        }

        UpdateDao<NoticeMessage> updateDao = jpaDao.updateTo(NoticeMessage.class)
                .set(E_NoticeMessage.lastUpdateTime, new Date())
                .appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新通知消息失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteNoticeMessageReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r = jpaDao.deleteFrom(NoticeMessage.class).appendByQueryObj(req).delete() > 0;
        if (!r) {
            return RestfulApiRespFactory.error("通知消息删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public NoticeMessageInfo findById(Long id) {

        QueryNoticeMessageReq queryReq = new QueryNoticeMessageReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<NoticeMessageInfo> query(QueryNoticeMessageReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, NoticeMessage.class, NoticeMessageInfo.class, req);

    }
}
