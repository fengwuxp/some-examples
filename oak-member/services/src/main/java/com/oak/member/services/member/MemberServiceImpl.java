package com.oak.member.services.member;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.SelectDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.model.PageInfo;
import com.oak.member.entities.E_Member;
import com.oak.member.entities.E_MemberOpen;
import com.oak.member.entities.Member;
import com.oak.member.entities.MemberOpen;
import com.oak.member.logic.enums.OpenType;
import com.oak.member.services.member.info.MemberInfo;
import com.oak.member.services.member.req.*;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

import static com.oak.api.model.ApiBaseQueryReq.TABLE_ALIAS;


/**
 * 会员信息服务
 * 2020-2-6 15:32:43
 *
 * @author wxup
 */
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateMemberReq req) {

        long userNameC = jpaDao.selectFrom(Member.class)
                .eq(E_Member.userName, req.getUserName())
                .eq(E_Member.serviceProviderId, req.getServiceProviderId())
                .count();
        if (userNameC > 0) {
            return RestfulApiRespFactory.error("用户名已被使用");
        }
        Member entity = new Member();
        BeanUtils.copyProperties(req, entity);
        Date date = new Date();
        entity.setCreateTime(date)
                .setLastUpdateTime(date)
                .setIdAuth(false);
        jpaDao.create(entity);
        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditMemberReq req) {

        if (!StringUtils.isEmpty(req.getUserName())) {
            long userNameC = jpaDao.selectFrom(Member.class)
                    .eq("userName", req.getUserName())
                    .appendWhere("id != :?", req.getId())
                    .count();
            if (userNameC > 0) {
                return RestfulApiRespFactory.error("用户名已被使用");
            }
        }

        Member entity = jpaDao.find(Member.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("会员信息数据不存在");
        }
        UpdateDao<Member> updateDao = jpaDao.updateTo(Member.class)
                .set(E_Member.lastUpdateTime, new Date())
                .appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新会员信息失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteMemberReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r;
        try {
            r = jpaDao.deleteFrom(Member.class).appendByQueryObj(req).delete() > 0;
        } catch (Exception e) {
            r = jpaDao.updateTo(Member.class)
                    .set(E_Member.deleted, true)
                    .appendByQueryObj(req)
                    .update() > 0;
        }

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public MemberInfo findById(Long id) {

        QueryMemberReq queryReq = new QueryMemberReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public MemberInfo findByNo(String no) {
        QueryMemberReq queryReq = new QueryMemberReq();
        queryReq.setNo(no);
        return query(queryReq).getFirst();
    }

    @Override
    public MemberInfo findByInviteCode(String inviteCode) {
        QueryMemberReq queryReq = new QueryMemberReq();
        queryReq.setInviteCode(inviteCode);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MemberInfo> query(QueryMemberReq req) {

        SelectDao<?> selectDao = jpaDao.selectFrom(Member.class, TABLE_ALIAS).appendByQueryObj(req);

        if (req.getOpenType() != null) {
            selectDao.appendJoin(" left join MemberOpen o on o.memberId =  e.id ");
            selectDao.eq("o.openType", req.getOpenType());
            selectDao.select(TABLE_ALIAS);
        }
        if (req.getNotOpenType() != null) {
            String subSelect = "select o.memberId from " + MemberOpen.class.getSimpleName()
                    + " as o where o.memberId = e.id and o.openType = '" + req.getNotOpenType().name() + "'";
            selectDao.notExists(subSelect);
        }

        PageInfo<MemberInfo> pageInfo = PageInfo.newInstance(req);

        /*总数查询*/
        if (req.getQueryType().isQueryNum()) {
            int count = (int) selectDao.count();
            pageInfo.setTotal(count);
        }

        /*结果集查询*/
        if (req.getQueryType().isQueryResult()) {

            if (!req.isOrderBy()) {
                //设置默认排序
                req.setDefaultOrderById();
            }
            List<MemberInfo> records = selectDao.page(req.getQueryPage(), req.getQuerySize())
                    .orderBy(req.getOrderByArr(TABLE_ALIAS))
                    .find(MemberInfo.class);

            pageInfo.setRecords(records);
        }
        return pageInfo;
    }


    @Override
    public Long checkByOpenId(OpenType openType, String openId, String unionId) {
        SelectDao<MemberOpen> selectDao = jpaDao.selectFrom(MemberOpen.class)
                .select(E_MemberOpen.memberId)
                .eq(E_MemberOpen.openType, openType)
                .eq(E_MemberOpen.openId, openId);
        if (StringUtils.hasText(unionId)) {
            selectDao.eq(E_MemberOpen.unionId, unionId);
        }
        return selectDao
                .findOne();
    }

    @Override
    public Long checkByUserName(String userName) {
        return jpaDao.selectFrom(Member.class)
                .select(E_Member.id)
                .eq(E_Member.userName, userName)
                .findOne();
    }

    @Override
    public Long checkByMobilePhone(String mobilePhone) {
        return jpaDao.selectFrom(Member.class)
                .select(E_Member.id)
                .eq(E_Member.mobilePhone, mobilePhone)
                .findOne();
    }

    @Override
    public Pagination<MemberInfo> findByNameOrPhone(QueryMemberReq req) {

        PageInfo<MemberInfo> pageInfo = PageInfo.newInstance(req);

        SelectDao<Member> selectDao = jpaDao.selectFrom(Member.class, "m")
                .eq(E_Member.deleted, req.getDeleted())
                .or()
                .contains(E_Member.nickName, req.getName())
                .contains(E_Member.mobilePhone, req.getMobilePhone());

        pageInfo.setTotal(selectDao.count());

        List<MemberInfo> memberInfos = selectDao.page(req.getQueryPage(), req.getQuerySize())
                .find(MemberInfo.class);

        pageInfo.setRecords(memberInfos);
        return pageInfo;
    }

    @Override
    public ApiResp<Long> checkMember(CheckMemberReq req) {
        Long memberId = jpaDao.selectFrom(Member.class)
                .select(E_Member.id)
                .appendWhere(StringUtils.hasText(req.getUserName()), E_Member.userName + " = :?", req.getUserName())
                .appendWhere(StringUtils.hasText(req.getMobilePhone()), E_Member.mobilePhone + " = :?",req.getMobilePhone())
                .appendWhere(StringUtils.hasText(req.getEmail()), E_Member.email + " = :?", req.getEmail())
                .findOne();
        return RestfulApiRespFactory.ok(memberId);
    }

}
