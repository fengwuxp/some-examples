package com.oak.member.constant;

/**
 * @Classname DepositCashMethodInitialSettingConstant
 * @Description 提现配置方式初始化Code对照
 * @Date 2020/6/9 15:16
 * @Created by 44487
 */
public class DepositCashMethodInitialSettingConstant {

    /**
     * 线下提现编号（Code），通过银行卡的方式
     */
    public static final String OFFLINE_BANK_CARD_WITHDRAWAL_CODE  =  "OFFLINE_BANK_CARD";

    /**
     * 线上支付宝提现编号（Code），通过支付宝线上自动提现的方式
     */
    public static final String ONLINE_ALIPAY_WITHDRAWAL_CODE  =  "ONLINE_ALIPAY_WITHDRAWAL";

    /**
     * 线上微信提现编号（Code），通过微信线上自动提现的方式
     */
    public static final String ONLINE_WECHATPAY_WITHDRAWAL_CODE  =  "ONLINE_WECHATPAY_WITHDRAWAL";

}
