package com.oak.member.security.authentication;

import com.oak.member.services.member.MemberService;
import com.oak.member.services.member.info.MemberInfo;
import com.oak.member.services.member.req.QueryMemberReq;
import com.oak.member.services.secure.MemberSecureService;
import com.oak.member.services.secure.info.MemberSecureInfo;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

/**
 * @author wxup
 */
@Slf4j
@Setter
public class OakMemberDetailsService implements UserDetailsService, InitializingBean, BeanFactoryAware {


    private BeanFactory beanFactory;

    private MemberService memberService;

    private MemberSecureService memberSecureService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (!StringUtils.hasText(username)) {
            throw new UsernameNotFoundException("username is empty");
        }

        QueryMemberReq req = new QueryMemberReq();
        req.setUserName(username)
                .setDeleted(false)
                .setQuerySize(1);
        MemberInfo memberInfo = memberService.query(req).getFirst();
        if (memberInfo == null) {
            throw new UsernameNotFoundException(username);
        }

        boolean isLocked = memberInfo.getFrozenDate() != null && memberInfo.getFrozenDate().getTime() > System.currentTimeMillis();
        if (isLocked) {
            throw new LockedException("用户被锁定");
        }
        MemberSecureInfo memberSecureInfo = memberSecureService.findById(memberInfo.getId());
        return converterMemberDetails(memberInfo, memberSecureInfo);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.memberService == null) {
            this.memberService = this.beanFactory.getBean(MemberService.class);
        }
        if (this.memberSecureService == null) {
            this.memberSecureService = this.beanFactory.getBean(MemberSecureService.class);
        }
    }

    private static OakMemberDetails converterMemberDetails(MemberInfo memberInfo, MemberSecureInfo memberSecureInfo) {


        OakMemberDetails memberDetails = new OakMemberDetails(memberInfo.getUserName(),
                memberSecureInfo.getLoginPassword(),
                memberInfo.getEnabled(),
                true,
                true,
                true);
        memberDetails.setId(memberInfo.getId())
                .setCryptoSalt(memberSecureInfo.getLoginCryptoSalt());
        return memberDetails;
    }
}
