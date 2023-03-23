package com.jlkj.safety.si.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 4.吊装安全作业证
 * </p>
 *
 * @author su
 * @since 2022-05-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiJobHoisting implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 作业证ID
     */
    private String id;

    /**
     * 申请单位ID
     */
    private String applyDepartId;

    /**
     * 申请单位名称
     */
    private String applyDepartName;

    /**
     * 申请人ID
     */
    private String applyPersonId;

    /**
     * 申请人姓名
     */
    private String applyPersonName;

    /**
     * 吊装所在区域ID
     */
    private String safeAreaId;

    /**
     * 吊装所在区域名称
     */
    private String safeAreaName;

    /**
     * 作业详细地点
     */
    private String place;

    /**
     * 吊装工具名称
     */
    private String hoistingTools;

    /**
     * 作业证编号
     */
    private String jobCode;

    /**
     * 吊装人员及特殊工种作业证号
     */
    private String jobPersons;

    /**
     * 监护人ID
     */
    private String guardianPersonId;

    /**
     * 监护人姓名
     */
    private String guardianPersonName;

    /**
     * 吊装指挥及特殊工种作业证号
     */
    private String jobDirector;

    /**
     * 起吊重物质量
     */
    private String weight;

    /**
     * 作业开始时间
     */
    private LocalDateTime startTime;

    /**
     * 作业结束时间
     */
    private LocalDateTime endTime;

    /**
     * 吊装内容
     */
    private String jobContent;

    /**
     * 危害辨识
     */
    private String harmIdentification;

    /**
     * 实施安全教育人
     */
    private String safeEducationPersons;

    /**
     * 实施安全教育人id
     */
    private String safeEducationPersonsId;

    /**
     * 状态(1未完成,2已完成)
     */
    private Integer status;

    /**
     * 申请时间
     */
    private LocalDateTime applyTime;

    /**
     * 完成时间
     */
    private LocalDateTime finishTime;

    /**
     * 吊装作业名称
     */
    private String hoistName;

    /**
     * 吊装作业级别
     */
    private String jobLevel;

    /**
     * 作业负责人id
     */
    private String hoistChargeId;

    /**
     * 作业负责人
     */
    private String hoistChargeName;

    /**
     * 交底人
     */
    private String specialName;

    /**
     * ERP作业证编号
     */
    private String jobCodeErp;
}
