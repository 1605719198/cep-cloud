package com.jlkj.rabbit.mqconstant;

/**
 * @author 智能研发室 黄兵
 * @date 2022/5/11 - 9:15
 */
public class QualityConstants {
    /** 样品基本资料主表队列 */
    public static  final String TSSAMPLEBASICMASTERDATA_QUEUE = "cep.ts.ts0011";
    /** 样品基本资料从表队列 */
    public static  final String TSSAMPLEBASICSLAVEDATA_QUEUE = "cep.ts.ts0012";
    /** 试验仪器资料队列 */
    public static  final String TSEQUIPBASICINFORMATION_QUEUE = "cep.ts.ts013";
    /** 试验元素代号维护队列 */
    public static  final String TSTESTELEMENTCODE_QUEUE = "cep.ts.ts010";
    /** 试验元素表格维护队列 */
    public static  final String TSELEMENTTABLEDATA_QUEUE = "cep.ts.ts011";
    /** 样品试验高低标准队列 */
    public static  final String TSELEMENTHIGHLOWSTANDARD_QUEUE = "cep.ts.ts012";
    /** 焦化试验值传输队列 */
    public static  final String TSEXPERIMENTRECORDCOAL_QUEUE = "cep.ts.tv";
    /** 数据来源 */
    public static  final String QUALITY_DATASOURCE = "erp";
    /** 质量模块操作标识：N 新增 R 修改 D 删除 */
    public static  final String QUALITY_N = "N";
    public static  final String QUALITY_R = "R";
    public static  final String QUALITY_D = "D";
    public static  final String QUALITY_S = "S";
}
