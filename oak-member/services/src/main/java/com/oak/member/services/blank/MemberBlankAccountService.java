package com.oak.member.services.blank;

import com.oak.member.services.blank.info.MemberBlankAccountInfo;
import com.oak.member.services.blank.req.CreateMemberBlankAccountReq;
import com.oak.member.services.blank.req.DeleteMemberBlankAccountReq;
import com.oak.member.services.blank.req.EditMemberBlankAccountReq;
import com.oak.member.services.blank.req.QueryMemberBlankAccountReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;



/**
 *  用户银行账号信息表服务
 *  2020-6-8 10:07:37
 */
public interface MemberBlankAccountService {


    ApiResp<Long> create(CreateMemberBlankAccountReq req);


    ApiResp<Void> edit(EditMemberBlankAccountReq req);


    ApiResp<Void> delete(DeleteMemberBlankAccountReq req);


    MemberBlankAccountInfo findById(Long id);


    Pagination<MemberBlankAccountInfo> query(QueryMemberBlankAccountReq req);

}
