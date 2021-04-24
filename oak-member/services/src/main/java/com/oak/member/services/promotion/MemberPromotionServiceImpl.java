package com.oak.member.services.promotion;

import com.levin.commons.dao.JpaDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.member.entities.fission.MemberPromotion;
import com.oak.member.services.promotion.info.MemberPromotionInfo;
import com.oak.member.services.promotion.req.CreateMemberPromotionReq;
import com.oak.member.services.promotion.req.QueryMemberPromotionReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * 用户推广信息服务
 * 2020-6-3 21:29:08
 */
@Service
@Slf4j
public class MemberPromotionServiceImpl implements MemberPromotionService {


    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateMemberPromotionReq req) {

        MemberPromotion entity = new MemberPromotion();
        BeanUtils.copyProperties(req, entity);
        Date date = new Date();
        entity.setCreateTime(date)
                .setLastUpdateTime(date);
        jpaDao.create(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }

    @Override
    public MemberPromotionInfo findById(Long id) {

        QueryMemberPromotionReq queryReq = new QueryMemberPromotionReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MemberPromotionInfo> query(QueryMemberPromotionReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, MemberPromotion.class, MemberPromotionInfo.class, req);

    }
}
