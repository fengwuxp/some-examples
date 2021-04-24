package com.oak.rbac.services.user;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.rbac.entities.*;
import com.oak.rbac.services.user.info.OakAdminUserInfo;
import com.oak.rbac.services.user.req.*;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.basic.uuid.JdkUUIDGenerateStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.oak.rbac.entities.OakAdminUser.DEFAULT_BUSINESS_MODULE;


/**
 * 管理员用户服务
 * 2020-1-16 18:28:37
 */
@Service
@Slf4j
public class OakAdminUserServiceImpl implements OakAdminUserService {

    private final String ADMIN_USER_CACHE_NAME = "ADMIN_USER_CACHE_NAME";

    @Autowired
    private JpaDao jpaDao;

    @Autowired
    private JdkUUIDGenerateStrategy uuidGenerateStrategy;

    private static final String DEFAULT_PASSWORD = "123456";


    @Override
    public ApiResp<OakAdminUser> create(CreateOakAdminUserReq req) {
        if (!StringUtils.hasText(req.getPassword())) {
            // 设置缺省密码
            req.setPassword(DEFAULT_PASSWORD);
        }

        Long creatorId = req.getCreatorId();
        OakAdminUserInfo oakAdminUserInfo = null;
        if (creatorId != null) {
            oakAdminUserInfo = this.findById(creatorId);
            if (oakAdminUserInfo == null) {
                return RestfulApiRespFactory.error("创建者不存在");
            }
        }

        OakAdminUser entity = new OakAdminUser();
        BeanUtils.copyProperties(req, entity);

        entity.setCreatorId(creatorId);
        if (oakAdminUserInfo != null) {
            entity.setCreatorName(oakAdminUserInfo.getUserName());
        }

        //生成密码
        String passwordSalt = uuidGenerateStrategy.uuid();
        entity.setCryptoSalt(passwordSalt);
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(passwordSalt);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        Date time = new Date();
        entity.setCreateTime(time);
        entity.setLastUpdateTime(time);
        entity.setDeleted(false);
        entity.setEnabled(true);
        if (!StringUtils.hasText(entity.getBusinessModule())) {
            entity.setBusinessModule(DEFAULT_BUSINESS_MODULE);
        }

        jpaDao.save(entity);

        //创建关联角色
        if (req.getRoleIds() != null && req.getRoleIds().length > 0) {
            List<OakRole> roleList = jpaDao.selectFrom(OakRole.class, "c")
                    .in(E_OakRole.id, req.getRoleIds())
                    .find();
            entity.setRoles(new HashSet<>(roleList));

            jpaDao.save(entity);
        }

        return RestfulApiRespFactory.ok(entity);
    }

    @Override
    public ApiResp<Void> edit(EditOakAdminUserReq req) {

         OakAdminUser entity = jpaDao.find(OakAdminUser.class, req.getId());

        if (entity == null) {
            return RestfulApiRespFactory.error("管理员用户数据不存在");
        }

        UpdateDao<OakAdminUser> updateDao = jpaDao.updateTo(OakAdminUser.class)
                .appendByQueryObj(req)
                .set(E_OakAdminUser.lastUpdateTime, new Date());
        if (StringUtils.hasText(req.getPassword())) {
            //生成密码
            String passwordSalt = uuidGenerateStrategy.uuid();
            PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(passwordSalt);
            updateDao.set(E_OakAdminUser.cryptoSalt, passwordSalt)
                    .set(E_OakAdminUser.password, passwordEncoder.encode(req.getPassword()));
        }

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新管理员用户失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteOakAdminUserReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(OakAdminUser.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(OakAdminUser.class)
                    .set(E_OakAdminUser.deleted, true)
                    .appendByQueryObj(req)
                    .update() > 0;
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public OakAdminUserInfo findById(Long id) {

        QueryOakAdminUserReq queryReq = new QueryOakAdminUserReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<OakAdminUserInfo> query(QueryOakAdminUserReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, OakAdminUser.class, OakAdminUserInfo.class, req);

    }


    @Override
    public ApiResp<Void> editAdminRole(EditOakAdminUserReq req) {

        OakAdminUser entity = jpaDao.selectFrom(OakAdminUser.class)
                .eq(E_OakAdminUser.id, req.getId())
                .join(E_OakAdminUser.roles)
                .findOne();

        if (entity == null) {
            return RestfulApiRespFactory.error("管理员用户数据不存在");
        }

        Set<OakRole> oakRoles = new HashSet<>(entity.getRoles() == null ? Collections.EMPTY_LIST : entity.getRoles());
        List<Long> oakRolesIdList = Arrays.asList(req.getRoleIds() == null ? new Long[0] : req.getRoleIds());

        if (oakRoles != null && !oakRoles.isEmpty() && oakRolesIdList != null && !oakRolesIdList.isEmpty()) {

            // 删除
            oakRoles.forEach(oakRole -> {
                boolean match = oakRolesIdList.stream().anyMatch(id -> id.equals(oakRole.getId()));
                if (!match) {
                    // 删除
                    entity.getRoles().remove(oakRole);
                }
            });

            // 添加
            List<Long> needAddList = oakRoles.isEmpty() ? oakRolesIdList :
                    oakRolesIdList.stream().filter(id -> oakRoles.stream()
                            .anyMatch(oakRole -> !id.equals(oakRole.getId())))
                            .collect(Collectors.toList());

            if (!needAddList.isEmpty()) {
                List<OakRole> OakRoles = jpaDao.selectFrom(OakRole.class)
                        .in(E_OakRole.id, needAddList.toArray(new Long[0]))
                        .find();
                entity.getRoles().addAll(new HashSet<>(OakRoles));
            }

        }

        jpaDao.save(entity);

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> changePassword(ChangePasswordReq req) {

        OakAdminUserInfo adminUserInfo = findById(req.getAdminId());

        //旧密码校验
        PasswordEncoder passwordEncoder = new Pbkdf2PasswordEncoder(adminUserInfo.getCryptoSalt());
        if (!passwordEncoder.matches(req.getOldPassword(), adminUserInfo.getPassword())) {
            return RestfulApiRespFactory.error("旧密码错误");
        }

        //新密码修改
        EditOakAdminUserReq editReq = new EditOakAdminUserReq();
        editReq.setId(req.getAdminId())
                .setPassword(req.getNewPassword());

        return edit(editReq);
    }

    @Override
    public ApiResp<Void> resetPassword(Long adminId) {
        EditOakAdminUserReq editReq = new EditOakAdminUserReq();
        editReq.setId(adminId)
                .setPassword(DEFAULT_PASSWORD);
        return edit(editReq);
    }
}
