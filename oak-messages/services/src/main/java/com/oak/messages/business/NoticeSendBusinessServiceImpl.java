package com.oak.messages.business;

import com.oak.messages.TemplateMessagePusher;
import com.oak.messages.enums.SendMode;
import com.oak.messages.pusher.DefaultTemplateMessagePusher;
import com.oak.messages.pusher.request.SimplePushTemplateMessageRequest;
import com.oak.messages.services.notice.NoticeMessageService;
import com.oak.messages.services.notice.req.CreateNoticeMessageReq;
import com.oak.messages.services.template.MessageTemplateService;
import com.oak.messages.services.template.info.MessageTemplateInfo;
import com.oak.messages.services.template.req.CreateMessageTemplateReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QueryType;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Date： 2020/7/13 17:39
 *
 * @author ZHM
 */

@Service
@Slf4j
public class NoticeSendBusinessServiceImpl implements NoticeSendBusinessService {

    public static final String DEFAULT_MESSAGE = "SYSTEM";
    public static final String DEFAULT_GROUP = "DEFAULT";

    @Autowired
    private MessageTemplateService messageTemplateService;


    @Autowired
    private TemplateMessagePusher templateMessagePusher;

    @Autowired
    private NoticeMessageService noticeMessageService;

    @Override
    public ApiResp<Void> noticeSend(Long templateId,List<Long> memberIdList) {
        memberMessagePush(templateId, memberIdList);
        return RestfulApiRespFactory.ok();
    }

    /**
     * 批量发送
     *
     * @param templateId     模板Id
     * @param memberIdList 会员信息
     */
    private void memberMessagePush(Long templateId, List<Long> memberIdList) {

        MessageTemplateInfo templateInfo = messageTemplateService.findById(templateId);
        AssertThrow.assertNotEquals("该模板不存在", null, templateInfo);

        if (memberIdList != null && !memberIdList.isEmpty()) {
            for (Long memberId : memberIdList) {
                //拼装发送的消息
                SimplePushTemplateMessageRequest simplePushTemplateMessageRequest = new SimplePushTemplateMessageRequest();
                simplePushTemplateMessageRequest.setMemberId(memberId);
                simplePushTemplateMessageRequest.setSendMode(SendMode.ASYNC);
                simplePushTemplateMessageRequest.setTemplateId(templateInfo.getCode());

                //是否消息持久化
                if( templateInfo.getPersistence() != null && templateInfo.getPersistence() ){

                    CreateNoticeMessageReq createNoticeMessageReq = new CreateNoticeMessageReq();
                    createNoticeMessageReq.setBusinessType(templateInfo.getBusinessType())
                            .setCode(templateInfo.getCode())
                            .setContent(templateInfo.getContent())
                            .setGroup(templateInfo.getGroup())
                            .setMemberId(memberId)
                            .setTitle(templateInfo.getTitle());

                    ApiResp<Long> apiResp = noticeMessageService.create(createNoticeMessageReq);
                    AssertThrow.assertResp(apiResp);
                }

                //发送
                templateMessagePusher.pushMessage(simplePushTemplateMessageRequest);
            }
        }
    }


}
