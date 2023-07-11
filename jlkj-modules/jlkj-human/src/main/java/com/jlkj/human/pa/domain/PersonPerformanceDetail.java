package com.jlkj.human.pa.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 个人绩效计划明细档
 * @author HuangBing
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
    @NotNull(message = "重点工作内容为必填项")
    private String item;

    /**
     * 完成标准/目标
     */
    @NotNull(message = "完成标准/目标为必填项")
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
    private BigDecimal selfScore;

    /**
     * 主管考评
     */
    private BigDecimal admScore;

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
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String creator;

    /**
     * 输入人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String creatorId;

    /**
     * 输入日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}