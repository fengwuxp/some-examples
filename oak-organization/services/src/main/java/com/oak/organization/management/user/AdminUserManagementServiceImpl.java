package com.oak.organization.management.user;

import com.levin.commons.dao.JpaDao;
import com.oak.organization.entities.E_Staff;
import com.oak.organization.entities.Organization;
import com.oak.organization.entities.Staff;
import com.oak.organization.enums.ApprovalStatus;
import com.oak.organization.management.user.info.LoginAdminUserInfo;
import com.oak.organization.management.user.req.LoginAdminUserReq;
import com.oak.rbac.entities.E_OakAdminUser;
import com.oak.rbac.entities.OakAdminUser;
import com.oak.rbac.security.OakUser;
import com.oak.rbac.security.OakAdminAuthenticateSessionManager;
import com.oak.rbac.services.user.OakAdminUserService;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.security.jwt.JwtTokenPair;
import com.wuxp.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author: zhuox
 * @create: 2020-02-04
 * @description: 管理员用户服务
 **/
@Service
@Slf4j
public class AdminUserManagementServiceImpl implements AdminUserManagementService {

    @Autowired
    private JpaDao jpaDao;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private OakAdminUserService oakAdminUserService;
    @Autowired
    private OakAdminAuthenticateSessionManager userSessionCacheHelper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ApiResp<LoginAdminUserInfo> login(LoginAdminUserReq req) {
        OakAdminUser adminUser = jpaDao.selectFrom(OakAdminUser.class).eq(E_OakAdminUser.userName, req.getUserName()).findOne();
        if (adminUser == null) {
            RestfulApiRespFactory.error("用户不存在");
        }
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(adminUser.getCryptoSalt());
        if (!passwordEncoder.matches(req.getPassword(), adminUser.getPassword())) {
            RestfulApiRespFactory.error("密码错误");
        }
        Staff staff = jpaDao.selectFrom(Staff.class).eq(E_Staff.adminId, adminUser.getId()).findOne();
        Organization organization = staff.getOrganization();
        if (organization.getStatus().equals(ApprovalStatus.DISABLED)) {
            RestfulApiRespFactory.error("账号被禁用");
        }
        if (!organization.getStatus().equals(ApprovalStatus.AGREE)) {
            RestfulApiRespFactory.error("账号未审核通过");
        }
        LoginAdminUserInfo loginAdminUserInfo = new LoginAdminUserInfo();
        BeanUtils.copyProperties(adminUser, loginAdminUserInfo);
        UserDetails userDetails = userDetailsService.loadUserByUsername(adminUser.getUserName());
        OakUser principal = (OakUser) userDetails;
        userSessionCacheHelper.join(principal);
        loginAdminUserInfo.setToken(principal.getToken())
                .setTokenExpired(principal.getTokenExpired());
        return RestfulApiRespFactory.ok(loginAdminUserInfo);
    }

    @Override
    public ApiResp<LoginAdminUserInfo> refreshToken(String token) {
        OakUser oakUser = userSessionCacheHelper.get(token);
        if (oakUser == null) {
            return RestfulApiRespFactory.error("token已失效");
        }
        //生成TOKEN
        JwtTokenPair.JwtTokenPayLoad jwtTokenPayLoad = jwtTokenProvider.generateRefreshToken(oakUser.getUsername());
        UserDetails userDetails = userDetailsService.loadUserByUsername(oakUser.getUsername());
        OakUser principal = (OakUser) userDetails;
        userSessionCacheHelper.refreshToken(token, principal.getClientCode());
        LoginAdminUserInfo loginAdminUserInfo = new LoginAdminUserInfo();
        BeanUtils.copyProperties(oakUser, loginAdminUserInfo);
        loginAdminUserInfo.setUserName(oakUser.getUsername());
        loginAdminUserInfo.setToken(jwtTokenPayLoad.getToken())
                .setTokenExpired(jwtTokenPayLoad.getTokenExpireTimes());
        return RestfulApiRespFactory.ok(loginAdminUserInfo);
    }

}
