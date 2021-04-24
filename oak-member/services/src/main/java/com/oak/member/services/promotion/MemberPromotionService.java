package com.oak.member.services.promotion;

import com.oak.member.services.promotion.info.MemberPromotionInfo;
import com.oak.member.services.promotion.req.CreateMemberPromotionReq;
import com.oak.member.services.promotion.req.QueryMemberPromotionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;



/**
 *  用户推广信息服务
 *  2020-6-3 21:29:08
 */
public interface MemberPromotionService {


    ApiResp<Long> create(CreateMemberPromotionReq req);


    MemberPromotionInfo findById(Long id);


    Pagination<MemberPromotionInfo> query(QueryMemberPromotionReq req);

}
