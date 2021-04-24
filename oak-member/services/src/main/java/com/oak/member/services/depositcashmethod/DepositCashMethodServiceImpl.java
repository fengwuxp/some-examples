package com.oak.member.services.depositcashmethod;

import com.levin.commons.dao.JpaDao;
import com.levin.commons.dao.UpdateDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.member.entities.DepositCashMethod;
import com.oak.member.entities.E_DepositCashMethod;
import com.oak.member.services.depositcashmethod.info.DepositCashMethodInfo;
import com.oak.member.services.depositcashmethod.req.CreateDepositCashMethodReq;
import com.oak.member.services.depositcashmethod.req.DeleteDepositCashMethodReq;
import com.oak.member.services.depositcashmethod.req.EditDepositCashMethodReq;
import com.oak.member.services.depositcashmethod.req.QueryDepositCashMethodReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 提现方式服务
 * 2020-6-8 16:51:49
 */
@Service
@Slf4j
public class DepositCashMethodServiceImpl implements DepositCashMethodService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateDepositCashMethodReq req) {


        long codeC = jpaDao.selectFrom(DepositCashMethod.class)
                .eq(E_DepositCashMethod.code, req.getCode())
                .count();
        if (codeC > 0) {
            return RestfulApiRespFactory.error("提现方式代码已被使用");
        }

        DepositCashMethod entity = new DepositCashMethod();
        BeanUtils.copyProperties(req, entity);
        if (entity.getAutoAmount() == null) {
            entity.setAutoAmount(0);
        }

        Date date = new Date();
        entity.setCreateTime(date)
                .setLastUpdateTime(date)
                .setEnabled(true);
        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public ApiResp<Void> edit(EditDepositCashMethodReq req) {


        if (!StringUtils.isEmpty(req.getCode())) {
            long codeC = jpaDao.selectFrom(DepositCashMethod.class)
                    .eq(E_DepositCashMethod.code, req.getCode())
                    .eq(E_DepositCashMethod.id, req.getId())
                    .count();
            if (codeC > 0) {
                return RestfulApiRespFactory.error("提现方式代码已被使用");
            }
        }

        DepositCashMethod entity = jpaDao.find(DepositCashMethod.class, req.getId());
        if (entity == null) {
            return RestfulApiRespFactory.error("提现方式数据不存在");
        }

        UpdateDao<DepositCashMethod> updateDao = jpaDao.updateTo(DepositCashMethod.class).appendByQueryObj(req);

        int update = updateDao.update();
        if (update < 1) {
            return RestfulApiRespFactory.error("更新提现方式失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public ApiResp<Void> delete(DeleteDepositCashMethodReq req) {


        if (req.getId() == null
                && (req.getIds() == null || req.getIds().length == 0)) {
            return RestfulApiRespFactory.error("删除参数不能为空");
        }

        boolean r = jpaDao.deleteFrom(DepositCashMethod.class).appendByQueryObj(req).delete() > 0;

        if (!r) {
            return RestfulApiRespFactory.error("删除失败");
        }

        return RestfulApiRespFactory.ok();
    }

    @Override
    public DepositCashMethodInfo findById(Long id) {

        QueryDepositCashMethodReq queryReq = new QueryDepositCashMethodReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<DepositCashMethodInfo> query(QueryDepositCashMethodReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, DepositCashMethod.class, DepositCashMethodInfo.class, req);

    }

    @Override
    public DepositCashMethodInfo findByCode(String code) {
        QueryDepositCashMethodReq queryDepositCashMethodReq = new QueryDepositCashMethodReq();
        queryDepositCashMethodReq.setCode(code);
        return query(queryDepositCashMethodReq).getFirst();
    }
}
