package com.oak.app.services.appversion;

import com.oak.app.services.appversion.info.AppVersionInfo;
import com.oak.app.services.appversion.req.CreateAppVersionReq;
import com.oak.app.services.appversion.req.DeleteAppVersionReq;
import com.oak.app.services.appversion.req.EditAppVersionReq;
import com.oak.app.services.appversion.req.QueryAppVersionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 * app版本服务
 * 2020-7-4 17:56:49
 */
public interface AppVersionService {


    ApiResp<Long> create(CreateAppVersionReq req);


    ApiResp<Void> edit(EditAppVersionReq req);


    ApiResp<Void> delete(DeleteAppVersionReq req);


    AppVersionInfo findById(Long id);


    Pagination<AppVersionInfo> query(QueryAppVersionReq req);


}
