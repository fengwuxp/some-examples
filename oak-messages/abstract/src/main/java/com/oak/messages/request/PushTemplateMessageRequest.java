package com.oak.messages.request;


import com.oak.messages.MessagePushType;
import com.oak.messages.enums.SendMode;

import java.util.Map;

/**
 * 推送模板消息的请求
 *
 * @author wuxp
 */
public interface PushTemplateMessageRequest {


    /**
     * 模板消息id
     *
     * @return
     */
    String getTemplateId();


    /**
     * 模板命名参数
     *
     * @return
     */
    Map<String, String> getNamedParams();

    /**
     * 模板位置参数
     *
     * @return
     */
    String[] getPositionParams();

    /**
     * 推送的用户
     *
     * @return
     */
    Long getMemberId();



    /**
     * 客户端类型，如果有指明客户端类型，则推送到具体的客户端
     * 否则尝试推送所有的类型的客户端
     *
     * @return
     */
    String getClientType();

    /**
     * 消息推送方式，默认 {@link SendMode#ASYNC}
     *
     * @return
     */
    SendMode getSendMode();
}
