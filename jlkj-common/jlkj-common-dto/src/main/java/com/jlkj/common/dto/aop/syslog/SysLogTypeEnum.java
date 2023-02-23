package com.jlkj.common.dto.aop.syslog;

/**
 * @author zyf
 * 2022-04-11 9:56
 */
public enum SysLogTypeEnum {
    /*
    日志类型
     */
    TYPE_SAVE("保存"),
    TYPE_UPDATE("修改"),
    TYPE_DELETE("删除"),
    TYPE_SEARCH("查询"),
    TYPE_LOGIN("登录"),
    TYPE_LOGOUT("登出"),
    TYPE_EXCEPTION("异常"),
    ;
    private final String type;

    SysLogTypeEnum(String type) {
        this.type = type;
    }
    public String getType() {
        return this.type;
    }
}
