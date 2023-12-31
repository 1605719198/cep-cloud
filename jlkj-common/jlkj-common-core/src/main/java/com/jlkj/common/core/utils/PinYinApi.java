package com.jlkj.common.core.utils;

import net.sourceforge.pinyin4j.PinyinHelper;

/**
 * @author 265675
 * @version 1.0
 * @className PinYinApi
 * @description TODO
 * @date 2023年3月21日, 0021 10
 */
public class PinYinApi {

    /**
     * 提取每个汉字的首字母(大写)
     *
     * @param str
     * @return
     */
    public static String getPinYinHeadChar(String str) {
        if (isNull(str)) {
            return "";
        }
        String convert = "";
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            }
            else {
                convert += word;
            }
        }

        convert = string2AllTrim(convert);
        return convert.toUpperCase();
    }

    /*
     * 判断字符串是否为空
     */

    public static boolean isNull(Object strData) {
        if (strData == null || "".equals(String.valueOf(strData).trim())) {
            return true;
        }
        return false;
    }

    /**
     * 去掉字符串包含的所有空格
     *
     * @param value
     * @return
     */
    public static String string2AllTrim(String value) {
        if (isNull(value)) {
            return "";
        }
        return value.trim().replace(" ", "");
    }
}
