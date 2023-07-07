package com.jlkj.finance.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 断言工具类
 * @author 114288
 */
public final class AssertUtil {

    /**
     * 构造方法
     */
    private AssertUtil() {
        super();
    }

    /**
     * 字符串是否有值
     * @param string 字符串
     * @return 字符串是否有值
     */
    public static boolean isVal(String string) {
        if (string == null || "null".equals(string) || string.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * 字符串是否无值
     * @param string 字符串
     * @return 字符串是否无值
     */
    public static boolean isInval(String string) {
        return ! isVal(string);
    }

    /**
     * 字符串是否有值
     * @param stringBuffer 字符串
     * @return 字符串是否有值
     */
    public static boolean isVal(StringBuffer stringBuffer) {
        if (stringBuffer == null || stringBuffer.length() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 字符串是否无值
     * @param stringBuffer 字符串
     * @return 字符串是否无值
     */
    public static boolean isInval(StringBuffer stringBuffer) {
        return ! isVal(stringBuffer);
    }

    /**
     * 数字是否有值
     * @param number 数字
     * @return 数字是否有值
     */
    public static boolean isVal(Number number) {
        if (number == null) {
            return false;
        }
        return true;
    }

    /**
     * 数字是否无值
     * @param number 数字
     * @return 数字是否无值
     */
    public static boolean isInval(Number number) {
        return ! isVal(number);
    }

    /**
     * 对象是否非空
     * @param object 对象
     * @return 对象是否非空
     */
    public static boolean isVal(Object object) {
        if (object == null) {
            return false;
        }
        return true;
    }

    /**
     * 对象是否为空
     * @param object 对象
     * @return 对象是否为空
     */
    public static boolean isInval(Object object) {
        return ! isVal(object);
    }

    /**
     * 对象数组是否非空
     * @param array 对象数组
     * @return 对象数组是否非空
     */
    public static boolean isVal(Object[] array) {
        if (array == null || array.length == 0) {
            return false;
        }
        return true;
    }

    /**
     * 对象数组是否为空
     * @param array 对象数组
     * @return 对象数组是否为空
     */
    public static boolean isInval(Object[] array) {
        return ! isVal(array);
    }

    /**
     * 验证映射是否为非空
     * @param map 映射
     * @return 验证映射是否为非空
     */
    @SuppressWarnings("unchecked")
    public static boolean isVal(Map map) {
        if (map != null && ! map.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 验证映射是否为空
     * @param map 映射
     * @return 验证映射是否为空
     */
    @SuppressWarnings("unchecked")
    public static boolean isInval(Map map) {
        return ! isVal(map);
    }

    /**
     * 验证集合是否为非空集合
     * @param collection 集合
     * @return 验证集合是否为非空集合
     */
    @SuppressWarnings("unchecked")
    public static boolean isVal(Collection collection) {
        if (collection != null && ! collection.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 验证集合是否为空集合
     * @param collection 集合
     * @return 验证集合是否为空集合
     */
    @SuppressWarnings("unchecked")
    public static boolean isInval(Collection collection) {
        return ! isVal(collection);
    }

    /**
     * 验证对象是否为整数
     * @param string 字符串对象
     * @return 验证对象是否为整数
     */
    public static boolean isInteger(String string){
        boolean isInteger = false;
        if (AssertUtil.isVal(string)) {
            isInteger = string.matches("\\d*");
        }
        return isInteger;
    }

    /**
     * 验证对象是否为整数
     * @param number 数字对象
     * @return 验证对象是否为整数
     */
    public static boolean isInteger(Number number){
        boolean isInteger = false;
        if (AssertUtil.isVal(number)) {
            isInteger = number.toString().matches("\\d*");
        }
        return isInteger;
    }

    /**
     * 是否是正数
     * @param number 数字对象
     * @return 是否是正数
     */
    public static boolean isPositiveNumber(BigDecimal number) {
        if (isVal(number) && number.compareTo(BigDecimal.ZERO) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 是否是负数
     * @param number 数字对象
     * @return 是否是负数
     */
    public static boolean isNegativeNumber(BigDecimal number) {
        if (isVal(number) && number.compareTo(BigDecimal.ZERO) < 0) {
            return true;
        }
        return false;
    }

    /**
     * 验证数字是否为0
     * @param number 数字
     * @return 验证数字是否为0
     */
    public static boolean isZero(BigDecimal number) {
        boolean isZero = false;
        if (isVal(number) && BigDecimal.ZERO.compareTo(number) == 0) {
            isZero = true;
        }
        return isZero;
    }

    /**
     * 得到int值
     * @param obj
     * @return String
     */
    public static int intValue(Object obj) {
        return AssertUtil.isVal(obj) ? Integer.parseInt(obj.toString()) : 0;
    }

    /**
     * 得到String值
     * @param obj
     * @return String
     */
    public static String stringValue(Object obj) {
        return AssertUtil.isVal(obj) ? obj.toString() : "";
    }

    /**
     * 将0转化为空字符串
     * @param obj
     * @return String
     */
    public static String removeZero(BigDecimal obj) {
        return AssertUtil.isVal(obj) && obj.compareTo(BigDecimal.ZERO) == 0 ? "" : obj.toString();
    }

    /**
     * 将0转化为空字符串
     * @param num
     * @return String
     */
    public static String removeZero(int num) {
        return num == 0 ? "" : String.valueOf(num);
    }

    /**
     * 得到bigDecimal值
     * @param obj
     * @return BigDecimal
     */
    public static BigDecimal bigDecimalValue(Object obj) {
        return AssertUtil.isVal(obj) ? new BigDecimal(obj.toString()) : BigDecimal.ZERO;
    }

    /**
     * 得到bigDecimal字符串显示，不使用科学计数法
     * @param obj
     * @return BigDecimal
     */
    public static String bigDecimalToString(Object obj) {
        BigDecimal value = bigDecimalValue(obj);
        return value.stripTrailingZeros().toPlainString();
    }

    /**
     * 数量格式化位字符串
     * @param value
     * @return
     */
    public static String quantityFormatToString(BigDecimal value){
        if(AssertUtil.isInval(value)){
            value = BigDecimal.ZERO;
        }
        String quantityFormat = "##0.000";
        DecimalFormat df = new DecimalFormat(quantityFormat);
        return df.format(value.stripTrailingZeros());
    }

    /**
     * 得到objec值
     * @param obj
     * @param realObj
     * @return Object
     */
    public static Object objectValue(Object obj, Object realObj) {
        return AssertUtil.isVal(obj) ? obj : realObj;
    }

    /**
     * 数据处理,字符串转成BigDecimal
     * @param amt
     * @return BigDecimal
     */
    public static BigDecimal getDecimal(String amt) {
        if ("".equals(amt) || null == amt) {
            return BigDecimal.ZERO;
        } else {
            return new BigDecimal(amt.replaceAll(",", "").trim());
        }
    }


}
