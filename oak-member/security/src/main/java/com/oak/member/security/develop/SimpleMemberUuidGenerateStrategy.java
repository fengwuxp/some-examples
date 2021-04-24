package com.oak.member.security.develop;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import com.oak.member.logic.develop.model.MemberRegisterModel;
import com.oak.member.logic.uuid.MemberInviteCodeGenerateStrategy;
import com.oak.member.logic.uuid.MemberSnGenerateStrategy;
import com.oak.member.logic.uuid.MemberUserNameGenerateStrategy;
import com.oak.member.security.HexFormatter;
import com.oak.member.services.member.MemberService;
import com.oak.member.services.member.req.QueryMemberReq;
import com.wuxp.api.model.QueryType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;


/**
 * @author wxup
 */
@Slf4j
public class SimpleMemberUuidGenerateStrategy implements MemberSnGenerateStrategy, MemberUserNameGenerateStrategy, MemberInviteCodeGenerateStrategy {


    private static final String SN_SOURCES_TEXT = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private Snowflake snowflake = new Snowflake(2, 3);

    private HexFormatter inviteCodeFormatter = new HexFormatter();

    private HexFormatter snFormatter = new HexFormatter(SN_SOURCES_TEXT, 10, 492371);

    private HexFormatter userNameFormatter = new HexFormatter(SN_SOURCES_TEXT, 12, 192858912);


    @Autowired
    private MemberService memberService;

    private final Object inviteLock = new Object();


    @Override
    public String inviteCode(MemberRegisterModel memberId) {
        QueryMemberReq req = new QueryMemberReq();
        req.setQueryType(QueryType.QUERY_NUM);
        long num;
        synchronized (inviteLock) {
            num = memberService.query(req).getTotal();
        }
        num += RandomUtil.randomInt(8000, 100_000_00);
        return inviteCodeFormatter.print(num, Locale.CHINESE);
    }

    @Override
    public String sn(MemberRegisterModel model) {
        return snFormatter.print(snowflake.nextId(), Locale.CHINESE).toLowerCase();
    }

    @Override
    public String userName(MemberRegisterModel model) {
        return userNameFormatter.print(snowflake.nextId(), Locale.CHINESE).toLowerCase();
    }

}
