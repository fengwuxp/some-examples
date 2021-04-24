package com.oak.rbac.log;

import com.oak.api.services.log.OperationalLogService;
import com.oak.api.services.log.req.CreateOperationalLogReq;
import com.oak.rbac.security.OakUser;
import com.wuxp.api.log.ApiLogModel;
import com.wuxp.api.log.ApiLogRecorder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.security.core.Authentication;

/**
 * Date： 2020/8/7 16:05
 *
 * @author ZHM
 */
public class RbacApiLogRecorder implements ApiLogRecorder {

    @Autowired
    private OperationalLogService operationalLogService;

    @Override
    public void log(ApiLogModel apiLogModel, EvaluationContext evaluationContext, Throwable throwable) {

        CreateOperationalLogReq req = new CreateOperationalLogReq();
        req.setIp(apiLogModel.getIp());
        req.setAction(apiLogModel.getAction());
        req.setType(apiLogModel.getType());
        req.setContent(apiLogModel.getContent());
        req.setUrl(apiLogModel.getUri());
        req.setTargetResourceId(apiLogModel.getTargetResourceId());

        if(StringUtils.isBlank(req.getIp())){
            req.setIp("127.0.0.1");
        }
        if( StringUtils.isBlank(req.getUrl()) ){
            req.setUrl("暂无URL信息");
        }
        if( StringUtils.isBlank(req.getContent()) ){
            req.setContent(req.getType()+req.getAction());
        }

        Object authentication = apiLogModel.getAuthentication();
        if (authentication instanceof Authentication) {
            Authentication admin = (Authentication) authentication;
            OakUser principal = (OakUser) admin.getPrincipal();
            req.setOperationalName(admin.getName());
            req.setOperationalId(principal.getId());
        }

        operationalLogService.createOperationalLog(req);

    }
}
