package com.oak.member.services.member;

import com.oak.member.logic.MemberChecker;
import com.oak.member.services.member.info.MemberInfo;
import com.oak.member.services.member.req.*;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 * 会员信息服务
 * 2020-2-6 15:32:43
 *
 * @author wxup
 */
public interface MemberService extends MemberChecker {

    ApiResp<Long> create(CreateMemberReq req);

    ApiResp<Void> edit(EditMemberReq req);

    ApiResp<Void> delete(DeleteMemberReq req);

    MemberInfo findById(Long id);

    MemberInfo findByNo(String no);

    MemberInfo findByInviteCode(String inviteCode);

    Pagination<MemberInfo> query(QueryMemberReq req);

    Pagination<MemberInfo> findByNameOrPhone(QueryMemberReq req);

    ApiResp<Long> checkMember(CheckMemberReq req);

}
