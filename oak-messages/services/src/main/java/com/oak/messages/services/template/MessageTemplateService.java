package com.oak.messages.services.template;

import com.oak.messages.services.template.info.MessageTemplateInfo;
import com.oak.messages.services.template.req.CreateMessageTemplateReq;
import com.oak.messages.services.template.req.DeleteMessageTemplateReq;
import com.oak.messages.services.template.req.EditMessageTemplateReq;
import com.oak.messages.services.template.req.QueryMessageTemplateReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;



/**
 *  消息模板服务
 *  2020-7-7 13:58:37
 */
public interface MessageTemplateService {


    ApiResp<Long> create(CreateMessageTemplateReq req);


    ApiResp<Void> edit(EditMessageTemplateReq req);


    ApiResp<Void> delete(DeleteMessageTemplateReq req);


    MessageTemplateInfo findById(Long id);


    Pagination<MessageTemplateInfo> query(QueryMessageTemplateReq req);

}
