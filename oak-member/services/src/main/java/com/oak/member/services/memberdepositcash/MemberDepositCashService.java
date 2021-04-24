package com.oak.member.services.memberdepositcash;

import com.oak.member.services.memberdepositcash.info.MemberDepositCashInfo;
import com.oak.member.services.memberdepositcash.req.CreateMemberDepositCashReq;
import com.oak.member.services.memberdepositcash.req.DeleteMemberDepositCashReq;
import com.oak.member.services.memberdepositcash.req.EditMemberDepositCashReq;
import com.oak.member.services.memberdepositcash.req.QueryMemberDepositCashReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;

import java.util.List;


/**
 *  会员账户提现记录服务
 *  2020-6-8 23:10:52
 */
public interface MemberDepositCashService {


    ApiResp<Long> create(CreateMemberDepositCashReq req);


    ApiResp<Void> edit(EditMemberDepositCashReq req);


    ApiResp<Void> delete(DeleteMemberDepositCashReq req);


    MemberDepositCashInfo findById(Long id);

    Pagination<MemberDepositCashInfo> query(QueryMemberDepositCashReq req);

    /**
     * 查询会员每日提现申请记录
     * @param memberId 会员ID
     * @return 列表结果信息
     */
    List<MemberDepositCashInfo> queryByMemberDaily(Long memberId );

    /**
     * 管理后台，获取会员提现申请信息
     * @param req 请求对象
     * @return 提现记录结果
     */
    Pagination<MemberDepositCashInfo> managementQuery(QueryMemberDepositCashReq req);
}
