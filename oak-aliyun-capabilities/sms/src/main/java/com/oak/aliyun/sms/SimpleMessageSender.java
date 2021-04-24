package com.oak.aliyun.sms;


import com.alibaba.cloud.spring.boot.sms.ISmsService;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.oak.aliyun.sms.configuration.OakSmsProperties;
import com.wuxp.security.captcha.mobile.MobileCaptchaSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;

/**
 * 基于阿里云的短信发送
 *
 * @author wxup
 */
@Slf4j
public class SimpleMessageSender implements MobileCaptchaSender {


    /**
     * https://help.aliyun.com/document_detail/55284.html?spm=a2c4g.11186623.6.667.731634fcMVjEtG
     */
    private static final String SUCCESS_CODE = "OK";

    @Autowired
    private ISmsService iSmsService;

//    @Autowired
//    private SmsProperties smsProperties;

    @Autowired
    private OakSmsProperties oakSmsProperties;

    @Override
    public MobileCaptchaSenderResult send(String useType, String mobilePhone, String value) {

        if (!oakSmsProperties.getWhetherSend()) {
            return new MobileCaptchaSenderResult(true, null);
        }

        // 组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        // 必填:待发送手机号
        request.setPhoneNumbers(mobilePhone);
        // 必填:短信签名-可在短信控制台中找到
        request.setSignName(oakSmsProperties.getSignName());
        // 必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(oakSmsProperties.getTemplateCodes().get(useType.toLowerCase()));
        // 必填:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        // 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
        request.setTemplateParam(MessageFormat.format("'{'\"code\":\"{0}\"'}'", value));
        try {
            SendSmsResponse sendSmsResponse = iSmsService.sendSmsRequest(request);
            if (SUCCESS_CODE.equals(sendSmsResponse.getCode())) {
                return new MobileCaptchaSenderResult(true, null);
            }
            return new MobileCaptchaSenderResult(false, sendSmsResponse.getMessage());

        } catch (ClientException clientException) {
            log.error("阿里云短信发送失败", clientException);
            return new MobileCaptchaSenderResult(true, "短信发送异常");
        }
    }
}
