package com.jlkj.common.dto.resp;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@SuppressWarnings("ALL")
@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode implements IResultCode, Serializable {

    SUCCESS("0", "成功"),
    FAILED("-1", "失败"),
    EXISTSORNOT("1", "已存在"),
    ACCESS_UNAUTHORIZED("401", "访问未授权"),
    USER_ERROR("403", "forbbidon"),
    RESOURCE_NOT_FOUND("404", "请求资源不存在"),
    PARAM_ERROR("406", "用户请求参数错误"),
    SYSTEM_EXECUTION_ERROR("500", "系统执行出错")
    ;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    private String code;

    private String msg;

    @Override
    public String toString() {
        return "{" +
                "\"code\":\"" + code + '\"' +
                ", \"msg\":\"" + msg + '\"' +
                '}';
    }


    public static ResultCode getValue(String code){
        for (ResultCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return SYSTEM_EXECUTION_ERROR;
    }
}
