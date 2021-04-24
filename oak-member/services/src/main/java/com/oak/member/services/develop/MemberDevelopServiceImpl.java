package com.oak.member.services.develop;

import com.levin.commons.dao.JpaDao;
import com.oak.api.helper.SimpleCommonDaoHelper;
import com.oak.member.entities.Member;
import com.oak.member.entities.fission.MemberDevelop;
import com.oak.member.services.develop.info.MemberDevelopInfo;
import com.oak.member.services.develop.req.CreateMemberDevelopReq;
import com.oak.member.services.develop.req.QueryMemberDevelopReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.model.Pagination;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;


/**
 * 用户发展关系服务
 * 2020-6-3 21:55:15
 */
@Service
@Slf4j
public class MemberDevelopServiceImpl implements MemberDevelopService {

    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateMemberDevelopReq req) {

        MemberDevelop entity = new MemberDevelop();
        BeanUtils.copyProperties(req, entity);
        // 查找血缘关系
        Long developId = req.getDevelopId();
        if (developId!=null){
            MemberDevelop superMemberDevelop = jpaDao.find(MemberDevelop.class, developId);
            if (superMemberDevelop != null) {
                entity.setParentPath(superMemberDevelop.getParentPath() != null
                        ? MessageFormat.format("{0}{1}#", superMemberDevelop.getParentPath(), req.getId())
                        : MessageFormat.format("#{0}#{1}#", developId, req.getId()));
                String name = jpaDao.find(Member.class, req.getId()).getName();
                entity.setParentId(superMemberDevelop.getId()).setParentName(name);
            } else {
                entity.setParentPath(MessageFormat.format("#{0}#{1}#", developId, req.getId()));
            }
        }else {

        }

        Date date = new Date();
        entity.setCreateTime(date)
                .setLastUpdateTime(date);
        jpaDao.save(entity);

        return RestfulApiRespFactory.ok(entity.getId());
    }


    @Override
    public MemberDevelopInfo findById(Long id) {

        QueryMemberDevelopReq queryReq = new QueryMemberDevelopReq();
        queryReq.setId(id);
        return query(queryReq).getFirst();
    }

    @Override
    public Pagination<MemberDevelopInfo> query(QueryMemberDevelopReq req) {

        return SimpleCommonDaoHelper.queryObject(jpaDao, MemberDevelop.class, MemberDevelopInfo.class, req);

    }
}
