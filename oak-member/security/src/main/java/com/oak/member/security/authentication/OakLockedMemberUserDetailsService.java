package com.oak.member.security.authentication;


import com.oak.member.services.member.MemberService;
import com.oak.member.services.member.info.MemberInfo;
import com.oak.member.services.member.req.EditMemberReq;
import com.oak.member.services.member.req.QueryMemberReq;
import com.wuxp.api.ApiResp;
import com.wuxp.security.authenticate.LockedUserDetailsService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;


/**
 * 锁定连续登录失败的账号
 *
 * @author wxup
 */
@Slf4j
@Setter
public class OakLockedMemberUserDetailsService implements LockedUserDetailsService, InitializingBean, BeanFactoryAware {


    private BeanFactory beanFactory;


    private MemberService memberService;

    @Override
    public void lockUserByUsername(String username, Duration limitLoginTimes) throws UsernameNotFoundException {

        QueryMemberReq queryMemberReq = new QueryMemberReq();
        queryMemberReq.setUserName(username).setQuerySize(1);
        MemberInfo memberInfo = memberService.query(queryMemberReq).getFirst();
        if (memberInfo == null) {
            throw new UsernameNotFoundException(username);
        }


        Date lockExpired = Date.from(Instant.now().plusSeconds(limitLoginTimes.getSeconds()));
        EditMemberReq req = new EditMemberReq();
        req.setId(memberInfo.getId())
                .setFrozenDate(lockExpired);
        ApiResp<Void> resp = memberService.edit(req);
        if (resp.isSuccess()) {
            if (log.isDebugEnabled()) {
                log.debug("锁定账号 {} 到 {}", username, limitLoginTimes);
            }
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.memberService == null) {
            this.memberService = beanFactory.getBean(MemberService.class);
        }
    }
}
