package com.oak.member.services.memberlog;

import com.oak.member.services.memberlog.info.MemberLogInfo;
import com.oak.member.services.memberlog.req.CreateMemberLogReq;
import com.oak.member.services.memberlog.req.QueryMemberLogReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;



/**
 *  会员日志服务
 *  2020-6-3 14:07:02
 */
public interface MemberLogService {


    ApiResp<Long> create(CreateMemberLogReq req);



    MemberLogInfo findById(Long id);


    Pagination<MemberLogInfo> query(QueryMemberLogReq req);

}
