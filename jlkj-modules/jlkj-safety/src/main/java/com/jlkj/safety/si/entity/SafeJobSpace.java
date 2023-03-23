package com.jlkj.safety.si.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 2.受限空间安全作业证
 * @TableName safe_si_job_space
 * @author sdy
 */
@TableName(value ="safe_si_job_space")
@Data
public class SafeJobSpace implements Serializable {
    /**
     * 作业证ID
     */
    @TableId
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
     * 作业级别
     */
    private String jobLevel;

    /**
     * 受限空间所在区域ID
     */
    private String safeAreaId;

    /**
     * 受限空间所在区域名称
     */
    private String safeAreaName;

    /**
     * 受限空间详细地点
     */
    private String place;

    /**
     * 原有介质名称
     */
    private String originalMediumName;

    /**
     * 作业内容
     */
    private String jobContent;

    /**
     * 作业开始时间
     */
    private LocalDateTime startTime;

    /**
     * 作业结束时间
     */
    private LocalDateTime endTime;

    /**
     * 作业单位负责人id
     */
    private String jobResponsiblePersonId;

    /**
     * 作业单位负责人
     */
    private String jobResponsiblePersonName;

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
     * 作业人员姓名
     */
    private String jobPersons;

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
     * 许可证编制人id
     */
    private String workEmpId;

    /**
     * 许可证编制人
     */
    private String workEmpName;

    /**
     * 救援器具
     */
    private String rescueEquipment;

    /**
     * 设备单位id
     */
    private String equipDepId;

    /**
     * 设备单位名称
     */
    private String equipDepName;

    /**
     * ERP作业证编号
     */
    private String jobCodeErp;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
