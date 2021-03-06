package com.oak.provider.member.services.open;

import com.oak.provider.member.services.open.info.MemberOpenInfo;
import com.oak.provider.member.services.open.req.*;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 *  会员绑定信息服务
 *  2020-2-8 20:22:08
 */
public interface MemberOpenService {


    ApiResp<Long> create(CreateMemberOpenReq req);


    ApiResp<Void> edit(EditMemberOpenReq req);


    ApiResp<Void> delete(DeleteMemberOpenReq req);


    MemberOpenInfo findById(Long id);


    Pagination<MemberOpenInfo> query(QueryMemberOpenReq req);

    ApiResp<Boolean> checkBindOpen(CheckBindOpenReq checkBindOpenEvt);
}
