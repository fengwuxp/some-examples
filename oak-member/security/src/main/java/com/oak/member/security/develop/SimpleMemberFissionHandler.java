package com.oak.member.security.develop;

import com.oak.member.logic.develop.MemberFissionHandler;
import com.oak.member.logic.develop.model.BindReferrerModel;
import com.oak.member.services.promotion.MemberPromotionService;
import com.oak.member.services.promotion.req.CreateMemberPromotionReq;
import com.oak.member.services.promotion.req.QueryMemberPromotionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.QueryType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wxup
 */
@Slf4j
public class SimpleMemberFissionHandler implements MemberFissionHandler {

    @Autowired
    private MemberPromotionService memberPromotionService;

    @Override
    public void bindReferrer(BindReferrerModel model) throws RuntimeException {

        QueryMemberPromotionReq promotionReq = new QueryMemberPromotionReq();
        promotionReq.setType(model.getType())
                .setToMemberId(model.getToMemberId())
                .setQueryType(QueryType.QUERY_NUM);
        long total = memberPromotionService.query(promotionReq).getTotal();
        if (total > 0) {
            return;
        }
        CreateMemberPromotionReq req = new CreateMemberPromotionReq();
        req.setType(model.getType())
                .setToMemberId(model.getToMemberId())
                .setFromMemberId(model.getFormMemberId())
                .setToMemberNo(req.getToMemberNo());
        ApiResp<Long> resp = memberPromotionService.create(req);
        if (!resp.isSuccess()) {
            throw new RuntimeException("绑定用户推荐关系失败");
        }
    }


    /**
     * 检查用户发展关系，如果存在推荐关系则绑定
     *
     * @param memberId
     */
   protected void checkMemberDevelop(Long memberId){

   }
}
