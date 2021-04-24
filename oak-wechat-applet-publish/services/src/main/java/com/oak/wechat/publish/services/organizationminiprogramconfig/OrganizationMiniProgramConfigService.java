package com.oak.wechat.publish.services.organizationminiprogramconfig;

import com.oak.wechat.publish.services.organizationminiprogramconfig.info.OrganizationMiniProgramConfigInfo;
import com.oak.wechat.publish.services.organizationminiprogramconfig.req.CreateOrganizationMiniProgramConfigReq;
import com.oak.wechat.publish.services.organizationminiprogramconfig.req.DeleteOrganizationMiniProgramConfigReq;
import com.oak.wechat.publish.services.organizationminiprogramconfig.req.EditOrganizationMiniProgramConfigReq;
import com.oak.wechat.publish.services.organizationminiprogramconfig.req.QueryOrganizationMiniProgramConfigReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 *  组织小程序发布配置服务
 *  2020-3-2 17:28:21
 */
public interface OrganizationMiniProgramConfigService {


    ApiResp<Long> create(CreateOrganizationMiniProgramConfigReq req);


    ApiResp<Void> edit(EditOrganizationMiniProgramConfigReq req);


    ApiResp<Void> delete(DeleteOrganizationMiniProgramConfigReq req);


    OrganizationMiniProgramConfigInfo findById(Long id);


    Pagination<OrganizationMiniProgramConfigInfo> query(QueryOrganizationMiniProgramConfigReq req);

}
