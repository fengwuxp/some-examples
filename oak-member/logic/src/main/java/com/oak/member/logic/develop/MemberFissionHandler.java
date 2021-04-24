package com.oak.member.logic.develop;

import com.oak.member.logic.develop.model.BindReferrerModel;

/**
 * 用户裂变处理
 *
 * @author wxup
 */
public interface MemberFissionHandler {

    /**
     * 绑定推荐人
     *
     * @param model
     * @throws RuntimeException 失败则抛出异常
     */
    void bindReferrer(BindReferrerModel model) throws RuntimeException;

//    /**
//     * 检查用户发展关系，如果存在推荐关系则绑定
//     *
//     * @param memberId
//     */
//    void checkMemberDevelop(Long memberId);
}
