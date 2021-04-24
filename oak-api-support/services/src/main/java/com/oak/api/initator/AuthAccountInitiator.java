package com.oak.api.initator;


import com.oak.api.services.app.AppAuthService;
import com.oak.api.services.app.info.AppAuthAccountInfo;
import com.oak.api.services.app.req.CreateAppAuthAccountReq;
import com.oak.api.services.app.req.QueryAppAuthAccountReq;
import com.wuxp.api.initiator.AbstractBaseInitiator;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QueryType;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

/**
 * 鉴权账号初始化器
 */
@Slf4j
public class AuthAccountInitiator extends AbstractBaseInitiator<CreateAppAuthAccountReq> {


    private AppAuthService appAuthService;


    @Override
    @Transactional(rollbackOn = Exception.class)
    public void init() {
        super.init();
    }

    @Override
    protected boolean initSingleItem(CreateAppAuthAccountReq data) {
        QueryAppAuthAccountReq queryAuthAccountEvt = new QueryAppAuthAccountReq(data.getAppId());
        queryAuthAccountEvt.setQueryType(QueryType.QUERY_NUM);
        Pagination<AppAuthAccountInfo> page = appAuthService.queryAppAuthAccount(queryAuthAccountEvt);
        if (page.isEmpty()) {
            Long appAuthAccount = appAuthService.createAppAuthAccount(data);
            if (log.isDebugEnabled()) {
                log.debug("创建接入账号【" + data + "】-> {} ,{}", data, appAuthAccount);
            }
            return true;
        }
        return false;
    }


    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        if (this.appAuthService == null) {
            this.appAuthService = this.beanFactory.getBean(AppAuthService.class);
        }
    }
}
