package com.oak.provider.member.services.personal;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.provider.member.entities.E_Member;
import com.oak.provider.member.entities.E_MemberPersonal;
import com.oak.provider.member.entities.Member;
import com.oak.provider.member.entities.MemberPersonal;
import com.oak.provider.member.services.personal.info.MemberPersonalInfo;
import com.oak.provider.member.services.personal.req.*;
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
 * 个人实名信息服务
 * 2020-3-5 17:08:13
 */
@Service
@Slf4j
public class MemberPersonalServiceImpl implements MemberPersonalService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateMemberPersonalReq req) {
        Member member = jpaDao.selectFrom(Member.class)
                .eq(E_Member.id, req.getId())
                .findOne();
        if (member == null) {
            return RestfulApiRespFactory.error("会员不存在");
        }
        //会员状态限制
        if (Boolean.TRUE.equals(member.getIdAuth())) {
            return RestfulApiRespFactory.error("会员个人实名认证已完成");
        }

        MemberPersonal memberPersonal = jpaDao.selectFrom(MemberPersonal.class)
                .eq(E_MemberPersonal.id, req.getId())
                .findOne();

        Boolean isNew = false;
        if (memberPersonal == null) {

            long idNumberC = jpaDao.selectFrom(MemberPersonal.class)
                    .eq("idNumber", req.getIdNumber())
                    .count();
            if (idNumberC > 0) {
                return RestfulApiRespFactory.error("身份证号已被使用");
            }

            memberPersonal = new MemberPersonal();
            isNew = true;
        } else if (memberPersonal.getCheckResult() != null && memberPersonal.getCheckResult()) {
            return RestfulApiRespFactory.error("实名认证已通过核验，无法修改!");
        }


        BeanUtils.copyProperties(req, memberPersonal);
        if (isNew) {
            jpaDao.create(memberPersonal);
        } else {
            memberPersonal.setCheckResult(null);
            jpaDao.save(memberPersonal);
        }
        return RestfulApiRespFactory.ok(memberPersonal.getId());
    }

    @Override
    public ApiResp<Void> edit(EditMemberPersonalReq req) {
        Member member = jpaDao.selectFrom(Member.class)
                .eq(E_Member.id, req.getId())
                .findOne();
        if (member == null) {
            return RestfulApiRespFactory.error("会员不存在");
        }
        //会员状态限制
        if (Boolean.TRUE.equals(member.getIdAuth())) {
            return RestfulApiRespFactory.error("会员个人实名认证已完成,无法修改");
        }

        if (!StringUtils.isEmpty(req.getIdNumber())) {
            long idNumberC = jpaDao.selectFrom(MemberPersonal.class)
                    .eq("idNumber", req.getIdNumber())
                    .appendWhere("id != :?", req.getId())
                    .count();
            if (idNumberC > 0) {
                return RestfulApiRespFactory.error("身份证号已被使用");
            }
        }

        MemberPersonal entity = jpaDao.find(MemberPersonal.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("个人实名信息数据不存在");
        }
        if (entity.getCheckResult() != null && entity.getCheckResult()) {
            return RestfulApiRespFactory.error("个人实名信息已通过核验，无法修改");
        }
        req.setCheckResult(null);
        UpdateDao<MemberPersonal> updateDao = jpaDao.updateTo(MemberPersonal.class).appendByQueryObj(req)
                .isNull(E_MemberPersonal.checkResult);

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新个人实名信息失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteMemberPersonalReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
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
            return RestfulApiRespFactory.error("删除失败");
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

        return SimpleCommonDaoHelper.queryObject(jpaDao, MemberPersonal.class, MemberPersonalInfo.class, req);

    }

    @Override
    public ApiResp<Integer> checkMemberPersonal(CheckMemberPersonalReq req) {
        Member member = jpaDao.find(Member.class, req.getId());
        if (member == null) {
            return RestfulApiRespFactory.error("会员不存在");
        }

        MemberPersonal memberPersonal = jpaDao.find(MemberPersonal.class, req.getId());
        if (memberPersonal == null) {
            return RestfulApiRespFactory.error("认证信息不存在");
        }

        if (memberPersonal.getCheckResult() != null && memberPersonal.getCheckResult()) {
            return RestfulApiRespFactory.error("已核验完毕,无需再次核验！");
        }

        BeanUtils.copyProperties(req, memberPersonal);
        memberPersonal.setCheckTime(new Date());
        jpaDao.save(memberPersonal);

        if (Boolean.TRUE.equals(req.getCheckResult())) {
            //会员信息更新
            member.setIdAuth(true);
            member.setUserName(memberPersonal.getName());
            jpaDao.save(member);
        }

        return RestfulApiRespFactory.ok(memberPersonal.getCheckResult() != null ? (memberPersonal.getCheckResult() ? 1 : 0) : null);
    }
}
