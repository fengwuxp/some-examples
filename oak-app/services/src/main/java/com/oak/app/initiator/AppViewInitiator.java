package com.oak.app.initiator;


import com.oak.app.services.view.AppViewService;
import com.oak.app.services.view.info.AppViewInfo;
import com.oak.app.services.view.req.CreateAppViewReq;
import com.oak.app.services.view.req.QueryAppViewReq;
import com.wuxp.api.initiator.AbstractBaseInitiator;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QueryType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 初始化 客户端视图定义
 *
 * @author wuxp
 */
public class AppViewInitiator extends AbstractBaseInitiator<CreateAppViewReq> {

    @Autowired
    private AppViewService appViewService;

    @Override
    protected boolean initSingleItem(CreateAppViewReq data) {
        QueryAppViewReq req = new QueryAppViewReq();
        req.setCode(data.getCode())
                .setQueryType(QueryType.QUERY_NUM);
        Pagination<AppViewInfo> infoPagination = appViewService.query(req);
        if (infoPagination.getTotal() == 0) {
            return appViewService.create(data).isSuccess();
        }
        return true;
    }
}