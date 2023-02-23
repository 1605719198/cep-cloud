package com.jlkj.common.dto.tools.user;

/**
 * @author zyf
 * @Description
 * @create 2022-07-04 16:48
 */
public class BadUserInfoException extends Exception{

    public BadUserInfoException() {
        super("Failed to get user information.");
    }

    public BadUserInfoException(String msg) {
        super(msg);
    }
}
