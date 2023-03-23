package com.jlkj.safety.si.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 7.动土安全作业证
 * @TableName safe_si_job_soil
 * @author zyf
 */
@TableName(value ="safe_si_job_soil")
@Data
public class SafeJobSoil implements Serializable {
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
     * 监护人ID
     */
    private String guardianPersonId;

    /**
     * 监护人姓名
     */
    private String guardianPersonName;

    /**
     * 作业开始时间
     */
    private LocalDateTime startTime;

    /**
     * 作业结束时间
     */
    private LocalDateTime endTime;

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
     * 作业单位ID
     */
    private String jobUnitId;

    /**
     * 作业单位名称
     */
    private String jobUnitName;

    /**
     * 涉及的其它特殊作业
     */
    private String otherJob;

    /**
     * 危害辨识
     */
    private String harmIdentification;

    /**
     * 实施安全教育人
     */
    private String safeEducationPersons;

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
     * 作业内容
     */
    private String jobContent;

    /**
     * 作业名称
     */
    private String jobName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
