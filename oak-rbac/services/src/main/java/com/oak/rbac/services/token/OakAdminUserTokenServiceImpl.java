package com.oak.rbac.services.token;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import org.springframework.beans.BeanUtils;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.oak.rbac.entities.OakAdminUserToken;
import com.oak.rbac.services.token.req.*;
import com.oak.rbac.services.token.info.OakAdminUserTokenInfo;

import java.util.Date;


/**
 * 管理员用户登录的token信息服务
 * 2020-7-23 10:27:50
 */
@Service
@Slf4j
public class OakAdminUserTokenServiceImpl implements OakAdminUserTokenService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateOakAdminUserTokenReq req) {


        OakAdminUserToken entity = new OakAdminUserToken();
        BeanUtils.copyProperties(req, entity);


        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }


    @Override
    public ApiResp<Void> delete(DeleteOakAdminUserTokenReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r = jpaDao.deleteFrom(OakAdminUserToken.class).appendByQueryObj(req).delete() > 0;
        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public OakAdminUserTokenInfo findById(Long id) {

        QueryOakAdminUserTokenReq queryReq = new QueryOakAdminUserTokenReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<OakAdminUserTokenInfo> query(QueryOakAdminUserTokenReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, OakAdminUserToken.class, OakAdminUserTokenInfo.class, req);

    }
}
