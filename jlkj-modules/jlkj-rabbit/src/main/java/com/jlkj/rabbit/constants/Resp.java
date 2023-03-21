package com.jlkj.rabbit.constants;

import lombok.Data;

import java.util.Map;

/**
 * @author zyf
 * @Description
 * @create 2022-02-28 15:09
 */
@Data
public class Resp {

    public static Object success() {
        return Map.of("code", 200, "msg", "success", "data", "[]");
    }

    public static Object success(Object d) {
        return Map.of("code", 200, "msg", "success", "data", d);
    }

    public static Object success(String msg, Object d) {
        return Map.of("code", 200, "msg", msg, "data", d);
    }

    public static Object badRequest() {
        return Map.of("code", 400, "msg", "Bad Request", "data", "[]");
    }

    public static Object badRequest(String msg) {
        return Map.of("code", 400, "msg", msg, "data", "[]");
    }

    public static Object unAuthorized() {
        return Map.of("code", 401, "msg", "Unauthorized", "data", "[]");
    }

    public static Object unAuthorized(String msg) {
        return Map.of("code", 401, "msg", msg, "data", "[]");
    }

    public static Object forbidden() {
        return Map.of("code", 403, "msg", "Forbidden", "data", "[]");
    }

    public static Object forbidden(String msg) {
        return Map.of("code", 403, "msg", msg, "data", "[]");
    }

    public static Object notAcceptable() {
        return Map.of("code", 406, "msg", "Not Acceptable", "data", "[]");
    }

    public static Object notAcceptable(String msg) {
        return Map.of("code", 406, "msg", msg, "data", "[]");
    }
}
