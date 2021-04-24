package com.oak.api.services.log;

import com.levin.commons.dao.JpaDao;
import com.oak.api.entities.OperationalLog;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.api.services.log.info.OperationalLogInfo;
import com.oak.api.services.log.req.CreateOperationalLogReq;
import com.oak.api.services.log.req.QueryOperationalLogReq;
import com.wuxp.api.model.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class OperationalLogServiceImpl implements OperationalLogService {

    @Autowired
    private JpaDao jpaDao;

    @Override
    public Long createOperationalLog(CreateOperationalLogReq req) {
        OperationalLog entity = new OperationalLog();
        BeanUtils.copyProperties(req, entity);
        entity.setCreateTime(new Date());
        jpaDao.create(entity);

        return entity.getId();
    }


    @Override
    public OperationalLogInfo findOperationalLogById(Long id) {
        return this.queryOperationalLog(new QueryOperationalLogReq(id)).getFirst();
    }

    @Override
    public Pagination<OperationalLogInfo> queryOperationalLog(QueryOperationalLogReq req) {
        return SimpleCommonDaoHelper.queryObject(jpaDao, OperationalLog.class, OperationalLogInfo.class, req);
    }


}
