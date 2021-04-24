package com.oak.member.services.secure;

import com.oak.member.services.secure.info.LoginFailInfo;
import com.oak.member.services.secure.info.MemberSecureInfo;
import com.oak.member.services.secure.req.CreateMemberSecureReq;
import com.oak.member.services.secure.req.EditMemberSecureReq;
import com.oak.member.services.secure.req.QueryMemberSecureReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 * 用户安全相关服务
 *
 * @author wxup
 */
public interface MemberSecureService {


    ApiResp<Long> create(CreateMemberSecureReq req);


    ApiResp<Void> edit(EditMemberSecureReq req);

    MemberSecureInfo findById(Long id);

    Pagination<MemberSecureInfo> query(QueryMemberSecureReq req);


    /**
     * 获取用户登录失败的统计
     *
     * @param memberId
     * @return
     */
    LoginFailInfo findLoginFail(Long memberId);

    /**
     * 保存 用户登录的失败次数
     *
     * @param memberId
     * @param loginFail
     * @return
     */
    LoginFailInfo saveLoginFail(Long memberId, LoginFailInfo loginFail);

    /**
     * 增加一次用户登录失败的次数
     *
     * @param memberId
     * @param maxLoginCount
     * @return 返回剩余可登录的次数
     */
    int increaseLoginFailureCount(Long memberId, Integer maxLoginCount);

    /**
     * 重置用户登录失败的次数
     *
     * @param memberId
     */
    void resetLoginFail(Long memberId);



}
