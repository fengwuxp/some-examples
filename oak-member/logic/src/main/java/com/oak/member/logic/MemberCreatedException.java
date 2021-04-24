package com.oak.member.logic;

/**
 * 用户创建异常
 *
 * @author wxup
 */
public class MemberCreatedException extends RuntimeException {


    public MemberCreatedException() {
    }

    public MemberCreatedException(String message) {
        super(message);
    }

    public MemberCreatedException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberCreatedException(Throwable cause) {
        super(cause);
    }

    public MemberCreatedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
