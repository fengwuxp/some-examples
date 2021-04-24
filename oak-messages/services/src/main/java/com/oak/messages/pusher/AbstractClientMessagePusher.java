package com.oak.messages.pusher;

import com.oak.app.enums.ViewType;
import com.oak.app.services.view.AppViewService;
import com.oak.app.services.view.info.AppViewInfo;
import com.oak.app.services.view.req.QueryAppViewReq;
import com.oak.messages.ClientMessagePusher;
import com.oak.messages.request.PushMessageRequest;
import com.oak.messages.services.notice.NoticeMessageService;
import com.oak.messages.services.notice.req.CreateNoticeMessageReq;
import com.wuxp.api.ApiResp;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象的消息推送者
 *
 * @author wuxp
 */
public abstract class AbstractClientMessagePusher implements ClientMessagePusher {

    @Autowired
    protected AppViewService appViewService;

    @Autowired
    protected NoticeMessageService messageService;



    /**
     * 持久化消息
     *
     * @param request
     * @return
     */
    protected boolean persistenceMessage(PushMessageRequest request) {
        if (Boolean.FALSE.equals(request.getPersistence())) {
            return true;
        }
        CreateNoticeMessageReq req = new CreateNoticeMessageReq();
        req.setContent(request.getContent())
                .setTitle(request.getTitle())
                .setMemberId(request.getMemberId());
        ApiResp<Long> resp = messageService.create(req);
        return resp.isSuccess();
    }

    /**
     * 获取
     *
     * @param viewType
     * @param viewParams
     * @return
     */
    protected Map<String, String> getView(ViewType viewType, String[] viewParams) {
        Map<String, String> params = new HashMap<>();
        Map<ViewType, AppViewInfo> appViews = getAppView(params);
        String viewKey = "view";
        String paramKey = "param";
        AppViewInfo appViewInfo = appViews.get(viewType);
        if (appViewInfo != null) {
            params.put(viewKey, appViewInfo.getPath());
            if (viewParams != null) {
                params.put(paramKey, appViewInfo.getParams(viewParams));
            }
        }
        return params;
    }


    /**
     * 获取app视图
     *
     * @param params
     * @return
     */
    protected Map<ViewType, AppViewInfo> getAppView(Map<String, String> params) {

        Map<ViewType, AppViewInfo> appViews = new HashMap<>();
        String viewCodeKey = "viewCode";
        if (params.containsKey(viewCodeKey)) {
            QueryAppViewReq queryAppViewEvt = new QueryAppViewReq();
            queryAppViewEvt.setCode(params.get(viewCodeKey));
            List<AppViewInfo> records = appViewService.query(queryAppViewEvt).getRecords();
            for (AppViewInfo appViewInfo : records) {
                appViews.put(appViewInfo.getType(), appViewInfo);
            }
        }
        return appViews;
    }
}
