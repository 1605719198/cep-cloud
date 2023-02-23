package com.jlkj.common.dto.aop.permission;

/**
 * @author zyf
 * @Description
 * @create 2022-05-30 14:29
 */
public class PermissionDenidedException extends Exception {

    public PermissionDenidedException() {
        super("Unauthorized");
    }

    public PermissionDenidedException(String msg) {
        super(msg);
    }
}
