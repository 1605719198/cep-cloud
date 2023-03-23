package com.jlkj.safety.si.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * 盲板抽堵安全作业证-审批记录
 * @TableName safe_si_job_blind_plate_plugging_approval
 * @author zyf
 */
@TableName(value ="safe_si_job_blind_plate_plugging_approval")
@Data
public class SafeJobBlindPlatePluggingApproval implements Serializable {
    /**
     * 行标识
     */
    @TableId
    private String id;

    /**
     * 作业证ID
     */
    private String jobId;

    /**
     * 序号
     */
    private Integer sort;

    /**
     * 审批环节
     */
    private String approvalName;

    /**
     * 审批人ID
     */
    private String approvalPersonId;

    /**
     * 审批人姓名
     */
    private String approvalPersonName;

    /**
     * 是否需要审批意见
     */
    private Integer isRequired;

    /**
     * 审批意见内容
     */
    private String content;

    /**
     * 确认时间
     */
    private LocalDateTime confirmTime;

    /**
     * 审批时间
     */
    private LocalDateTime approvalTime;

    /**
     * 是否审批(0未审批,1已审批)
     */
    private Integer isApproval;

    /**
     * 选择人ID
     */
    private String pickPersonId;

    /**
     * 选择人姓名
     */
    private String pickPersonName;

    /**
     * 选择时间
     */
    private LocalDateTime pickTime;

    /**
     * 电子签名
     */
    private String signature;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
