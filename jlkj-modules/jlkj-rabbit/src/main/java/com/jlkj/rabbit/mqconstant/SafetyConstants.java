package com.jlkj.rabbit.mqconstant;

/**
 * 安环模块常量定义
 * @author sudeyou
 * @date 2022/7/11
 */
public class SafetyConstants {
    /** 作业证编号同步队列 */
    public static final String SAFETY_DANGERNO_QUEUE = "cep.si.siDangerNo";

    /** 数据来源 */
    public static final String SAFETY_DATASOURCE = "erp";

    /** 操作标识-新建 */
    public static final String SAFETY_NEW_ACTION = "N";

    /** 动火message-id */
    public static final String SAFETY_FIRE_MESSAGE_ID = "SI_01";

    /** 有限空间message-id */
    public static final String SAFETY_SPACE_MESSAGE_ID = "SI_02";

    /** 吊装message-id */
    public static final String SAFETY_HOISTING_MESSAGE_ID = "SI_03";

    /** 高处message-id */
    public static final String SAFETY_HIGH_MESSAGE_ID = "SI_04";

}
