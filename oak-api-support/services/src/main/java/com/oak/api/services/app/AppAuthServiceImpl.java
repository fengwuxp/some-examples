package com.oak.api.services.app;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.entities.AppAuthAccount;
import com.oak.api.entities.E_AppAuthAccount;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.api.services.app.info.AppAuthAccountInfo;
import com.oak.api.services.app.req.CreateAppAuthAccountReq;
import com.oak.api.services.app.req.EditAppAuthAccountReq;
import com.oak.api.services.app.req.FindAuthReq;
import com.oak.api.services.app.req.QueryAppAuthAccountReq;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Slf4j
@Service
public class AppAuthServiceImpl implements AppAuthService {

    @Autowired
    private JpaDao jpaDao;

    @Override
    @Transactional
    public Long createAppAuthAccount(CreateAppAuthAccountReq req) {

        req.setEnabled(Boolean.TRUE.equals(req.getEnabled()));
        AppAuthAccount authAccount = new AppAuthAccount();
        BeanUtils.copyProperties(req, authAccount);
        authAccount.setAppId(req.getAppId());
        authAccount.setAppSecret(req.getAppSecret());
        authAccount.setCreateTime(new Date());
        authAccount.setUpdateTime(authAccount.getCreateTime());
        authAccount.setDeleted(false);
        authAccount.setChannelCode(req.getChannelCode());
        jpaDao.create(authAccount);

        return authAccount.getId();
    }

    @Caching(
            evict = {
                    @CacheEvict(value = APP_STORE_CACHE_NAME, key = "#req.appId", condition = "#req.appId!=null")
            }
    )
    @Override
    public boolean editAppAuthAccount(EditAppAuthAccountReq req) {

        UpdateDao<AppAuthAccount> updateDao = jpaDao.updateTo(AppAuthAccount.class)
                .appendByQueryObj(req);
        if (!StringUtils.isEmpty(req.getAppSecret())) {
            updateDao.appendColumn(E_AppAuthAccount.appSecret, req.getAppSecret());
        }
        int c = updateDao
                .appendColumn(E_AppAuthAccount.updateTime, new Date())
                .eq(E_AppAuthAccount.id, req.getId())
                .update();

        return c >= 1;

    }

    @Override
    public Pagination<AppAuthAccountInfo> queryAppAuthAccount(QueryAppAuthAccountReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, AppAuthAccount.class, AppAuthAccountInfo.class, req);
    }

    @Override
    public AppAuthAccountInfo findAppAuthAccount(FindAuthReq req) {
        QueryAppAuthAccountReq queryAppAuthAccountReq = new QueryAppAuthAccountReq();
        queryAppAuthAccountReq.setAppId(req.getClientAppId());
        Pagination<AppAuthAccountInfo> pageInfo = this.queryAppAuthAccount(queryAppAuthAccountReq);
        return pageInfo.getFirst();
    }

    @Cacheable(value = APP_STORE_CACHE_NAME,
            key = "#appId",
            condition = "#appId !=null",
            unless = "#result==null")
    @Override
    public AppAuthAccountInfo getAppInfo(@NotNull String appId) {
        return this.findAppAuthAccount(new FindAuthReq(appId));
    }
}
