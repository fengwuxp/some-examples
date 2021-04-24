package com.oak.app.services.view;

import com.levin.commons.dao.JpaDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.app.entities.AppView;
import com.oak.app.services.view.info.AppViewInfo;
import com.oak.app.services.view.req.CreateAppViewReq;
import com.oak.app.services.view.req.QueryAppViewReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ChenXiaoBin
 * on 2020-05-21
 */
@Service
@Slf4j
public class AppViewServiceImpl implements AppViewService {

    @Autowired
    private JpaDao jpaDao;


    @Override
    public ApiResp<Long> create(CreateAppViewReq req) {

        AppView entity = new AppView();
        BeanUtils.copyProperties(req, entity);
        Date date = new Date();
        entity.setCreateTime(date);
        entity.setLastUpdateTime(date);
        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public Pagination<AppViewInfo> query(QueryAppViewReq req) {
        return SimpleCommonDaoHelper.queryObject(jpaDao, AppView.class, AppViewInfo.class, req);
    }
}
