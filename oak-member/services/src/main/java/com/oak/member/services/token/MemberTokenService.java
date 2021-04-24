package com.oak.member.services.token;

import com.oak.member.services.token.info.MemberTokenInfo;
import com.oak.member.services.token.req.QueryMemberTokenReq;
import com.oak.member.services.token.req.SaveMemberTokenReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 * 会员登录的token信息服务
 * 2020-2-18 16:22:54
 */
public interface MemberTokenService {


    ApiResp<Void> save(SaveMemberTokenReq req);


    /**
     * 通过用户id和访问的渠道号获取token 信息
     *
     * @param memberId
     * @param channelCode
     * @return
     */
    MemberTokenInfo findMemberToken(Long memberId, String channelCode);

    Pagination<MemberTokenInfo> query(QueryMemberTokenReq req);

}
