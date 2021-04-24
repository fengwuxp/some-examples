package com.oak.rbac.services.token;

import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import com.oak.rbac.services.token.req.*;
import com.oak.rbac.services.token.info.OakAdminUserTokenInfo;


/**
 * 管理员用户登录的token信息服务
 * 2020-7-23 10:27:50
 */
public interface OakAdminUserTokenService {


    ApiResp<Long> create(CreateOakAdminUserTokenReq req);


    ApiResp<Void> delete(DeleteOakAdminUserTokenReq req);


    OakAdminUserTokenInfo findById(Long id);


    Pagination<OakAdminUserTokenInfo> query(QueryOakAdminUserTokenReq req);

}
