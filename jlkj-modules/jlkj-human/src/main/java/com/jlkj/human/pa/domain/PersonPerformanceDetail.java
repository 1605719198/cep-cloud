package com.jlkj.human.pa.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 个人绩效计划明细档
 * @TableName human_pa_person_performance_detail
 */
@TableName(value ="human_pa_person_performance_detail")
@Data
public class PersonPerformanceDetail implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 主档ID
     */
    private String personPerformanceId;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 重点工作类别（1-重点工作，2-KPI指标，3-固定工作，4-团队绩效）
     */
    private String type;

    /**
     * 序号
     */
    private String num;

    /**
     * 重点工作内容
     */
    private String item;

    /**
     * 完成标准/目标
     */
    private String itemGoal;

    /**
     * 完成时间
     */
    private Date completeTime;

    /**
     * 权重
     */
    private BigDecimal weight;

    /**
     * 实际完成情况
     */
    private String completeStatue;

    /**
     * 自评
     */
    private String selfScore;

    /**
     * 主管考评
     */
    private String admScore;

    /**
     * 备注
     */
    private String remark;

    /**
     * 考评标准S
     */
    private String fieldS;

    /**
     * 考评标准A
     */
    private String fieldA;

    /**
     * 考评标准B
     */
    private String fieldB;

    /**
     * 考评标准C
     */
    private String fieldC;

    /**
     * 考评标准D
     */
    private String fieldD;

    /**
     * 输入人
     */
    private String creator;

    /**
     * 输入人ID
     */
    private String creatorId;

    /**
     * 输入日期
     */
    private Date createDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}