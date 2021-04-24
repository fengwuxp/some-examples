package com.oak.member.logic.uuid;

import com.oak.member.logic.develop.model.MemberRegisterModel;

/**
 * 默认用户生成策略
 *
 * @author wxup
 */
public interface MemberUserNameGenerateStrategy {

    /**
     * 生成用户编号
     *
     * @param model
     * @return
     */
    String userName(MemberRegisterModel model);
}
