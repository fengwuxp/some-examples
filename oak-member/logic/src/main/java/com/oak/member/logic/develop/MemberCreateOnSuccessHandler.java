package com.oak.member.logic.develop;


import com.oak.member.logic.MemberDefinition;
import com.oak.member.logic.develop.model.MemberRegisterModel;
import org.springframework.core.Ordered;

/**
 * 用户创建成功的处理策略接口
 *
 * @author wxup
 */
public interface MemberCreateOnSuccessHandler extends Ordered {


    /**
     * 用户创建成功的处理
     *
     * @param member 用户信息
     * @param model  用户注册信息
     */
    void onCreatedSuccess(MemberDefinition member, MemberRegisterModel model);

    /**
     * {@link #}
     *
     * @param model
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>MemberRegisterModel</code> class presented
     */
    boolean supports(Class<?> model);
}
