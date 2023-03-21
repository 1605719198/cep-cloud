package com.jlkj.rabbit.mqconstant;

/**
 * @author 智能研发室 xk
 * @date 2022/6/7 - 13:15
 */
public class SalesConstants {
    /** 合同条款主档资料同步队列 */
    public static  final String SALES_SO03_QUEUE = "cep.so.so03";
    /** 合同条款主档资料同步队列 */
    public static  final String SALES_SO04_QUEUE = "cep.so.so04";
    /** 终到地区同步队列 */
    public static  final String SALES_SHJCBX1801_QUEUE = "cep.sh.shjcbx1801";
    /** 汽运交运地同步队列 */
    public static  final String SALES_SHJCBX1201_QUEUE = "cep.sh.shjcbx1201";
    /** 铁路到局同步队列 */
    public static  final String SALES_SHJCBX1301_QUEUE = "cep.sh.shjcbx1301";
    /** 铁路到站同步队列 */
    public static  final String SALES_SHJCBX1302_QUEUE = "cep.sh.shjcbx1302";
    /** 专用线同步队列 */
    public static  final String SALES_SHJCBX1303_QUEUE = "cep.sh.shjcbx1303";
    /** 收货人信息同步队列 */
    public static  final String SALES_SBJCBX030_QUEUE = "cep.sb.sbjcBX030";
    /** 指送地点信息同步队列 */
    public static  final String SALES_SBJCBX031_QUEUE = "cep.sb.sbjcBX031";
    /** 合同资料同步队列 */
    public static  final String SALES_SO01_QUEUE = "cep.so.so01";
    /** 订单资料同步队列 */
    public static  final String SALES_SO02_QUEUE = "cep.so.so02";
    /** 数据来源 */
    public static  final String SALES_DATASOURCE = "erp";

    /** 销售模块操作标识：N 新增 R 修改 D 删除 */
    public static  final String SALES_N = "N";
    public static  final String SALES_R = "R";
    public static  final String SALES_D = "D";
}
