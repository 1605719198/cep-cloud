package com.jlkj.common.dto.jedis;

/**
 * @author zyf
 * @Description
 * @create 2022-03-29 11:00
 */
public class JedisUtil {
    public static final String TOKEN_KEY_PREFIX = "web:";
    public static final String APP_TOKEN_KEY_PREFIX = "app:";

    public static String generateUserTokenKey(String id) {
        return TOKEN_KEY_PREFIX + id;
    }

    public static String generateAppUserTokenKey(String id) {
        return APP_TOKEN_KEY_PREFIX + id;
    }
}
