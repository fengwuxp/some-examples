package com.oak.member.services.memberpersonal;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.member.entities.E_MemberPersonal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.oak.member.entities.MemberPersonal;
import com.oak.member.services.memberpersonal.req.*;
import com.oak.member.services.memberpersonal.info.MemberPersonalInfo;
import java.util.Date;


/**
 *  个人实名信息服务
 *  2020-9-9 11:56:03
 */
@Service
@Slf4j
public class MemberPersonalServiceImpl implements MemberPersonalService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateMemberPersonalReq req) {


        long idNumberC = jpaDao.selectFrom(MemberPersonal.class)
                .eq("idNumber", req.getIdNumber())
                .count();
        if (idNumberC > 0) {
            return RestfulApiRespFactory.error("身份证号已被使用");
        }

        MemberPersonal entity = new MemberPersonal();
        BeanUtils.copyProperties(req, entity);

        Date date = new Date();
            entity.setCreateTime(date);
            entity.setLastUpdateTime(date);

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditMemberPersonalReq req) {


        if (!StringUtils.isEmpty(req.getIdNumber())) {
            long idNumberC = jpaDao.selectFrom(MemberPersonal.class)
                    .eq("idNumber", req.getIdNumber())
                    .appendWhere("id != :?", req.getId())
                    .count();
            if (idNumberC > 0) {
                return  RestfulApiRespFactory.error("身份证号已被使用");
            }
        }

        MemberPersonal entity = jpaDao.find(MemberPersonal.class, req.getId());
        if (entity == null) {
            return  RestfulApiRespFactory.error("个人实名信息数据不存在");
        }

        UpdateDao<MemberPersonal> updateDao = jpaDao.updateTo(MemberPersonal.class)
                .appendByQueryObj(req)
                .set(E_MemberPersonal.lastUpdateTime,new Date());

        int update = updateDao.update();
        if (update < 1) {
            return  RestfulApiRespFactory.error("更新个人实名信息失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteMemberPersonalReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return  RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(MemberPersonal.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(MemberPersonal.class)
                    .appendColumn("deleted", true)
                    .appendByQueryObj(req)
                    .update() > 0;
            //return RestfulApiRespFactory.error("无法删除个人实名信息");
        }

        if (!r) {
            return  RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public MemberPersonalInfo findById(Long id) {

        QueryMemberPersonalReq queryReq = new QueryMemberPersonalReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MemberPersonalInfo> query(QueryMemberPersonalReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao,MemberPersonal.class,MemberPersonalInfo.class,req);

    }
}
