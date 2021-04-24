package com.oak.member.management.account;


import com.oak.member.management.account.info.TodayMemberWithdraw;
import com.oak.member.management.account.req.ApplyWithdrawalReq;
import com.oak.member.management.account.req.CheckWithdrawReq;
import com.wuxp.api.ApiResp;

/**
 * 用户账号管理服务
 *
 * @author wxup
 */
public interface MemberAccountManagementService {

    /**
     * 会员发起提现申请
     *
     * @return
     */
    ApiResp<Void> applyWithdrawal(ApplyWithdrawalReq req);

    /**
     * 校验提现流程
     * 发起提现前置流程：校验当前用户能否发起提现申请
     *
     * 例如：系统当前系统不支持申请校验，则提示用户无法发起提前申请
     *
     * @return
     */
    ApiResp<Void> checkWithdrawRequest(CheckWithdrawReq req);


    /**
     * 获取今日会员提现次数和提现金额
     *
     * @param memberId
     * @return
     */
    TodayMemberWithdraw getTodayMemberWithdraw(Long memberId);

}
