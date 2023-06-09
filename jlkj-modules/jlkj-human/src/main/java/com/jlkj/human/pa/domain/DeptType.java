package com.jlkj.human.pa.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 岗位考评项目设定主档
 * @TableName human_pa_dept_type
 */
@TableName(value ="human_pa_dept_type")
@Data
public class DeptType implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 岗位类别
     */
    private String deptType;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 生效年月
     */
    private String effcMonth;

    /**
     * 团队绩效
     */
    private String isTeam;

    /**
     * 团队绩效比例
     */
    private Integer teamRatio;

    /**
     * 是否重点工作
     */
    private String isPoint;

    /**
     * 重点工作比例
     */
    private Integer pointRatio;

    /**
     * 固定考核项目
     */
    private String isFix;

    /**
     * 固定考核项目比例
     */
    private Integer fixRatio;

    /**
     * KPI指标
     */
    private String isKpi;

    /**
     * KPI指标比例
     */
    private Integer kpiRatio;

    /**
     * 自评
     */
    private String kpiSelf;

    /**
     * 上报级数
     */
    private Integer upLevel;

    /**
     * 人事审核
     */
    private String isHr;

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
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}