package com.oak.organization.services.staff;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.organization.entities.E_Staff;
import com.oak.organization.entities.Staff;
import com.oak.organization.enums.StaffAccountType;
import com.oak.organization.services.staff.info.StaffInfo;
import com.oak.organization.services.staff.req.CreateStaffReq;
import com.oak.organization.services.staff.req.DeleteStaffReq;
import com.oak.organization.services.staff.req.EditStaffReq;
import com.oak.organization.services.staff.req.QueryStaffReq;
import com.oak.rbac.entities.OakAdminUser;
import com.oak.rbac.services.user.OakAdminUserService;
import com.oak.rbac.services.user.info.OakAdminUserInfo;
import com.oak.rbac.services.user.req.CreateOakAdminUserReq;
import com.oak.rbac.services.user.req.EditOakAdminUserReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;


/**
 * 员工服务
 * 2020-1-19 14:23:00
 */
@Service
@Slf4j
public class StaffServiceImpl implements StaffService {


    @Autowired
    private JpaDao jpaDao;

    @Autowired
    private OakAdminUserService oakAdminUserService;

    @Override
    public ApiResp<Long> create(CreateStaffReq req) {


        long userNameC = jpaDao.selectFrom(Staff.class)
                .eq(E_Staff.userName, req.getUserName())
                .count();
        if (userNameC > 0) {
            return RestfulApiRespFactory.error("用户名称已被使用");
        }

        long userPhoneC = jpaDao.selectFrom(Staff.class)
                .eq(E_Staff.mobilePhone, req.getMobilePhone())
                .count();
        if (userPhoneC > 0) {
            return RestfulApiRespFactory.error("手机号已经被重复使用");
        }

        //业务员默认都是子账号类型
        if( req.getAccountType() == null ){
            req.setAccountType(StaffAccountType.SUB);
        }


        // 创建 staff 同时创建 admin管理账户
        CreateOakAdminUserReq createOakAdminUserReq = new CreateOakAdminUserReq();
        //统一使用手机作为 用户名
        createOakAdminUserReq.setUserName(req.getMobilePhone())
                .setPassword(req.getPassword())
                .setNickName(req.getUserName())
                .setMobilePhone(req.getMobilePhone())
                .setEmail(req.getEmail())
                .setRoot(false)
                .setCreatorId(req.getCreatorId())
                .setName(req.getName());

        ApiResp<OakAdminUser> adminApi =  oakAdminUserService.create(createOakAdminUserReq);


        Staff entity = new Staff();
        BeanUtils.copyProperties(req, entity);

        entity.setCreateTime(new Date());
        entity.setAdminId(adminApi.getData().getId());

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditStaffReq req) {


        if (!StringUtils.isEmpty(req.getUserName())) {
            long userNameC = jpaDao.selectFrom(Staff.class)
                    .eq(E_Staff.userName, req.getUserName())
                    .appendWhere("id != :?", req.getId())
                    .count();
            if (userNameC > 0) {
                return RestfulApiRespFactory.error("用户名称已被使用");
            }
        }

        Staff entity = jpaDao.find(Staff.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("员工数据不存在");
        }

        UpdateDao<Staff> updateDao = jpaDao.updateTo(Staff.class).appendByQueryObj(req)
                .appendColumn(E_Staff.lastUpdateTime,new Date());

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新员工失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteStaffReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(Staff.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(Staff.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除员工");
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public StaffInfo findById(Long id) {

        QueryStaffReq queryReq = new QueryStaffReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<StaffInfo> query(QueryStaffReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, Staff.class, StaffInfo.class, req);

    }

    @Override
    public ApiResp<Void> resetStaffPassword(long staffId) {

        StaffInfo  staffInfo = findById(staffId);
        //重置密码
        OakAdminUserInfo oakAdminUserInfo = oakAdminUserService.findById(staffInfo.getAdminId());

        EditOakAdminUserReq editOakAdminUserReq = new EditOakAdminUserReq();
        editOakAdminUserReq.setId(oakAdminUserInfo.getId())
                .setPassword("123456");

       return oakAdminUserService.edit(editOakAdminUserReq);

    }
}
