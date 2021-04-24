package com.oak.member.logic.develop;

import com.oak.member.logic.MemberCreatedException;
import com.oak.member.logic.MemberDefinition;
import com.oak.member.logic.develop.model.MemberRegisterModel;

/**
 * 用户创建的提供者
 *
 * @author wxup
 * {@link MemberCreatedManager}
 */
public interface MemberCreatedProvider {


    /**
     * 用户注册
     *
     * @param model 用于用户注册的信息
     * @return 注册成的用户信息
     * @throws MemberCreatedException 用户创建异常
     */
    MemberDefinition create(MemberRegisterModel model) throws MemberCreatedException;


    /**
     * {@link #create(MemberRegisterModel)}
     *
     * @param model
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>MemberRegisterModel</code> class presented
     */
    boolean supports(Class<?> model);
}
