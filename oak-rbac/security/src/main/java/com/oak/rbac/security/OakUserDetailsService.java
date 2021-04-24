package com.oak.rbac.security;

import com.oak.rbac.configuration.OakRbacProperties;
import com.oak.rbac.services.role.info.RoleInfo;
import com.oak.rbac.services.user.OakAdminUserService;
import com.oak.rbac.services.user.info.OakAdminUserInfo;
import com.oak.rbac.services.user.req.QueryOakAdminUserReq;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static com.oak.rbac.authority.OakRequestUrlResourceProvider.ROLE_PREFIX;
import static com.oak.rbac.authority.OakRequestUrlResourceProvider.ROOT_ROLE;


/**
 * @author wxup
 */
@Slf4j
@Setter
public class OakUserDetailsService implements UserDetailsService, InitializingBean, BeanFactoryAware {

    private BeanFactory beanFactory;

    private OakAdminUserService oakAdminUserService;

    private OakRbacProperties oakRbacProperties;

    @Override
    public OakUser loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!StringUtils.hasText(username)) {
            throw new UsernameNotFoundException("username is empty");
        }


        /**
         * 登录流程概述
         * 1： 从数据加载用户信息和rbac账号信息
         * 2:  生成jwt token
         * 3： 将用户信息加入到缓存中（缓存使用spring cache）
         */
        //账号信息
        QueryOakAdminUserReq queryOakAdminUserReq = new QueryOakAdminUserReq();
        queryOakAdminUserReq.setUserName(username)
                .setDeleted(false)
                .setFetchRole(true);
        OakAdminUserInfo adminUserInfo = oakAdminUserService.query(queryOakAdminUserReq).getFirst();

        if (adminUserInfo == null) {
            throw new UsernameNotFoundException(username);
        }

        // 用户鉴别，判断是否在同一个业务模块
        if (!oakRbacProperties.getBusinessModule().equals(adminUserInfo.getBusinessModule())) {
            throw new UsernameNotFoundException(MessageFormat.format("not support business module ,user：{0}", adminUserInfo.getBusinessModule()));
        }

        boolean isLocked = adminUserInfo.getLockExpired() != null && adminUserInfo.getLockExpired().getTime() > System.currentTimeMillis();
        if (isLocked) {
            throw new LockedException("用户被锁定");
        }

        return converterOakUser(adminUserInfo);
    }

    private static OakUser converterOakUser(OakAdminUserInfo adminUserInfo) {
        // AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_APP")
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (adminUserInfo.getRoot()) {
            // 超级管理员
            authorities.addAll(AuthorityUtils.createAuthorityList(ROOT_ROLE));
        }

        Set<RoleInfo> roles = adminUserInfo.getRoles();
        if (roles != null && !roles.isEmpty()) {
            String[] roleNames = roles.stream()
                    .map(roleInfo -> MessageFormat.format("{0}{1}", ROLE_PREFIX, roleInfo.getName()))
                    .toArray(String[]::new);
            authorities.addAll(AuthorityUtils.createAuthorityList(roleNames));
        }

        OakUser oakUser = new OakUser(adminUserInfo.getUserName(),
                adminUserInfo.getPassword(),
                adminUserInfo.getEnabled(),
                true,
                true,
                true,
                authorities);
        oakUser.setCryptoSalt(adminUserInfo.getCryptoSalt())
                .setEmail(adminUserInfo.getEmail())
                .setId(adminUserInfo.getId())
                .setMobilePhone(adminUserInfo.getMobilePhone())
                .setRoot(adminUserInfo.getRoot())
                .setName(adminUserInfo.getName());
        return oakUser;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        BeanFactory beanFactory = this.beanFactory;
        if (this.oakAdminUserService == null) {
            this.oakAdminUserService = beanFactory.getBean(OakAdminUserService.class);
        }

        if (this.oakRbacProperties == null) {
            this.oakRbacProperties = beanFactory.getBean(OakRbacProperties.class);
        }
    }
}
