package com.oak.messages.services.notice;

import com.oak.messages.services.notice.info.NoticeMessageInfo;
import com.oak.messages.services.notice.req.CreateNoticeMessageReq;
import com.oak.messages.services.notice.req.DeleteNoticeMessageReq;
import com.oak.messages.services.notice.req.EditNoticeMessageReq;
import com.oak.messages.services.notice.req.QueryNoticeMessageReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;



/**
 *  通知消息服务
 *  2020-7-7 13:59:04
 */
public interface NoticeMessageService {


    ApiResp<Long> create(CreateNoticeMessageReq req);


    ApiResp<Void> edit(EditNoticeMessageReq req);


    ApiResp<Void> delete(DeleteNoticeMessageReq req);


    NoticeMessageInfo findById(Long id);


    Pagination<NoticeMessageInfo> query(QueryNoticeMessageReq req);

}
