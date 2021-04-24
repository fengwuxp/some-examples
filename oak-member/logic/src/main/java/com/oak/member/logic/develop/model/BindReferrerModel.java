package com.oak.member.logic.develop.model;

import com.oak.member.logic.enums.PromotionType;

/**
 * 绑定推荐人
 * @author wxup
 */
public interface BindReferrerModel {


    /**
     * 推荐人用户id
     * @return
     */
    Long getFormMemberId();

    /**
     * 推广类型
     * @return
     */
    PromotionType getType();

    /**
     * 被推广用户的no（来源注册时）
     * @return
     */
    Long getToMemberNo();

    /**
     * 被推广用户ID（来源注册时）
     * @return
     */
    Long getToMemberId();
}
