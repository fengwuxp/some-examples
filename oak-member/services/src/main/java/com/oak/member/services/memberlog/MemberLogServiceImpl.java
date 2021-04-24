package com.oak.member.services.memberlog;

import com.levin.commons.dao.JpaDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.member.entities.MemberLog;
import com.oak.member.services.memberlog.info.MemberLogInfo;
import com.oak.member.services.memberlog.req.CreateMemberLogReq;
import com.oak.member.services.memberlog.req.QueryMemberLogReq;
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
 * 会员日志服务
 * 2020-6-3 14:07:02
 */
@Service
@Slf4j
public class MemberLogServiceImpl implements MemberLogService {


    private static final String NONE_IP = "0.0.0.0";

    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateMemberLogReq req) {


        MemberLog entity = new MemberLog();
        BeanUtils.copyProperties(req, entity);
        if (!StringUtils.hasText(entity.getIp())) {
            entity.setIp(NONE_IP);
        }
        Date date = new Date();
        entity.setCreateTime(date)
                .setLastUpdateTime(date);

        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }


    @Override
    public MemberLogInfo findById(Long id) {

        QueryMemberLogReq queryReq = new QueryMemberLogReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MemberLogInfo> query(QueryMemberLogReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, MemberLog.class, MemberLogInfo.class, req);

    }
}
