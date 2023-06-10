package com.jlkj.human.pa.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * KPI指标明细档
 * @TableName human_pa_dept_kpi_detail
 */
@TableName(value ="human_pa_dept_kpi_detail")
@Data
public class DeptKpiDetail implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * kpi主档ID
     */
    private String kpiId;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 一级单位
     */
    private String dept;

    /**
     * 生效年月
     */
    private String effcMonth;

    /**
     * 编码
     */
    private String num;

    /**
     * 指标项目
     */
    private String item;

    /**
     * 数据提供单位
     */
    private String dataDept;

    /**
     * 权重
     */
    private BigDecimal weight;

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