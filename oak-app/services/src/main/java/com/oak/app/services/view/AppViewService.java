package com.oak.app.services.view;

import com.oak.app.services.view.info.AppViewInfo;
import com.oak.app.services.view.req.CreateAppViewReq;
import com.oak.app.services.view.req.QueryAppViewReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author ChenXiaoBin
 * on 2020-05-21
 */
public interface AppViewService {



    ApiResp<Long> create(CreateAppViewReq req);

    @Schema(description = "查询客户端视图")
    Pagination<AppViewInfo> query(QueryAppViewReq req);
}
