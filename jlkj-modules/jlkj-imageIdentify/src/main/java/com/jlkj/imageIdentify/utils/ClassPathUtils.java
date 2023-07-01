package com.jlkj.imageIdentify.utils;
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.net.URLDecoder;

public class ClassPathUtils {
    public ClassPathUtils() {
    }

    public static String getPath() {
        String path = null;

        try {
            path = ClassPathUtils.class.getClassLoader().getResource("").getPath().toString();
            String systemName = System.getProperty("os.name");
            if (systemName.startsWith("Windows")) {
                path = URLDecoder.decode(path.substring(1), "utf-8");
            } else {
                path = URLDecoder.decode(path, "utf-8");
            }
        } catch (Exception var2) {
            var2.printStackTrace();
        }

        return path;
    }
}
