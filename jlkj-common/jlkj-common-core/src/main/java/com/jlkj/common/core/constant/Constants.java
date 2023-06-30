package com.jlkj.common.core.constant;

import java.math.BigDecimal;

/**
 * 通用常量信息
 *
 * @author jlkj
 */
public class Constants {
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * www主域
     */
    public static final String WWW = "www.";

    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi:";

    /**
     * LDAP 远程方法调用
     */
    public static final String LOOKUP_LDAP = "ldap:";

    /**
     * LDAPS 远程方法调用
     */
    public static final String LOOKUP_LDAPS = "ldaps:";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 500;

    /**
     * 登录成功状态
     */
    public static final String LOGIN_SUCCESS_STATUS = "0";

    /**
     * 登录失败状态
     */
    public static final String LOGIN_FAIL_STATUS = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 注册
     */
    public static final String REGISTER = "Register";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN = "orderByColumn";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC = "isAsc";

    /**
     * 验证码有效期（分钟）
     */
    public static final long CAPTCHA_EXPIRATION = 2;

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /**
     * 定时任务白名单配置（仅允许访问的包名，如其他需要可以自行添加）
     */
    public static final String[] JOB_WHITELIST_STR = {"com.jlkj"};

    /**
     * 定时任务违规的字符
     */
    public static final String[] JOB_ERROR_STR = {"java.net.URL", "javax.naming.InitialContext", "org.yaml.snakeyaml",
            "org.springframework", "org.apache", "com.jlkj.common.core.utils.file"};
    /**
     * 常量字符串 0
     *
     * @author 111191
     * @Date 上午 09:28:43 2023年6月17日, 0017
     * @param null
     */
    public static final String STR_ZERO = "0";
    /**
     * 常量字符串 1
     *
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     * @param null
     */
    public static final String STR_ONE = "1";
    /**
     * 常量字符串 2
     *
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     * @param null
     */
    public static final String STR_TWO = "2";
    /**
     * 常量字符串 3
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_THREE = "3";
    /**
     * 常量字符串 4
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_FOUR = "4";
    /**
     * 常量字符串 5
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_FIVE = "5";
    /**
     * 常量字符串 6
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_SIX = "6";
    /**
     * 常量字符串 7
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_SEVEN = "7";
    /**
     * 常量字符串 8
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_EIGHT = "8";
    /**
     * 常量字符串 9
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_NINE = "9";
    /**
     * 常量字符串 10
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_TEN = "10";
    /**
     * 常量字符串 11
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_ELEVEN = "11";
    /**
     * 常量字符串 12
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_TWELVE = "12";
    /**
     * 常量字符串 13
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_THIRTEEN = "13";
    /**
     * 常量字符串 14
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_FOURTEEN = "14";
    /**
     * 常量字符串 15
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_FIFTEEN = "15";
    /**
     * 常量字符串 16
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_SIXTEEN = "16";
    /**
     * 常量字符串 17
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_SEVENTEEN = "17";
    /**
     * 常量字符串 18
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_EIGHTEEN = "18";
    /**
     * 常量字符串 19
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_NINETEEN = "19";
    /**
     * 常量字符串 20
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_TWENTY = "20";
    /**
     * 常量字符串 30
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_THIRTY = "30";
    /**
     * 常量字符串 40
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_FORTY = "40";
    /**
     * 常量字符串 50
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_FIFTY = "50";
    /**
     * 常量字符串 60
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_SIXTY = "60";
    /**
     * 常量字符串 70
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_SEVENTY = "70";
    /**
     * 常量字符串 80
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_EIGHTY = "80";
    /**
     * 常量字符串 90
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_NINETY = "90";
    /**
     * 常量字符串 100
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     */
    public static final String STR_HUNDRED = "100";
    /**
     * 常量字符串 01
     *
     * @author 111191
     * @Date 上午 09:28:43 2023年6月17日, 0017
     * @param null
     */
    public static final String STR_ZERO_ONE = "01";
    /**
     * 常量字符串 Y
     *
     * @author 111191
     * @Date 上午 09:27:51 2023年6月29日, 0029
     * @param null
     */
    public static final String STR_Y = "Y";
    /**
     * 常量字符串 N
     *
     * @author 111191
     * @Date 上午 09:27:55 2023年6月29日, 0029
     * @param null
     */
    public static final String STR_N = "N";
// ************************************************   以下为 Integer 类型常量      ***********************************************
    /**
     * 数字 1
     *
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     * @param null
     */
    public static final Integer INT_ONE = 1;

    /**
     * 数字 0
     *
     * @author 265675
     * @Date 下午 15:28:43 2023年6月27日
     * @param null
     */
    public static final Integer INT_ZERO = 0;
    /**
     * 数字 100
     * @author 111191
     * @Date 上午 09:28:45 2023年6月29日, 0029
     * @param null
     */
    public static final Integer INT_HUNDRED = 100;

// ************************************************   以下为 BigDecimal 类型常量      ***********************************************
    /**
     * 数字 100
     *
     * @author 265675
     * @Date 上午 10:19:43 2023年6月28日
     * @param null
     */
    public static final BigDecimal BIG_DECIMAL_HUNDRED = new BigDecimal("100");

// ************************************************   以下为 定义其它 类型常量      ***********************************************
}
