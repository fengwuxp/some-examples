package com.oak.rbac.initiator;

import com.oak.rbac.entities.OakAdminUser;
import com.oak.rbac.services.user.OakAdminUserService;
import com.oak.rbac.services.user.req.CreateOakAdminUserReq;
import com.oak.rbac.services.user.req.QueryOakAdminUserReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.initiator.AbstractBaseInitiator;
import com.wuxp.api.model.QueryType;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 系统管理员初始化
 *
 * @author wuxp
 */
public class SystemAdminInitiator extends AbstractBaseInitiator<CreateOakAdminUserReq> {

    @Autowired
    private OakAdminUserService oakAdminUserService;


    @Override
    protected boolean initSingleItem(CreateOakAdminUserReq data) {

        QueryOakAdminUserReq req = new QueryOakAdminUserReq();
        req.setUserName(data.getUserName())
                .setBusinessModule(data.getBusinessModule())
                .setQueryType(QueryType.QUERY_NUM);
        long total = oakAdminUserService.query(req).getTotal();
        if (total > 0) {
            return true;
        }
        // 一定是超级用户
        data.setRoot(Boolean.TRUE);
        ApiResp<OakAdminUser> oakAdminUserApiResp = oakAdminUserService.create(data);
        return oakAdminUserApiResp.isSuccess();
    }
}
