package com.oak.member.services.memberagent;

import com.levin.commons.dao.JpaDao;
import com.oak.member.entities.fission.E_MemberAgent;
import com.oak.member.entities.fission.MemberAgent;
import com.oak.member.services.memberagent.info.MemberAgentInfo;
import com.oak.member.services.memberagent.req.CreateMemberAgentReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.restful.RestfulApiRespFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author ChenXiaoBin
 * on 2020-06-06
 */
@Service
@Slf4j
public class MemberAgentServiceImpl implements MemberAgentService {

    @Autowired
    private JpaDao jpaDao;

    @Override
    public ApiResp<Long> create(CreateMemberAgentReq req) {
        MemberAgent entity = new MemberAgent();
        BeanUtils.copyProperties(req, entity);
        entity.setCreateTime(new Date())
                .setLastUpdateTime(new Date());
        jpaDao.create(entity);
        return RestfulApiRespFactory.ok(entity.getId());
    }


    /**
     * 查找推荐人对应的代理信息
     * @param id
     * @return
     */
    @Override
    public MemberAgentInfo findById(Long id) {
        MemberAgentInfo memberAgentInfo = jpaDao.selectFrom(MemberAgent.class)
                .eq(E_MemberAgent.id, id)
                .findOne(MemberAgentInfo.class);
        return memberAgentInfo;
    }

    /**
     * 更新推荐人信息
     * @param id
     */
    @Override
    public void editMemberAgent(Long id) {
        MemberAgentInfo memberAgentInfo = jpaDao.selectFrom(MemberAgent.class)
                .eq(E_MemberAgent.id, id)
                .findOne(MemberAgentInfo.class);
        jpaDao.updateTo(MemberAgent.class)
                .set(E_MemberAgent.agentCount, memberAgentInfo.getAgentCount() + 1)
                .eq(E_MemberAgent.id, id)
                .update();
    }
}
