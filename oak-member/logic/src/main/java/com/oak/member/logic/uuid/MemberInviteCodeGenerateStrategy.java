package com.oak.member.logic.uuid;


import com.oak.member.logic.develop.model.MemberRegisterModel;

/**
 * 用邀请码生成策略
 *
 * @author wxup
 */
public interface MemberInviteCodeGenerateStrategy {


    /**
     * 邀请吗生成
     *
     * @param memberId
     * @return
     */
    String inviteCode(MemberRegisterModel memberId);
}
