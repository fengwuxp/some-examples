package com.oak.member.services.develop;

import com.oak.member.services.develop.info.MemberDevelopInfo;
import com.oak.member.services.develop.req.CreateMemberDevelopReq;
import com.oak.member.services.develop.req.QueryMemberDevelopReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;



/**
 *  用户发展关系服务
 *  2020-6-3 21:55:15
 */
public interface MemberDevelopService {


    ApiResp<Long> create(CreateMemberDevelopReq req);


    MemberDevelopInfo findById(Long id);


    Pagination<MemberDevelopInfo> query(QueryMemberDevelopReq req);

}
