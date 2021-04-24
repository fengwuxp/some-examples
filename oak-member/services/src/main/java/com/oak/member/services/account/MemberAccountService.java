package com.oak.member.services.account;

import com.oak.member.services.account.info.MemberAccountInfo;
import com.oak.member.services.account.req.ChangeAccountReq;
import com.oak.member.services.account.req.CreateMemberAccountReq;
import com.oak.member.services.account.req.QueryMemberAccountReq;
import com.oak.member.services.account.req.UpdateAccountStatusReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;


/**
 * 会员账户信息服务
 * 2020-6-8 10:22:51
 */
public interface MemberAccountService {


    ApiResp<Long> create(CreateMemberAccountReq req);


    ApiResp<Void> updateStatus(UpdateAccountStatusReq req);


    /**
     * 调整账号的余额、积分、代金券 增加或减少
     *
     * @return
     */
    ApiResp<Void> changeAccount(ChangeAccountReq req);



    MemberAccountInfo findById(Long id);


    Pagination<MemberAccountInfo> query(QueryMemberAccountReq req);

    /**
     * 将积分转换金额
     * 积分和1元钱的兑换比例
     *
     * @param points 积分数量
     * @return 返回积分可用抵扣的金额，单位：分
     */
    Integer pointsToFee(int points);

    /**
     * 将金额转换为积分数量
     *
     * @param amount 金额  单位：分
     * @return 返回转换的积分数量
     */
    Integer feeToPoints(int amount);
}
