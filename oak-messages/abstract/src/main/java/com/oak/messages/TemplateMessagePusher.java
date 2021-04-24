package com.oak.messages;

import com.oak.messages.request.PushTemplateMessageRequest;

/**
 * 模板消息推送者
 * 用于应用系统给各个客户端发送模板消息
 * 如需解耦可以接入消息队列
 *
 * @author wuxp
 * @see com.oak.messages.ClientMessagePusher
 */
public interface TemplateMessagePusher {


    /**
     * 推送模板消息
     *
     * @param request
     */
    void pushMessage(PushTemplateMessageRequest request);
}
