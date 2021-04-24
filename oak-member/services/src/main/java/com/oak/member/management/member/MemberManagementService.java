package com.oak.member.management.member;

import com.oak.member.management.member.req.ChangeLoginPasswordReq;
import com.oak.member.management.member.req.ForgetPasswordReq;
import com.wuxp.api.ApiResp;

/**
 * 用户相关业务服务
 *
 * @author wuxp
 */
public interface MemberManagementService {

    /**
     * 忘记密码
     * @param req
     * @return
     */
    ApiResp<Void> forgetPassword(ForgetPasswordReq req);


    /**
     * 修改登录密码
     * @param req 请求对象
     * @return 执行结果
     */
    ApiResp<Void> changeLoginPassword(ChangeLoginPasswordReq req);

}
