package com.oak.member.constant;

/**
 * 缓存配置的常量名称
 *
 * @author wxup
 */
public final class MemberCacheKeyConstant {


    /**
     * 用于记录登录失败次数的 缓存名称
     */
    public static final String LOGIN_FAIL_CACHE_NAME = "login.fail";

    /**
     * 是否开启用户登录失败次数权限
     */
    public static final String MEMBER_LOGIN_FAIL_CHECK = "member.login.fail.check";


    /**
     * 是否启用token 刷新
     */
    public static final String LOGIN_REFRESH_TOKEN = "login.refresh.token";

    /**
     * 用户最大可登录次数
     */
    public static final String MEMBER_MAX_LOGIN_COUNT = "login.max.error.count";

    /**
     * 用户连续登录失败 {@code MemberCacheKeyConstant#MEMBER_MAX_LOGIN_COUNT} 后，将锁定用户一段时间
     * 单位 分钟
     */
    public static final String MEMBER_MAX_LOCK_TIME = "login.max.lock.time";
}
