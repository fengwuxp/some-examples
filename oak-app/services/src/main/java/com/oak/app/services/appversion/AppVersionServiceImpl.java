package com.oak.app.services.appversion;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.app.entities.AppVersion;
import com.oak.app.entities.E_AppVersion;
import com.oak.app.services.appversion.info.AppVersionInfo;
import com.oak.app.services.appversion.req.CreateAppVersionReq;
import com.oak.app.services.appversion.req.DeleteAppVersionReq;
import com.oak.app.services.appversion.req.EditAppVersionReq;
import com.oak.app.services.appversion.req.QueryAppVersionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * app版本服务
 * 2020-7-4 17:56:49
 */
@Service
@Slf4j
public class AppVersionServiceImpl implements AppVersionService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateAppVersionReq req) {

        //验证“版本号”是匹配版本号
//        if (!req.getVersion().startsWith(String.valueOf(req.getAppCodeNum()))) {
//            return RestfulApiRespFactory.error("版本号与当前版本不匹配!");
//        }

        AppVersion entity = new AppVersion();
        BeanUtils.copyProperties(req, entity);

        Date date = new Date();
        entity.setCreateTime(date);
        entity.setPublicTime(date);
        entity.setLastUpdateTime(date);
        entity.setDeleted(false);

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditAppVersionReq req) {

        AppVersion entity = jpaDao.find(AppVersion.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("app版本数据不存在");
        }

        UpdateDao<AppVersion> updateDao = jpaDao.updateTo(AppVersion.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新app版本失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteAppVersionReq req) {

        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(AppVersion.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(AppVersion.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除app版本");
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public AppVersionInfo findById(Long id) {

        QueryAppVersionReq queryReq = new QueryAppVersionReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<AppVersionInfo> query(QueryAppVersionReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, AppVersion.class, AppVersionInfo.class, req);

    }

}
