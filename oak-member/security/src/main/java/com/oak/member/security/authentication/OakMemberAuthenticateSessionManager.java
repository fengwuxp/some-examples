package com.oak.member.security.authentication;

import com.oak.member.services.token.MemberTokenService;
import com.oak.member.services.token.info.MemberTokenInfo;
import com.oak.member.services.token.req.QueryMemberTokenReq;
import com.oak.member.services.token.req.SaveMemberTokenReq;
import com.wuxp.api.ApiResp;
import com.wuxp.api.exception.AssertThrow;
import com.wuxp.api.model.Pagination;
import com.wuxp.security.authenticate.session.AbstractAuthenticateSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 用户认证会话管理器
 *
 * @author wuxp
 */
@Slf4j
public class OakMemberAuthenticateSessionManager extends AbstractAuthenticateSessionManager<OakMemberDetails> {


    /**
     * 用户登录信息的缓存
     */
    public static final String OAK_MEMBER_CACHE_NAME = "OAK_MEMBER_CACHE";

    /**
     * 用户登录token的缓存
     */
    public static final String OAK_MEMBER_TOKEN_CACHE_NAME = "OAK_MEMBER_TOKEN_CACHE";

    /**
     * 用户被踢出登录的原因的缓存
     */
    public static final String OAK_MEMBER_KICK_OUT_REASON_CACHE_NAME = "OAK_MEMBER_KICK_OUT_REASON_CACHE";

    private MemberTokenService memberTokenService;

    public OakMemberAuthenticateSessionManager() {
        super(OakMemberDetails.class);
    }


    @Override
    public OakMemberDetails join(OakMemberDetails userDetails) {

        OakMemberDetails memberDetails = this.genAuthenticateToken(userDetails);
        // 保存用户token信息
        SaveMemberTokenReq saveMemberTokenReq = new SaveMemberTokenReq();
        Date tokenExpired = userDetails.getTokenExpired();
        String token = memberDetails.getToken();
        saveMemberTokenReq
                .setMemberId(userDetails.getId())
                .setExpirationDate(tokenExpired)
                .setToken(token)
                .setLoginTime(new Date())
                .setRefreshToken(token)
                .setRefreshExpirationDate(tokenExpired)
                .setChannelCode(userDetails.getClientCode());
        ApiResp<Void> tokenResp = memberTokenService.save(saveMemberTokenReq);
        AssertThrow.assertResp(tokenResp);
        return super.join(userDetails);
    }

    @Override
    protected OakMemberDetails findUserByToken(String token) throws UsernameNotFoundException {
        // 从数据库加载
        String username = this.getUsername(token);
        UserDetails details = userDetailsService.loadUserByUsername(username);
        if (details == null) {
            throw new UsernameNotFoundException("未查询到该用户，username：" + username);
        }
        OakMemberDetails userDetails = (OakMemberDetails) details;
        QueryMemberTokenReq req = new QueryMemberTokenReq();
        req.setMemberId(userDetails.getId())
                .setChannelCode(userDetails.getClientCode())
                .setToken(token)
                .setQuerySize(1);
        MemberTokenInfo memberToken = memberTokenService.query(req).getFirst();
        if (memberToken == null) {
            return null;
        }
        Date expirationDate = memberToken.getExpirationDate();
        userDetails.setToken(token);
        userDetails.setEffectiveMilliseconds(expirationDate.getTime() - System.currentTimeMillis());
        userDetails.setTokenExpired(expirationDate);
        return userDetails;
    }

    @Override
    protected void tryRemoveDbToken(OakMemberDetails user) {

        // 保持一个用户一个渠道就一条记录，仅清空token内容
        SaveMemberTokenReq saveMemberTokenReq = new SaveMemberTokenReq();
        Date tokenExpired = user.getTokenExpired();
        saveMemberTokenReq
                .setMemberId(user.getId())
                .setExpirationDate(tokenExpired)
                .setToken("")
                .setLoginTime(new Date())
                .setRefreshToken("")
                .setRefreshExpirationDate(tokenExpired)
                .setChannelCode(user.getClientCode());
        ApiResp<Void> tokenResp = memberTokenService.save(saveMemberTokenReq);
        AssertThrow.assertResp(tokenResp);

    }

    @Override
    protected List<String> getTokensByDb(String userName, String clientCode) {

        UserDetails details = userDetailsService.loadUserByUsername(userName);
        if (details == null) {
            return Collections.emptyList();
        }
        OakMemberDetails memberDetails = (OakMemberDetails) details;
        QueryMemberTokenReq req = new QueryMemberTokenReq();
        req.setMemberId(memberDetails.getId())
                .setChannelCode(clientCode);
        Pagination<MemberTokenInfo> pagination = memberTokenService.query(req);
        List<MemberTokenInfo> records = pagination.getRecords();
        // 返回存在token 内容的有效token
        return records.stream()
                .filter(memberTokenInfo -> StringUtils.hasText(memberTokenInfo.getToken()))
                .map(MemberTokenInfo::getToken)
                .collect(Collectors.toList());

    }

    @Override
    protected String getUserCacheName() {
        return OAK_MEMBER_CACHE_NAME;
    }

    @Override
    protected String getTokenCacheName() {
        return OAK_MEMBER_TOKEN_CACHE_NAME;
    }

    @Override
    protected String getKickOutReasonCacheName() {
        return OAK_MEMBER_KICK_OUT_REASON_CACHE_NAME;
    }

    @Override
    public void tryKickOut(OakMemberDetails userDetails, String reason) {
        this.tryKickOut(userDetails.getToken(), reason);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        super.afterPropertiesSet();
        BeanFactory beanFactory = this.beanFactory;
        if (this.memberTokenService == null) {
            this.memberTokenService = beanFactory.getBean(MemberTokenService.class);
        }
        if (this.userDetailsService == null) {
            this.userDetailsService = beanFactory.getBean(OakMemberDetailsService.class);
        }


    }
}
