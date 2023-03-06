package com.jlkj.common.core.constant;

/**
 * @author zyf
 * @Description
 * @create 2022-04-11 10:44
 */
public class RabbitConstant {

    public static final String SYSLOG_EXCHANGE = "syslog_exchange";
    public static final String SYSLOG_QUEUE = "syslog_queue";
    public static final String SYSLOG_ROUTE_KEY = "ksy_syslog";

    public static  final String SAFETY_EXCHANGE = "safeExchange";

    public static  final String SAFELP_EXCHANGE = "safeLpExchange";

    public static  final String PRODUCT_EXCHANGE = "productExchange";

    public static  final String MATERIAL_EXCHANGE = "materialExchange";
    /**
     * 有限空间同步
     */
    public static  final String SAFETY_SPACE_ROUTE_KEY = "cep.erp.si.si40";
    /**
     * 高处作业同步
     */
    public static  final String SAFETY_HIGH_ROUTE_KEY = "cep.erp.si.si62";
    public static  final String SAFETY_HIGH_QUEUE = "cep.erp.si.si62";
    /**
     * 吊装作业同步
     */
    public static  final String SAFETY_HOISTING_ROUTE_KEY = "cep.erp.si.si61";
    /**
     * 动火作业同步
     */
    public static  final String SAFETY_FIRE_ROUTE_KEY = "cep.erp.si.si34";
    /**
     * 应急物资同步
     */
    public static  final String SAFETY_WORKSHOPACC_ROUTE_KEY = "cep.erp.si.si30";
    /**
     * 焦化产出与外发同步
     */
    public static  final String PRODUCT_COKING_OUTPUT_AND_OUTSOURCING_ROUTE_KEY = "cep.erp.oi.oic01";
    /**
     * 焦化主要经济指标
     */
    public static  final String PRODUCT_COKING_MAIN_ECONOMIC_INDICATORS_ROUTE_KEY = "cep.erp.oi.oic02";
    /**
     * 焦化能源消耗记录
     */
    public static  final String PRODUCT_COKING_ENERGY_CONSUMPTION_RECORD_ROUTE_KEY = "cep.erp.oi.oic03";
    /**
     * 配煤槽作业
     */
    public static  final String PRODUCT_COAL_BLENDING_TANK_OPERATION_ROUTE_KEY = "cep.erp.oi.oic19";

    /**
     * 配合煤配比及耗量
     */
    public static  final String PRODUCT_PROPORTIONING_AND_CONSUMPTION_OF_BLENDED_COAL_ROUTE_KEY = "cep.erp.oi.oic04";
    /**
     * 煤塔作业
     */
    public static  final String PRODUCT_COAL_TOWER_OPERATION_ROUTE_KEY = "cep.erp.oi.oic18";


    /**  焦炉操作记录  */
    public static final String COKE_OVEN_OPERATION_RECORD_ROUTE_KEY = "cep.erp.oi.oic05";
    /**  干熄焦操作记录  */
    public static final String COKE_DRY_QUENCHING_OPERATION_RECORD_ROUTE_KEY = "cep.erp.oi.oic12";

    /**  锅炉工艺指标  */
    public static final String BOILER_PROCESS_INDEX_ROUTE_KEY = "cep.erp.oi.oic20";
    /**  焦仓储量记录  */
    public static final String COKE_WAREHOUSE_STOCK_RECORD_ROUTE_KEY = "cep.erp.oi.oic21";

    /**
     * 安全数据采集
     */
    public static  final String SAFETY_DATA_COLLECTION_ROUTE_KEY = "cep.lp.si.si01";



    /**
     * 物料消耗数据同步
     */
    public static  final String MATERIAL_CONSUMPTION_ROUTE_KEY = "cep.erp.mr.mr58";

    /**
     * 煤消耗数据同步
     */
    public static  final String COAL_CONSUMPTION_ROUTE_KEY = "cep.erp.mr.me58";
}
