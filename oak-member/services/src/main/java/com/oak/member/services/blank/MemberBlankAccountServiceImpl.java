package com.oak.member.services.blank;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.member.entities.E_MemberBlankAccount;
import com.oak.member.entities.MemberBlankAccount;
import com.oak.member.services.blank.info.MemberBlankAccountInfo;
import com.oak.member.services.blank.req.CreateMemberBlankAccountReq;
import com.oak.member.services.blank.req.DeleteMemberBlankAccountReq;
import com.oak.member.services.blank.req.EditMemberBlankAccountReq;
import com.oak.member.services.blank.req.QueryMemberBlankAccountReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 用户银行账号信息表服务
 * 2020-6-8 10:07:37
 */
@Service
@Slf4j
public class MemberBlankAccountServiceImpl implements MemberBlankAccountService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateMemberBlankAccountReq req) {


        MemberBlankAccount entity = new MemberBlankAccount();
        BeanUtils.copyProperties(req, entity);
        Date date = new Date();
        entity.setCreateTime(date)
                .setLastUpdateTime(date)
                .setDeleted(false);

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditMemberBlankAccountReq req) {


        MemberBlankAccount entity = jpaDao.find(MemberBlankAccount.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("用户银行账号信息表数据不存在");
        }

        UpdateDao<MemberBlankAccount> updateDao = jpaDao.updateTo(MemberBlankAccount.class)
                .set(E_MemberBlankAccount.lastUpdateTime, new Date())
                .appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新用户银行账号信息表失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteMemberBlankAccountReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }
        boolean r = jpaDao.deleteFrom(MemberBlankAccount.class).appendByQueryObj(req).delete() > 0;

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public MemberBlankAccountInfo findById(Long id) {

        QueryMemberBlankAccountReq queryReq = new QueryMemberBlankAccountReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MemberBlankAccountInfo> query(QueryMemberBlankAccountReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, MemberBlankAccount.class, MemberBlankAccountInfo.class, req);

    }
}
