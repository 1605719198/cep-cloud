package com.jlkj.rabbit.mqconstant;

/**
 * @author zyf
 * @Description
 * @create 2022-05-19 15:32
 */
public class MaterialConstants {
    /**
     * 料号基本资料
     */
    public static  final String MATERIAL_BASEPROFILE_QUEUE = "cep.mr.mr01";

    /**
     * 储位基本资料
     */
    public static  final String MATERIAL_STORAGEPROFILE_QUEUE = "cep.mr.mr02";

    /**
     * 物料品种资料
     */
    public static  final String MATERIAL_CATEGORY_QUEUE = "cep.mr.mr03";

    /**
     * 原料检斤计划
     */
    public static  final String MATERIAL_SWEIGHINGPLAN_QUEUE = "cep.mr.mr07";

    /**
     * 物料卸货实绩新增
     */
    public static  final String MATERIAL_SAVEMATERIALUNLOADINGPERFORMANCE_QUEUE = "cep.mr.mr57";

    /**
     * 原料入储信息
     */
    public static  final String MATERIAL_ADDMATERIALSSTORAGE_QUEUE = "cep.mr.mr10";



    /** 焦炭外售发货计划-message-id */
    public static final String MATERIAL_COKE_SALE_DELIVER_PLAN_MESSAGE_ID = "SB01";

    /** 焦炭外售发货计划-同步队列 */
    public static final String MATERIAL_COKE_SALE_DELIVER_PLAN_QUEUE = "cep.sb.sb01";

    /** 焦炭外售发货计划-数据来源 */
    public static final String MATERIAL_COKE_SALE_DELIVER_PLAN_DATASOURCE = "erp";

    /** 焦炭外售发货计划-操作标识-新增 */
    public static final String MATERIAL_COKE_SALE_DELIVER_PLAN_NEW_ACTION = "N";

    /** 焦炭外售发货计划-操作标识-删除 */
    public static final String MATERIAL_COKE_SALE_DELIVER_PLAN_DELETE_ACTION = "D";



    /** 焦炭外售销账实际-message-id */
    public static final String MATERIAL_COKE_SALE_WRITE_OFF_REAL_MESSAGE_ID = "il01";

    /** 焦炭外售销账实际-同步队列 */
    public static final String MATERIAL_COKE_SALE_WRITE_OFF_REAL_QUEUE = "cep.il.il01";

    /** 焦炭外售销账实际-数据来源 */
    public static final String MATERIAL_COKE_SALE_WRITE_OFF_REAL_DATASOURCE = "erp";

    /** 焦炭外售销账实际-操作标识-新增 */
    public static final String MATERIAL_COKE_SALE_WRITE_OFF_REAL_NEW_ACTION = "N";

    /** 焦炭外售销账实际-操作标识-删除 */
    public static final String MATERIAL_COKE_SALE_WRITE_OFF_REAL_DELETE_ACTION = "D";



}
