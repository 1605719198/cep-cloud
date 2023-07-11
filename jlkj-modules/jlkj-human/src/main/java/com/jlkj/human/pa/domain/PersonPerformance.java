package com.jlkj.human.pa.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 个人绩效主档
 * @author HuangBing
 * @TableName human_pa_person_performance
 */
@TableName(value ="human_pa_person_performance")
@Data
public class PersonPerformance implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 公司别
     */
    @Excel(name = "公司")
    private String compId;

    /**
     * 考评类别（1-月度，2-季度，3-年度）
     */
    @Excel(name = "考评类别")
    private String meritType;

    /**
     * 考评年月
     */
    @Excel(name = "考评年月")
    private String meritMonth;

    /**
     * 员工ID
     */
    @Excel(name = "员工编号/姓名")
    private String empId;

    /**
     * 岗位类别（岗位管理作业）
     */
    @Excel(name = "人员类别")
    private String deptType;

    /**
     * 一级单位
     */
    @Excel(name = "单位")
    private String dept;

    /**
     * 岗位
     */
    private String postId;

    /**
     * 职称
     */
    @Excel(name = "职称")
    private String jobTitleId;

    /**
     * 团队绩效（0-是，1-否）
     */
    private String isTeam;

    /**
     * 团队绩效比例
     */
    private Integer teamRatio;

    /**
     * 团队绩效分数
     */
    private BigDecimal teamScore;

    /**
     * 是否重点工作（0-是，1-否）
     */
    private String isPoint;

    /**
     * 重点工作比例
     */
    private Integer pointRatio;

    /**
     * 重点工作分数
     */
    private BigDecimal pointScore;

    /**
     * 固定考核项目
     */
    private String isFix;

    /**
     * 固定考核项目比例
     */
    private Integer fixRatio;

    /**
     * 固定考核项目分数
     */
    private BigDecimal fixScore;

    /**
     * 固定考核启动ID（固定考核启动时给值）
     */
    private String fixId;

    /**
     * KPI指标
     */
    private String isKpi;

    /**
     * KPI指标比例
     */
    private Integer kpiRatio;

    /**
     * KPI指标分数
     */
    private BigDecimal kpiScore;

    /**
     * 自述
     */
    private String selfAppr;

    /**
     * 主管评语
     */
    private String admAppr;

    /**
     * 出勤加扣分项
     */
    private BigDecimal pdAdjust;

    /**
     * 奖惩加扣分项
     */
    private BigDecimal pbAdjust;

    /**
     * 其他加扣分
     */
    private BigDecimal otherAdjust;

    /**
     * 考评得分
     */
    @Excel(name = "考绩分数")
    private BigDecimal score;

    /**
     * 考评主管
     */
    private String admEmpId;

    /**
     * 最终复核主管
     */
    private String lastEmpId;

    /**
     * 自评得分
     */
    private BigDecimal selfScore;

    /**
     * 主管考评得分
     */
    private BigDecimal admScore;

    /**
     * 等第
     */
    @Excel(name = "考评年月")
    private String grade;

    /**
     * 考评状态（0-计划中
1-已完成
2-计划审批中
3-自评中
4-主管考评中
5-最终复核中）
     */
    private String meritStatus;

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