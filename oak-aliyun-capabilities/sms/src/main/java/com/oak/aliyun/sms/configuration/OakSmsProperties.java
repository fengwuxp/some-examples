package com.oak.aliyun.sms.configuration;

import com.wuxp.security.captcha.mobile.MobileCaptchaType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Map;


@Schema(description = "阿里云sms 配置")
@Data
public class OakSmsProperties {


    static final String PREFIX = "alibaba.cloud.sms";


    /**
     * 短信模板代码
     *
     * @key 短信类型  {@link MobileCaptchaType}
     * @value 阿里云短信模板id
     */
    private Map<String, String> templateCodes;

    /**
     * 短信签名名称
     */
    private String signName;


    /**
     * 是否下发短信到手机
     */
    private Boolean whetherSend;


}
