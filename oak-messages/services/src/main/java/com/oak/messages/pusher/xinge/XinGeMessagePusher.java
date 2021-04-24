package com.oak.messages.pusher.xinge;

import com.alibaba.fastjson.JSON;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.oak.api.enums.ClientType;
import com.oak.app.enums.ViewType;
import com.oak.messages.enums.MessageShowType;
import com.oak.messages.enums.SimpleMessagePushType;
import com.oak.messages.pusher.AbstractClientMessagePusher;
import com.oak.messages.request.PushMessageRequest;
import com.tencent.xinge.XingeApp;
import com.tencent.xinge.bean.*;
import com.tencent.xinge.push.app.PushAppRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于腾讯信鸽推送实现的推送者
 *
 * @author wuxp
 * @doc https://xg.qq.com/docs/
 */
@Slf4j
public class XinGeMessagePusher extends AbstractClientMessagePusher {

    private final XinGeConfiguration configuration;

    private final XingeApp androidXinGeApp;

    private final XingeApp iosXinGeApp;

    private final Gson gson = new Gson();


    public XinGeMessagePusher(XinGeConfiguration configuration) {
        super();
        this.configuration = configuration;
        this.androidXinGeApp = new XingeApp(configuration.getIosAccessId(), configuration.getAndroidSecretKey());
        this.iosXinGeApp = new XingeApp(configuration.getIosAccessId(), configuration.getIosSecretKey());
    }

    @Override
    public void push(PushMessageRequest request) {
        Map<String, String> params;
        if (!StringUtils.isEmpty(request.getParams())) {
            params = gson.fromJson(request.getParams(), new TypeToken<Map<String, String>>() {
            }.getType());
        } else {
            params = new HashMap<>();
        }
        this.sendAndroid(request, params);
        this.sendIos(request, params);
    }


    @Override
    public boolean supports(PushMessageRequest request) {
        return SimpleMessagePushType.PUSH.equals(request.getPushType());
    }

    private void sendAndroid(PushMessageRequest request, Map<String, String> params) {
        if (request.getClientType() != null && !ClientType.ANDROID.name().equals(request.getClientType())) {
            return;
        }

        com.tencent.xinge.bean.Message message = new com.tencent.xinge.bean.Message();
        message.setTitle(request.getTitle());
        message.setContent(request.getContent());
        MessageAndroid messageAndroid = new MessageAndroid();
        if (params != null) {
            // 自定义内容
            messageAndroid.setCustom_content(gson.toJson(getView(ViewType.ANDROID, request.getViewParams())));
        } else if (!StringUtils.isEmpty(request.getParams())) {
            messageAndroid.setCustom_content(request.getParams());
        }
        message.setAndroid(messageAndroid);
        PushAppRequest pushAppRequest = getPushAppRequest(request, message);
        JSONObject ret = androidXinGeApp.pushApp(JSON.toJSONString(pushAppRequest));
        int retCode = ret.getInt("ret_code");
        if (retCode != 0) {
            log.debug("【信鸽安卓推送】【{}】 {}", request.getMemberId(), ret);
        }
    }


    private void sendIos(PushMessageRequest request, Map<String, String> params) {
        if (request.getClientType() != null && !ClientType.IOS.name().equals(request.getClientType())) {
            return;
        }
        // IOSENV_PROD = 1; IOSENV_DEV = 2;
        Environment xgIosEnv = Boolean.TRUE.equals(configuration.getDev())
                ? Environment.dev
                : Environment.product;

        com.tencent.xinge.bean.Message message = new com.tencent.xinge.bean.Message();
        message.setTitle(request.getTitle());
        message.setContent(request.getContent());
        MessageIOS messageIOS = new MessageIOS();
        params.put("content", request.getContent());
        messageIOS.setCustom(gson.toJson(getView(ViewType.IOS, request.getViewParams())));

        Aps aps = new Aps();
        Alert alert = new Alert();
        alert.setTitle(request.getTitle());
        alert.setBody(request.getContent());
        aps.setAlert(alert);
        aps.setCategory("INVITE_CATEGORY");
        messageIOS.setAps(aps);
        message.setIos(messageIOS);


        PushAppRequest pushAppRequest = getPushAppRequest(request, message);
        pushAppRequest.setEnvironment(xgIosEnv);
        JSONObject ret = iosXinGeApp.pushApp(JSON.toJSONString(pushAppRequest));
        int retCode = ret.getInt("ret_code");
        if (retCode != 0) {
            log.debug("【信鸽ios推送】【{}】 {}", request.getMemberId(), ret);
        }

    }

    private PushAppRequest getPushAppRequest(PushMessageRequest request, Message message) {
        PushAppRequest pushAppRequest = new PushAppRequest();
        boolean isAccount = request.getMemberId() != null && request.getMemberId() != -1;
        pushAppRequest.setAudience_type(isAccount ? AudienceType.account : AudienceType.all);
        pushAppRequest.setPlatform(Platform.android);
        // 设置推送的消息类型
        pushAppRequest.setMessage_type(MessageShowType.TRANSPARENT.equals(request.getShowType()) ? MessageType.message : MessageType.notify);
        if (isAccount) {
            ArrayList<String> accountList = new ArrayList<>();
            accountList.add((request.getMemberId() < 10 ? "0" + request.getMemberId().toString() : request.getMemberId().toString()));
            pushAppRequest.setAccount_list(accountList);
        }
        pushAppRequest.setMessage(message);
        return pushAppRequest;
    }


}
