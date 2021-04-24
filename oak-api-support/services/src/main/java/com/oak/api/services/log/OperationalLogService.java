package com.oak.api.services.log;

import com.oak.api.services.log.info.OperationalLogInfo;
import com.oak.api.services.log.req.CreateOperationalLogReq;
import com.oak.api.services.log.req.QueryOperationalLogReq;
import com.wuxp.api.model.Pagination;

/**
 * 操作日志相关服务
 */
public interface OperationalLogService   {

    Long createOperationalLog(CreateOperationalLogReq req);



    OperationalLogInfo findOperationalLogById(Long id);


    Pagination<OperationalLogInfo> queryOperationalLog(QueryOperationalLogReq req);
}
