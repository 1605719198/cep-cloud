package com.jlkj.safety.si.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 1.动火安全作业证
 * </p>
 *
 * @author su
 * @since 2022-06-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SafeSiJobFire implements Serializable {

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
     * 作业证编号
     */
    private String jobCode;

    /**
     * 动火作业级别
     */
    private String jobLevel;

    /**
     * 动火区域ID
     */
    private String safeAreaId;

    /**
     * 动火区域名称
     */
    private String safeAreaName;

    /**
     * 动火详细地点
     */
    private String place;

    /**
     * 动火方式
     */
    private String mode;

    /**
     * 动火作业负责人
     */
    private String jobResponsiblePersonName;

    /**
     * 动火开始时间
     */
    private LocalDateTime startTime;

    /**
     * 动火结束时间
     */
    private LocalDateTime endTime;

    /**
     * 涉及的其它特殊作业
     */
    private String otherJob;

    /**
     * 安全教育人
     */
    private String safeEducationPersonName;

    /**
     * 危害辨识
     */
    private String harmIdentification;

    /**
     * 监护人ID
     */
    private String guardianPersonId;

    /**
     * 监护人姓名
     */
    private String guardianPersonName;

    /**
     * 工种
     */
    private String workTypeName;

    /**
     * 生产单位负责人
     */
    private String productionResponsiblePersonName;

    /**
     * 相关单位监护人ID
     */
    private String relationGuardianPersonId;

    /**
     * 相关单位监护人姓名
     */
    private String relationGuardianPersonName;

    /**
     * 相关单位监护人工种
     */
    private String relationGuardianPersonWorkTypeName;

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
     * 设备单位id
     */
    private String equipmentUnitId;

    /**
     * 设备单位名称
     */
    private String equipmentUnitName;

    /**
     * 特种作业证号
     */
    private String specialJobNo;

    /**
     * 作业内容
     */
    private String jobContents;

    /**
     * 作业负责人电话
     */
    private String jobResponsiblePersonPhone;

    /**
     * 是否煤气区域(0否1是)
     */
    private Integer isGasArea;

    /**
     * 动火作业负责人id
     */
    private String jobResponsiblePersonId;

    /**
     * 交底人
     */
    private String specialName;

    /**
     * ERP作业证编号
     */
    private String jobCodeErp;

}
