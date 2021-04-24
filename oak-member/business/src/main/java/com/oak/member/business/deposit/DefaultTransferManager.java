package com.oak.member.business.deposit;


import com.oak.member.logic.deposit.TransferManager;
import com.oak.member.logic.deposit.TransferProvider;
import com.oak.member.logic.deposit.req.TransferRequest;
import com.oak.member.services.account.MemberAccountService;
import com.oak.member.services.account.info.MemberAccountInfo;
import com.oak.member.services.memberdepositcash.MemberDepositCashService;
import com.oak.member.services.memberdepositcash.info.MemberDepositCashInfo;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 提现管理器，主要实现不同场景的提现审核过程
 */
@Slf4j
@Setter
public class DefaultTransferManager implements TransferManager, InitializingBean, ApplicationContextAware {


    private List<TransferProvider> transferProviderList;

    private ApplicationContext applicationContext;

    @Autowired
    private MemberDepositCashService memberDepositCashService;

    @Autowired
    private MemberAccountService memberAccountService;

    /**
     * 提现转账操作，此过程已经包含提现审核动作
     *
     * 例如，线下转账场景，银行卡转账之后，则可以认为提现审核通过
     *       如果不给银行卡转账，则可以认为提现审核不通过
     *
     *       自动转账场景，支付宝或者是微信转账失败，则可以认为是提现不通过。
     *
     * @param request
     * @return
     */
    @Override
    public ApiResp<Void> transfer(TransferRequest request) {

        //提现申请的前置
        MemberDepositCashInfo memberDepositCashInfo = memberDepositCashService.findById(request.getId());

        if (memberDepositCashInfo == null) {
            return RestfulApiRespFactory.error("提现记录不存在");
        }

        MemberAccountInfo memberAccountInfo = memberAccountService.findById(memberDepositCashInfo.getMemberId());

        if (memberAccountInfo == null) {
            return RestfulApiRespFactory.error("获取会员账户信息失败");
        }

        if (memberDepositCashInfo.getAmount() > memberAccountInfo.getFrozenMoney()) {
            return RestfulApiRespFactory.error("会员账户冻结余额不足");
        }

        //具体的提现转账
        for (TransferProvider transferProvider : transferProviderList) {
            if (  transferProvider.supports( request.getDepositCashCode() )  ){
              return  transferProvider.transfer( request );
            }
        }
        return RestfulApiRespFactory.error("提现转账失败");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (transferProviderList == null) {
            try {
                Map<String, TransferProvider> memberCreatedProviderMap = this.applicationContext.getBeansOfType(TransferProvider.class);
                this.transferProviderList = new ArrayList<>(memberCreatedProviderMap.values());
            } catch (BeansException e) {
                log.error("初始化transferProviderList对象失败", e);
                this.transferProviderList = Collections.emptyList();
            }
        }
    }

    public List<TransferProvider> getMemberCreatedProviders() {
        return transferProviderList;
    }

}
