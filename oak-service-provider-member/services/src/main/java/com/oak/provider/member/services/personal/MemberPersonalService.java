package com.oak.provider.member.services.personal;

import com.oak.provider.member.services.personal.info.MemberPersonalInfo;
import com.oak.provider.member.services.personal.req.*;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;



/**
 *  个人实名信息服务
 *  2020-3-5 17:08:13
 */
public interface MemberPersonalService {


    ApiResp<Long> create(CreateMemberPersonalReq req);


    ApiResp<Void> edit(EditMemberPersonalReq req);


    ApiResp<Void> delete(DeleteMemberPersonalReq req);


    MemberPersonalInfo findById(Long id);


    Pagination<MemberPersonalInfo> query(QueryMemberPersonalReq req);

    /**
     * 个人认证审核
     * @return
     */
    ApiResp<Integer> checkMemberPersonal(CheckMemberPersonalReq req);

}
