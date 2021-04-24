package com.oak.member.services.depositcashmethod;

import com.oak.member.services.depositcashmethod.info.DepositCashMethodInfo;
import com.oak.member.services.depositcashmethod.req.CreateDepositCashMethodReq;
import com.oak.member.services.depositcashmethod.req.DeleteDepositCashMethodReq;
import com.oak.member.services.depositcashmethod.req.EditDepositCashMethodReq;
import com.oak.member.services.depositcashmethod.req.QueryDepositCashMethodReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;



/**
 *  提现方式服务
 *  2020-6-8 16:51:49
 */
public interface DepositCashMethodService {


    ApiResp<Long> create(CreateDepositCashMethodReq req);


    ApiResp<Void> edit(EditDepositCashMethodReq req);


    ApiResp<Void> delete(DeleteDepositCashMethodReq req);


    DepositCashMethodInfo findById(Long id);


    Pagination<DepositCashMethodInfo> query(QueryDepositCashMethodReq req);

    DepositCashMethodInfo findByCode( String code );


}
