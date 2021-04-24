package com.oak.messages;

import com.oak.messages.request.PushMessageRequest;

/**
 * 客户消息推送者
 * 用于给客户端推送应用相关消息，可以根据不同想推送平台实现不同的推送者
 *
 * @author wuxp
 */
public interface ClientMessagePusher {

    /**
     * 消息推送
     *
     * @param request
     */
    void push(PushMessageRequest request);


    /**
     * 是否支持该推送者
     * @param request
     * @return
     */
    boolean supports(PushMessageRequest request);
}
