package com.oak.messages.pusher;

import com.alibaba.fastjson.JSON;
import com.oak.messages.ClientMessagePusher;
import com.oak.messages.MessagePushType;
import com.oak.messages.enums.SimpleMessagePushType;
import com.oak.messages.request.PushMessageRequest;
import com.oak.messages.services.notice.NoticeMessageService;
import com.oak.messages.services.notice.req.CreateNoticeMessageReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 站内消息推送者
 * 基于数据库推送通知消息
 *
 * @author wuxp
 */
@Slf4j
public class StationMessagePusher implements ClientMessagePusher {


    @Autowired
    private NoticeMessageService noticeMessageService;

    @Override
    public void push(PushMessageRequest request) {

        CreateNoticeMessageReq req = new CreateNoticeMessageReq();
        req.setViewCode(req.getViewCode())
                .setMemberId(req.getMemberId())
                .setCode(request.getTemplateId())
                .setTitle(request.getTitle())
                .setContent(request.getContent())
                .setGroup(req.getGroup())
                .setBusinessType(req.getBusinessType())
                .setViewCode(request.getViewCode())
                .setViewParams(request.getViewParams() == null ? null : JSON.toJSONString(request.getViewParams()));

        ApiResp<Long> resp = noticeMessageService.create(req);
        AssertThrow.assertResp(resp);

    }

    @Override
    public boolean supports(PushMessageRequest request) {
        if (SimpleMessagePushType.STATION.equals(request.getPushType())) {
            return true;
        }
        return request.getPersistence();
    }
}

