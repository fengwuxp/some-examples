package com.oak.member.logic.uuid;


import com.oak.member.logic.develop.model.MemberRegisterModel;

/**
 * 用户编号生成策略
 *
 * @author wxup
 */
public interface MemberSnGenerateStrategy {


    /**
     * 生成用户编号
     *
     * @param model
     * @return
     */
    String sn(MemberRegisterModel model);
}
