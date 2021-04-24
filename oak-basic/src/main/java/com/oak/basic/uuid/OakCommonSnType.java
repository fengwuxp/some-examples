package com.oak.basic.uuid;

import com.wuxp.basic.uuid.sn.SnGenerateStrategy;


/**
 * 常见的 sn类型
 *
 * @author wxup
 */
public enum OakCommonSnType implements SnGenerateStrategy.SnType {

    /**
     * 订单的sn
     */
    ORDER_SN("11"),

    /**
     * 支付订单sn
     */
    PAYMENT_ORDER_SN("12"),

    /**
     * 支付单sn
     */
    PAYMENT_SN("13"),

    /**
     * 退款订单sn
     */
    REFUND_ORDER_SN("22"),

    /**
     * 退款单sn
     */
    REFUND_SN("23"),

    /**
     * 用户账户日志
     */
    ACCOUNT_LOG_SN("14"),


    /**
     * 发货单sn
     */
    SHIPPING("SH");

    private final String code;


    OakCommonSnType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
