package com.jlkj.rabbit.mqconstant;

/**
 * @author 智能研发室 黄兵
 * @date 2022/5/5 - 8:42
 */
public class EnergyConstants {
    /** 能源代码资料队列 */
    public static  final String ENERGYCODE_QUEUE = "cep.ee.code";
    /** 固液体能源代码对应料号队列 */
    public static  final String ENERGYCODEFOMATERIAL_QUEUE = "cep.ee.material";
    /** 月计划能耗队列 */
    public static  final String ENERGYMONTHPLANCONSUME_QUEUE = "cep.ea.ea01";
    /** 月计划产量队列 */
    public static  final String ENERGYMONTHPLANOUTPUT_QUEUE = "cep.ea.ea02";
    /** 能介资料队列 */
    public static  final String ENERGYTREE_QUEUE = "cep.ea.ea03";
    /** 能源耗用产出账务信息接收 */
    public static  final String ENERGYCONSUMEOUTPUT_QUEUE = "ems.ee.ozc1";
    /** 数据来源 */
    public static  final String ENERGY_DATASOURCE = "erp";
    public static  final String ENERGYMONTHPLAN_DATASOURCE = "emp";
    public static  final String ENERGYCONSUMEOUTPUT_DATASOURCE = "mes";
    /** 能源模块操作标识：N 新增 R 修改 D 删除 */
    public static  final String ENERGY_N = "N";
    public static  final String ENERGY_R = "R";
    public static  final String ENERGY_D = "D";

    public static final String DATASOURCE = "dataSource";


    /** 月计划能耗维护 */
    public static final String ENERGY_MONTH_PLAN_ENERGY_QUEUE = "cep.ea.ea01";
    /** 月计划产量维护 */
    public static final String ENERGY_MONTH_PLAN_YIELD_QUEUE = "cep.ea.ea02";

    /** 日计划能耗维护 */
    public static final String ENERGY_DAY_PLAN_ENERGY_QUEUE = "cep.ea.ea04";
    /** 日计划产量维护 */
    public static final String ENERGY_DAY_PLAN_YIELD_QUEUE = "cep.ea.ea05";

    /** 能源耗用产出账务信息-同步队列 */
    public static final String ENERGY_CONSUMPTION_OUTPUT_ACCOUNTING_QUEUE = "cep.ee.ozc1";

    /** 能源耗用产出账务信息-数据来源 */
    public static final String ENERGY_CONSUMPTION_OUTPUT_ACCOUNTING_DATASOURCE = "mes";

    /** 能源耗用产出账务信息-message-id */
    public static final String ENERGY_CONSUMPTION_OUTPUT_ACCOUNTING_MESSAGE_ID = "OZC1";

    /** 能源耗用产出账务信息-操作标识-新增 */
    public static final String ENERGY_CONSUMPTION_OUTPUT_ACCOUNTING_ACTION = "N";
}
