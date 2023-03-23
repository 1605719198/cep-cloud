package com.jlkj.safety.si.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 3.高处安全作业证
 * @TableName safe_si_job_high
 * @author sdy
 */
@TableName(value ="safe_si_job_high")
@Data
public class SafeJobHigh implements Serializable {
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
     * 作业负责人ID
     */
    private String jobManagerId;

    /**
     * 作业负责人工号
     */
    private String jobManagerNo;

    /**
     * 作业负责人姓名
     */
    private String jobManagerName;

    /**
     * 作业证编号
     */
    private String jobCode;

    /**
     * 作业开始时间
     */
    private LocalDateTime startTime;

    /**
     * 作业结束时间
     */
    private LocalDateTime endTime;

    /**
     * 作业级别
     */
    private String jobLevel;

    /**
     * 作业所在区域ID
     */
    private String safeAreaId;

    /**
     * 作业所在区域名称
     */
    private String safeAreaName;

    /**
     * 作业详细地点
     */
    private String place;

    /**
     * 作业内容
     */
    private String jobContent;

    /**
     * 作业高度
     */
    private String jobHeight;

    /**
     * 作业类别
     */
    private String jobCategory;

    /**
     * 作业单位
     */
    private String jobUnit;

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
     * 危害辨识
     */
    private String harmIdentification;

    /**
     * 实施安全教育人id
     */
    private String safeEducationPersonsId;

    /**
     * 实施安全教育人
     */
    private String safeEducationPersons;

    /**
     * 受教育人id
     */
    private String educComrId;

    /**
     * 受教育人
     */
    private String educComName;

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
     * ERP作业证编号
     */
    private String jobCodeErp;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
