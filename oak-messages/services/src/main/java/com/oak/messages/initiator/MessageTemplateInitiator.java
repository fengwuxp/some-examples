package com.oak.messages.initiator;

import com.oak.messages.services.template.MessageTemplateService;
import com.oak.messages.services.template.info.MessageTemplateInfo;
import com.oak.messages.services.template.req.CreateMessageTemplateReq;
import com.oak.messages.services.template.req.QueryMessageTemplateReq;
import com.wuxp.api.initiator.AbstractBaseInitiator;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QueryType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 消息模板初始化器
 *
 * @author wuxp
 */
public class MessageTemplateInitiator extends AbstractBaseInitiator<CreateMessageTemplateReq> {


    @Autowired
    private MessageTemplateService messageTemplateService;

    @Override
    protected boolean initSingleItem(CreateMessageTemplateReq data) {

        QueryMessageTemplateReq req = new QueryMessageTemplateReq();
        req.setCode(data.getCode())
                .setQueryType(QueryType.QUERY_NUM);
        Pagination<MessageTemplateInfo> pagination = messageTemplateService.query(req);

        if (pagination.getTotal() > 0) {
            return true;
        }
        return messageTemplateService.create(data).isSuccess();
    }
}
