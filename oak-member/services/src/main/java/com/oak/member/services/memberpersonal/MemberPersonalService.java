package com.oak.member.services.memberpersonal;

import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import io.swagger.v3.oas.annotations.media.Schema;
import com.oak.member.services.memberpersonal.req.*;
import com.oak.member.services.memberpersonal.info.MemberPersonalInfo;



/**
 *  个人实名信息服务
 *  2020-9-9 11:56:03
 */
public interface MemberPersonalService {


    ApiResp<Long> create(CreateMemberPersonalReq req);


    ApiResp<Void> edit(EditMemberPersonalReq req);


    ApiResp<Void> delete(DeleteMemberPersonalReq req);


    MemberPersonalInfo findById(Long id);


    Pagination<MemberPersonalInfo> query(QueryMemberPersonalReq req);

}
