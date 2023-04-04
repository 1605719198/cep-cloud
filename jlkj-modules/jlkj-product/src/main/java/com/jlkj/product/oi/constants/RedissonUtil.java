package com.jlkj.product.oi.constants;

/**
 * @author zyf
 * @Description
 * @create 2022-03-28 14:02
 */
public class RedissonUtil {
    public static String getLock(String name, Object... ids) {
        StringBuilder id = new StringBuilder();
        for (Object o : ids) {
            id.append(o.toString());
        }
        return "rlock:" + name + id;
    }
}
