package com.jlkj.safety.si.entity;

import lombok.Data;

import java.util.HashMap;

/**
 * @author zyf
 * @Description
 * @create 2020-07-13 15:31
 */
@Data
public class Resp {
    private int code = 500;
    private String msg = "服务器错误";
    private Object data = new HashMap<>();
}
