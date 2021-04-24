package com.oak.cms.services.advaccessrecord;

import com.oak.cms.services.advaccessrecord.info.AdvAccessRecordInfo;
import com.oak.cms.services.advaccessrecord.req.CreateAdvAccessRecordReq;
import com.oak.cms.services.advaccessrecord.req.DeleteAdvAccessRecordReq;
import com.oak.cms.services.advaccessrecord.req.EditAdvAccessRecordReq;
import com.oak.cms.services.advaccessrecord.req.QueryAdvAccessRecordReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 *  广告访问记录服务
 *  2020-5-28 15:15:29
 */
public interface AdvAccessRecordService {


    ApiResp<Long> create(CreateAdvAccessRecordReq req);


    ApiResp<Void> edit(EditAdvAccessRecordReq req);


    ApiResp<Void> delete(DeleteAdvAccessRecordReq req);


    AdvAccessRecordInfo findById(Long id);


    Pagination<AdvAccessRecordInfo> query(QueryAdvAccessRecordReq req);

}
