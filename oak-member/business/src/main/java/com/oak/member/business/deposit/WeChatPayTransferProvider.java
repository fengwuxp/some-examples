package com.oak.member.business.deposit;

import com.oak.member.logic.deposit.TransferProvider;
import com.oak.member.logic.deposit.req.TransferRequest;
import com.wuxp.api.ApiResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Classname WeChatPayTransferProvider
 * @Description 微信提现提现方式
 * @Date 2020/6/9 10:58
 * @Created by 44487
 */

@Service( "wechatpay_transfer_provider" )
@Slf4j
public class WeChatPayTransferProvider implements TransferProvider {

    @Override
    public ApiResp<Void> transfer(TransferRequest request) {
        return null;
    }

    @Override
    public boolean supports(String code) {
        return false;
    }
}
