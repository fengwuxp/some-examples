package com.oak.member.logic.develop.model;


import com.oak.member.logic.enums.FissionType;

/**
 * 用户裂变模型
 *
 * @author wxup
 */
public interface MemberFissionModel {


    /**
     * 用户裂变类型
     *
     * @return
     */
    FissionType getFissionType();

    /**
     * 推荐邀请人编码
     *
     * @return
     */
    String getInviteCode();
}
