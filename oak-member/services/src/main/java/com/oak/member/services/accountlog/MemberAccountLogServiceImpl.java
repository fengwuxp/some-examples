package com.oak.member.services.accountlog;

import com.levin.commons.dao.JpaDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.basic.uuid.OakCommonSnType;
import com.oak.member.entities.MemberAccountLog;
import com.oak.member.services.accountlog.info.MemberAccountLogInfo;
import com.oak.member.services.accountlog.req.CreateMemberAccountLogReq;
import com.oak.member.services.accountlog.req.QueryMemberAccountLogReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import com.wuxp.basic.uuid.sn.SnGenerateStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 会员账户信息日志服务
 * 2020-6-5 16:20:13
 */
@Service
@Slf4j
public class MemberAccountLogServiceImpl implements MemberAccountLogService {


    @Autowired
    private JpaDao jpaDao;

    @Autowired
    private SnGenerateStrategy snGenerateStrategy;


    @Override
    public ApiResp<Long> create(CreateMemberAccountLogReq req) {


        MemberAccountLog entity = new MemberAccountLog();
        BeanUtils.copyProperties(req, entity);
        entity.setSn(snGenerateStrategy.nextSn(OakCommonSnType.ACCOUNT_LOG_SN));
        entity.setCreateTime(new Date());
        if (entity.getOperatorValue() == null) {
            entity.setOperatorValue(0);
        }
        if (entity.getFrozenValue() == null) {
            entity.setFrozenValue(0);
        }
        if (entity.getCurrentValue() == null) {
            entity.setCurrentValue(0);
        }
        if (entity.getCurrentFrozenValue() == null) {
            entity.setCurrentFrozenValue(0);
        }

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }


    @Override
    public MemberAccountLogInfo findById(Long id) {

        QueryMemberAccountLogReq queryReq = new QueryMemberAccountLogReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MemberAccountLogInfo> query(QueryMemberAccountLogReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, MemberAccountLog.class, MemberAccountLogInfo.class, req);

    }
}
