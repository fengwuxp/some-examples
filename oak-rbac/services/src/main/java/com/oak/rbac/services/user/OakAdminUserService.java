package com.oak.rbac.services.user;

import com.oak.rbac.constant.RbacInjectVar;
import com.oak.rbac.entities.OakAdminUser;
import com.oak.rbac.services.user.info.OakAdminUserInfo;
import com.oak.rbac.services.user.req.*;
import com.wuxp.api.ApiResp;
import com.wuxp.api.context.InjectField;
import com.wuxp.api.model.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;


/**
 * 管理员用户服务
 * 2020-1-16 18:28:37
 */
public interface OakAdminUserService {


    ApiResp<OakAdminUser> create(CreateOakAdminUserReq req);


    ApiResp<Void> edit(EditOakAdminUserReq req);

    ApiResp<Void> editAdminRole(EditOakAdminUserReq req);

    ApiResp<Void> delete(DeleteOakAdminUserReq req);


    OakAdminUserInfo findById(Long id);


    Pagination<OakAdminUserInfo> query(QueryOakAdminUserReq req);

    /**
     * 修改密码
     *
     * @return
     */
    ApiResp<Void> changePassword(ChangePasswordReq req);

    /**
     * 重置密码
     */
    ApiResp<Void> resetPassword(Long adminId);
}
