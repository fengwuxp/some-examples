package com.oak.member.logic.develop;


import com.oak.member.logic.MemberCreatedException;
import com.oak.member.logic.MemberDefinition;
import com.oak.member.logic.develop.model.MemberRegisterModel;

/**
 * 用户创建管理者
 *
 * @author wxup
 * {@link MemberCreatedProvider}
 */
public interface MemberCreatedManager {


    /**
     * 用户注册
     * 事务控制 {@link DefaultMemberCreatedManager#create}
     *
     * @param model 用于用户注册的信息
     * @return 注册成的用户信息
     * @throws MemberCreatedException 用户创建异常
     */
    MemberDefinition create(MemberRegisterModel model) throws MemberCreatedException;

}
