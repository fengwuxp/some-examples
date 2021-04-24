package com.oak.member.constant;

/**
 * @Classname DepositCashConstant
 * @Description 会员账户提现 常量
 * @Date 2020/5/14 10:17
 * @Created by 44487
 */
public class DepositCashConstant {

    /**
     * 会员单笔提现最高限制额度
     */
    public static final String MEMBER_WITHDRAW_MAX_AMOUNT = "member.withdraw.max.amount";

    /**
     * 会员单笔提现最低限制额度
     */
    public static final String MEMBER_WITHDRAW_MIN_AMOUNT = "member.withdraw.min.amount";

    /**
     * 会员每日提现限制次数
     */
    public static final String MEMBER_WITHDRAW_DAILY_LIMIT_NUMBER = "member.withdraw.daily.limit.number";
    /**
     * 会员每日提现限制总金额
     */
    public static final String MEMBER_WITHDRAW_DAILY_LIMIT_AMOUNT = "member.withdraw.daily.limit.amount";

    /**
     * 会员提现手续费用
     */
    public static final String MEMBER_WITHDRAW_HANDLINGFEE = "member.withdraw.handlingfee";

    /**
     * 本系统是否启用支持提现功能
     */
    public static final String SYSTEM_WITHDRAWAL_ENABLE = "system.withdrawal.enable";


    /**
     * 本系统是否启用支持支持自动提现
     */
    public static final String SYSTEM_AUTO_WITHDRAWAL_ENABLE = "system.auto.withdrawal.enable";


    /**
     * 默认单笔提现最高限制额度（五千元）
     */
    public static final Integer DEFAULT_MEMBER_WITHDRAW_MAX_AMOUNT = 500000;


    /**
     * 默认单笔提现最低限制额度
     */
    public static final Integer DEFAULT_MEMBER_WITHDRAW_MIN_AMOUNT = 0;

    /**
     * 默认会员提现次数
     */
    public static final Integer DEFAULT_MEMBER_WITHDRAW_LIMIT_NUMBER = 3;

    /**
     * 默认手续费
     */
    public static final Integer DEFAULT_MEMBER_WITHDRAW_HANDLINGFEE = 0;

    /**
     * 默认系统中开启提现功能
     */
    public static final Boolean DEFAULT_SYSTEM_WITHDRAWAL_ENABLE = true;

    /**
     * 默认会员每日提现限制总金额为 （1万元）
     */
    public static final Integer DEFAULT_MEMBER_WITHDRAW_DAILY_LIMIT_AMOUNT = 1000000;

    /**
     * 默认当前系统是不支持自动提现
     */
    public static final Boolean DEFAULT_SYSTEM_AUTO_WITHDRAWAL_ENABLE = false;

}
