package com.oak.member.logic.deposit;


import com.oak.member.logic.deposit.req.TransferRequest;
import com.wuxp.api.ApiResp;

/**
 * 提现管理器 用来管理应用中提供的提现方式{@link TransferProvider}
 * {@link TransferProvider}
 *
 * @author wxup
 */
public interface TransferManager {

    /**
     *  发起提现
     *      {@link TransferProvider#supports(String)}
     *      {@link TransferProvider#transfer}
     * @param request
     * @return
     */
    ApiResp<Void> transfer(TransferRequest request);
}
