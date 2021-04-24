package com.oak.member.services.memberagent;

import com.oak.member.services.memberagent.info.MemberAgentInfo;
import com.oak.member.services.memberagent.req.CreateMemberAgentReq;
import com.wuxp.api.ApiResp;

/**
 * @author ChenXiaoBin
 * on 2020-06-06
 */
public interface MemberAgentService {

    ApiResp<Long> create(CreateMemberAgentReq req);

    MemberAgentInfo findById(Long id);

    void editMemberAgent(Long id);
}
