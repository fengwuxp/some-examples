package com.oak.rbac.security;

import com.oak.rbac.services.token.OakAdminUserTokenService;
import com.oak.rbac.services.token.info.OakAdminUserTokenInfo;
import com.oak.rbac.services.token.req.CreateOakAdminUserTokenReq;
import com.oak.rbac.services.token.req.DeleteOakAdminUserTokenReq;
import com.oak.rbac.services.token.req.QueryOakAdminUserTokenReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.api.model.Pagination;
import com.wuxp.security.authenticate.session.AbstractAuthenticateSessionManager;
import io.jsonwebtoken.JwtException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * admin用户会话缓存
 *
 * @author wxup
 */
@Slf4j
@Setter
public class OakAdminAuthenticateSessionManager extends AbstractAuthenticateSessionManager<OakUser> {

    /**
     * 用户登录信息的缓存
     */
    public static final String OAK_ADMIN_USER_CACHE_NAME = "OAK_ADMIN_USER_CACHE";

    /**
     * 用户登录token的缓存
     */
    public static final String OAK_ADMIN_USER_TOKEN_CACHE_NAME = "OAK_ADMIN_USER_TOKEN_CACHE";

    /**
     * 用户被踢出登录的原因的缓存
     */
    public static final String OAK_ADMIN_USER_KICK_OUT_REASON_CACHE_NAME = "OAK_ADMIN_USER_KICK_OUT_REASON_CACHE";


    private OakAdminUserTokenService oakAdminUserTokenService;

    public OakAdminAuthenticateSessionManager() {
        super(OakUser.class);
    }

    @Override
    public OakUser join(OakUser userDetails) {

        OakUser oakUser = this.genAuthenticateToken(userDetails);
        // 保存用户的token
        CreateOakAdminUserTokenReq req = new CreateOakAdminUserTokenReq();
        req.setChannelCode(oakUser.getClientCode())
                .setExpirationDate(oakUser.getTokenExpired())
                .setLoginTime(new Date())
                .setRefreshExpirationDate(oakUser.getTokenExpired())
                .setRefreshToken(oakUser.getToken())
                .setToken(oakUser.getToken())
                .setUserId(oakUser.getId());
        ApiResp<Long> resp = oakAdminUserTokenService.create(req);
        AssertThrow.assertResp(resp);

        return super.join(oakUser);
    }


    @Override
    public void tryKickOut(OakUser userDetails, String reason) {
        tryKickOut(userDetails.getToken(), reason);
    }

    @Override
    protected OakUser findUserByToken(String token) throws UsernameNotFoundException {

        // 从数据库加载
        String username = this.getUsername(token);
        UserDetails details = userDetailsService.loadUserByUsername(username);
        if (details == null) {
            throw new UsernameNotFoundException("未查询到该用户，username：" + username);
        }
        OakUser userDetails = (OakUser) details;
        QueryOakAdminUserTokenReq req = new QueryOakAdminUserTokenReq();
        req.setUserId(userDetails.getId())
                .setChannelCode(userDetails.getClientCode())
                .setToken(token)
                .setQuerySize(1);
        OakAdminUserTokenInfo adminUserTokenInfo = oakAdminUserTokenService.query(req).getFirst();

        if (adminUserTokenInfo == null) {
            return null;
        }
        Date expirationDate = adminUserTokenInfo.getExpirationDate();
        userDetails.setToken(token);
        userDetails.setEffectiveMilliseconds(expirationDate.getTime() - System.currentTimeMillis());
        userDetails.setTokenExpired(expirationDate);
        return userDetails;
    }

    @Override
    protected void tryRemoveDbToken(OakUser user) {

        QueryOakAdminUserTokenReq req = new QueryOakAdminUserTokenReq();
        req.setUserId(user.getId())
                .setChannelCode(user.getClientCode());
        Pagination<OakAdminUserTokenInfo> pagination = oakAdminUserTokenService.query(req);
        if (pagination.isEmpty()) {
            return;
        }
        // 查找当前token或已过期的token
        long currentTimeMillis = System.currentTimeMillis();
        Long[] ids = pagination.getRecords().
                stream()
                .filter(oakAdminUserTokenInfo -> user.getToken().equals(oakAdminUserTokenInfo.getRefreshToken())
                        || oakAdminUserTokenInfo.getExpirationDate().getTime() <= currentTimeMillis)
                .map(OakAdminUserTokenInfo::getId).toArray(Long[]::new);
        if (ids.length == 0) {
            return;
        }
        // 移除数据库中的token
        DeleteOakAdminUserTokenReq deleteOakAdminUserTokenReq = new DeleteOakAdminUserTokenReq();
        deleteOakAdminUserTokenReq.setIds(ids);
        ApiResp<Void> resp = oakAdminUserTokenService.delete(deleteOakAdminUserTokenReq);
        AssertThrow.assertResp(resp);
    }

    @Override
    protected List<String> getTokensByDb(String userName, String clientCode) {
        UserDetails details = userDetailsService.loadUserByUsername(userName);
        if (details == null) {
            return Collections.emptyList();
        }
        OakUser oakUser = (OakUser) details;
        QueryOakAdminUserTokenReq req = new QueryOakAdminUserTokenReq();
        req.setUserId(oakUser.getId())
                .setChannelCode(clientCode);
        Pagination<OakAdminUserTokenInfo> pagination = oakAdminUserTokenService.query(req);
        List<OakAdminUserTokenInfo> records = pagination.getRecords();

        // 如果存在已过期的token，先移除
        long currentTimeMillis = System.currentTimeMillis();
        Long[] needRemoveIds = records.stream()
                .filter(oakAdminUserTokenInfo -> oakAdminUserTokenInfo.getExpirationDate().getTime() <= currentTimeMillis)
                .map(OakAdminUserTokenInfo::getId)
                .toArray(Long[]::new);
        int length = needRemoveIds.length;
        if (length > 0) {
            if (log.isDebugEnabled()) {
                log.debug("删除过期的token，length = {}", length);
            }
            DeleteOakAdminUserTokenReq deleteOakAdminUserTokenReq = new DeleteOakAdminUserTokenReq();
            deleteOakAdminUserTokenReq.setIds(needRemoveIds);
            ApiResp<Void> resp = oakAdminUserTokenService.delete(deleteOakAdminUserTokenReq);
            AssertThrow.assertResp(resp);
        }

        return records.stream()
                .filter(oakAdminUserTokenInfo -> oakAdminUserTokenInfo.getExpirationDate().getTime() < currentTimeMillis)
                .map(OakAdminUserTokenInfo::getToken)
                .collect(Collectors.toList());
    }

    @Override
    protected String getUserCacheName() {
        return OAK_ADMIN_USER_CACHE_NAME;
    }

    @Override
    protected String getKickOutReasonCacheName() {
        return OAK_ADMIN_USER_KICK_OUT_REASON_CACHE_NAME;
    }


    @Override
    protected String getTokenCacheName() {
        return OAK_ADMIN_USER_TOKEN_CACHE_NAME;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        BeanFactory beanFactory = this.beanFactory;
        if (this.oakAdminUserTokenService == null) {
            this.oakAdminUserTokenService = beanFactory.getBean(OakAdminUserTokenService.class);
        }
        if (this.userDetailsService == null) {
            this.userDetailsService = beanFactory.getBean(OakUserDetailsService.class);
        }
    }

}
