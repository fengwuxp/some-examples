package com.oak.member.logic.deposit;

import com.oak.member.logic.deposit.req.TransferRequest;
import com.wuxp.api.ApiResp;


/**
 * 提现提供者，针对具体的提现方式进行操作
 * 例如：
 * <p>
 * 支付宝
 * 微信
 * 银联等
 * </p>
 *
 * @author wxup
 */
public interface TransferProvider {


    /**
     * 提现
     *
     * @param request
     */
    ApiResp<Void> transfer(TransferRequest request);


    /**
     * 判断是否支持当前提现方式
     *
     * @param code 提示方式的唯一标识
     * @return 返回<code>true</code> 则调用 {@link #transfer}
     */
    boolean supports(String code);
}
