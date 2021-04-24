package com.oak.app.initiator;


import com.levin.commons.dao.JpaDao;
import com.oak.app.entities.AppVersion;
import com.oak.app.services.appversion.AppVersionService;
import com.oak.app.services.appversion.info.AppVersionInfo;
import com.oak.app.services.appversion.req.CreateAppVersionReq;
import com.oak.app.services.appversion.req.QueryAppVersionReq;
import com.wuxp.api.initiator.AbstractBaseInitiator;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.model.QueryType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class AppVersionIntiator extends AbstractBaseInitiator<CreateAppVersionReq> {

    @Autowired
    private AppVersionService appService;

    @Autowired
    private JpaDao jpaDao;

    @Override
    protected boolean initSingleItem(CreateAppVersionReq data) {
        QueryAppVersionReq queryEvt = new QueryAppVersionReq();
        queryEvt.setAppCoding(data.getAppCoding());
        queryEvt.setQueryType(QueryType.QUERY_NUM);
        Pagination<AppVersionInfo> appVersionInfoPagination = appService.query(queryEvt);
        if (appVersionInfoPagination.getRecords() == null) {
            AppVersion appVersion = new AppVersion();
            BeanUtils.copyProperties(data, appVersion);
            jpaDao.create(appVersion);
        }
        return true;
    }
}
