package com.oak.messages.request;


import com.oak.messages.MessagePushType;
import com.oak.messages.enums.MessageShowType;
import com.oak.messages.enums.SendMode;

/**
 * 推送消息的定义
 *
 * @author wuxp
 */
public interface PushMessageRequest {

    /**
     * 模板消息id
     *
     * @return
     */
    String getTemplateId();

    /**
     * 推送消息类型
     *
     * @return
     */
    MessagePushType getPushType();


    /**
     * 消息展示类型
     *
     * @return
     */
    MessageShowType getShowType();


    /**
     * 消息分组（一般按照用户分组）
     *
     * @return
     */
    String getGroup();

    /**
     * 业务类型
     *
     * @return
     */
    String getBusinessType();


    /**
     * 消息推送方式，默认 {@link SendMode#ASYNC}
     *
     * @return
     */
    SendMode getSendMode();

    /**
     * 推送消息标题
     *
     * @return
     */
    String getTitle();

    /**
     * 推送消息内容
     *
     * @return
     */
    String getContent();

    /**
     * 推送的用户
     *
     * @return
     */
    Long getMemberId();

    /**
     * 推送的参数
     *
     * @return
     */
    String getParams();

    /**
     * 视图参数
     *
     * @return
     */
    String[] getViewParams();

    /**
     * 视图编码
     *
     * @return
     */
    String getViewCode();

    /**
     * 是否需要持久化消息
     *
     * @return <code>false</code> 表示不需要持久化消息
     */
    Boolean getPersistence();

    /**
     * 客户端类型，如果有指明客户端类型，则推送到具体的客户端
     * 否则尝试推送所有的类型的客户端
     *
     * @return
     */
    String getClientType();
}
