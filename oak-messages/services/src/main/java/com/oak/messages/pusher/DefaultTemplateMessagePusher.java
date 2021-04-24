package com.oak.messages.pusher;

import com.oak.messages.ClientMessagePusher;
import com.oak.messages.TemplateMessagePusher;
import com.oak.messages.enums.SendMode;
import com.oak.messages.pusher.request.SimplePushMessageRequest;
import com.oak.messages.request.PushMessageRequest;
import com.oak.messages.request.PushTemplateMessageRequest;
import com.oak.messages.services.template.MessageTemplateService;
import com.oak.messages.services.template.info.MessageTemplateInfo;
import com.oak.messages.services.template.req.QueryMessageTemplateReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 默认的单机模板消息推送处理者
 *
 * @author wuxp
 */
@Component
@Slf4j
public class DefaultTemplateMessagePusher implements TemplateMessagePusher {


    @Autowired
    protected MessageTemplateService messageTemplateService;

    @Autowired
    private List<ClientMessagePusher> clientMessagePushers;

    private final ThreadPoolExecutor ThreadPoolExecutor;

    public DefaultTemplateMessagePusher() {
        this.ThreadPoolExecutor = new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(500), r -> {
            Thread t = new Thread(new ThreadGroup(DefaultTemplateMessagePusher.class.getSimpleName()), r,
                    DefaultTemplateMessagePusher.class.getSimpleName(),
                    0);
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            return t;
        }, new ThreadPoolExecutor.AbortPolicy());
    }

    @Override
    public void pushMessage(PushTemplateMessageRequest request) {
        // 获取消息模板
        QueryMessageTemplateReq req = new QueryMessageTemplateReq();
        req.setCode(request.getTemplateId())
                .setEnabled(true);
        List<MessageTemplateInfo> records = messageTemplateService.query(req).getRecords();

        for (MessageTemplateInfo messageTemplateInfo : records) {

            SendMode sendMode = request.getSendMode();
            String viewCode = messageTemplateInfo.getViewCode();
            String title = replaceTemplateContent(messageTemplateInfo.getTitle(), null, request.getNamedParams());
            String content = replaceTemplateContent(messageTemplateInfo.getContent(), request.getPositionParams(), request.getNamedParams());
            SimplePushMessageRequest messageRequest = new SimplePushMessageRequest();
            messageRequest.setClientType(request.getClientType())
                    .setMemberId(request.getMemberId())
                    .setSendMode(sendMode)
                    .setViewParams(request.getPositionParams())
                    .setShowType(messageTemplateInfo.getShowType())
                    .setPushType(messageTemplateInfo.getPushType())
                    .setBusinessType(messageTemplateInfo.getBusinessType())
                    .setViewCode(viewCode)
                    .setPersistence(messageTemplateInfo.getPersistence())
                    .setTitle(title)
                    .setContent(content);

            if (SendMode.ASYNC.equals(sendMode)) {
                // 异步推送
                ThreadPoolExecutor.execute(() -> {
                    pushMessage(messageRequest);
                });
            } else {
                // 同步推送
                pushMessage(messageRequest);
            }
        }
    }

    private void pushMessage(PushMessageRequest messageRequest) {
        if (log.isDebugEnabled()) {
            log.debug("消息推送==> {}", messageRequest);
        }
        for (ClientMessagePusher clientMessagePusher : clientMessagePushers) {
            if (clientMessagePusher.supports(messageRequest)) {
                continue;
            }
            clientMessagePusher.push(messageRequest);
        }
    }


    /**
     * 替换模板变量
     *
     * @param template       模板内容
     * @param positionParams 位置参数
     * @param namedParams    命名参数
     * @return 替换后的消息内容
     */
    protected String replaceTemplateContent(String template, String[] positionParams, Map<String, String> namedParams) {
        boolean noneReplace = template == null || (positionParams == null && namedParams == null);
        if (noneReplace) {
            return template;
        }

        if (positionParams != null) {
            template = MessageFormat.format(template, positionParams);
        }

        if (namedParams != null) {
            StringSubstitutor sub = new StringSubstitutor(namedParams, "{", "}", '{');
            template = sub.replace(template);
        }

        return template;
    }
}
