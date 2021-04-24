package com.oak.member.services.token;

import com.levin.commons.dao.JpaDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.member.entities.E_MemberToken;
import com.oak.member.entities.MemberToken;
import com.oak.member.services.token.info.MemberTokenInfo;
import com.oak.member.services.token.req.QueryMemberTokenReq;
import com.oak.member.services.token.req.SaveMemberTokenReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 会员登录的token信息服务
 * 2020-2-18 16:22:54
 */
@Service
@Slf4j
public class MemberTokenServiceImpl implements MemberTokenService {


    @Autowired
    private JpaDao jpaDao;


    @Override
    public ApiResp<Void> save(SaveMemberTokenReq req) {


        MemberToken memberToken = jpaDao.selectFrom(MemberToken.class)
                .eq(E_MemberToken.memberId, req.getMemberId())
                .eq(E_MemberToken.channelCode, req.getChannelCode())
                .findOne();

        if (memberToken == null) {
            // 创建
            MemberToken entity = new MemberToken();
            BeanUtils.copyProperties(req, entity);
            jpaDao.create(entity);
        } else {
            int count = jpaDao.updateTo(MemberToken.class).appendByQueryObj(req)
                    .eq(E_MemberToken.id, memberToken.getId())
                    .update();
            AssertThrow.assertEquals("用户token信息更新失败", count, 1);

        }

        return RestfulApiRespFactory.ok();
    }


    @Override
    public MemberTokenInfo findMemberToken(Long memberId, String channelCode) {

        return jpaDao.selectFrom(MemberToken.class)
                .eq(E_MemberToken.memberId, memberId)
                .eq(E_MemberToken.channelCode, channelCode)
                .findOne(MemberTokenInfo.class);
    }

    @Override
    public Pagination<MemberTokenInfo> query(QueryMemberTokenReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, MemberToken.class, MemberTokenInfo.class, req);

    }
}
