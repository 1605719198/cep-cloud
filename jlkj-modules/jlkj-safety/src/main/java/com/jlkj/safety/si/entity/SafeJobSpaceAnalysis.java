package com.jlkj.safety.si.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 受限空间安全作业证-分析
 * @TableName safe_si_job_space_analysis
 * @author sdy
 */
@TableName(value ="safe_si_job_space_analysis")
@Data
public class SafeJobSpaceAnalysis implements Serializable {
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
     * 有毒介质
     */
    private String toxicMedium;

    /**
     * 可燃气体
     */
    private String combustibleGas;

    /**
     * 氧含量
     */
    private String oxygenContent;

    /**
     * 时间
     */
    private LocalDateTime analysisTime;

    /**
     * 部位
     */
    private String position;

    /**
     * 分析人id
     */
    private String personId;

    /**
     * 分析人
     */
    private String personName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
