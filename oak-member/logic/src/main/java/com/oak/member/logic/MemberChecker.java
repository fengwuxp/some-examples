package com.oak.member.logic;

import com.oak.member.logic.enums.OpenType;

/**
 * 用户检查器
 *
 * @author wxup
 */
public interface MemberChecker {


    /**
     * 通过第三方平台检查户并返回id
     *
     * @param openType
     * @param openId
     * @param unionId
     * @return
     */
    Long checkByOpenId(OpenType openType, String openId, String unionId);

    /**
     * 通过用户名检查并返回id
     *
     * @param userName
     * @return
     */
    Long checkByUserName(String userName);

    /**
     * 通过手机号码检查并返回id
     *
     * @param mobilePhone
     * @return
     */
    Long checkByMobilePhone(String mobilePhone);
}
