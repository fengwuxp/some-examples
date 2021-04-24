package com.oak.messages.business;

import com.oak.messages.services.notice.req.CreateNoticeMessageReq;
import com.oak.messages.services.template.req.CreateMessageTemplateReq;
import com.wuxp.api.ApiResp;

import java.util.List;

/**
 * Date： 2020/7/13 17:38
 *
 * @author ZHM
 *
 *
 */
public interface NoticeSendBusinessService {

    /**
     * <p>
     *   消息需要创建模板信息，消息模板创建完成之后，需要创建发送的具体的消息内容
     *   一旦通知消息生成创建之后，就直接发送给所有的客户
     *
     *   需要预先初始化消息模板以及指定消息

     * @param templateId 模板ID
     * @param memberIdList 会员ID列表
     * @return
     */
    ApiResp<Void> noticeSend(Long templateId, List<Long> memberIdList);

}
