package com.oak.member.initator;

import com.oak.member.services.depositcashmethod.DepositCashMethodService;
import com.oak.member.services.depositcashmethod.info.DepositCashMethodInfo;
import com.oak.member.services.depositcashmethod.req.CreateDepositCashMethodReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.initiator.AbstractBaseInitiator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Classname DepositCashMethodInitator
 * @Description 提现设置方式初始化器
 * @Date 2020/6/8 17:03
 * @Created by 44487
 */
public class DepositCashMethodInitator extends AbstractBaseInitiator<DepositCashMethodInitialSetting> {

    @Autowired
    private DepositCashMethodService depositCashMethodService;

    @Override
    protected boolean initSingleItem(DepositCashMethodInitialSetting data) {

        DepositCashMethodInfo cashMethodInfo = depositCashMethodService.findByCode(data.getCode());

        if( cashMethodInfo == null ){
            //需要初始化

            CreateDepositCashMethodReq createDepositCashMethodReq = new CreateDepositCashMethodReq();
            BeanUtils.copyProperties( data ,createDepositCashMethodReq );

            ApiResp<Long> apiResp = depositCashMethodService.create(createDepositCashMethodReq);

            return apiResp.isSuccess();
        }else {
            return true;
        }
    }
}
