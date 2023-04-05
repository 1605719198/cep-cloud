package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 实体类-产量分析日表
 * @author sudeyou
 * @since 2022-07-15 14:27:41
 */
@TableName(value ="product_oi_yield_analysis_date")
@Data
public class ProductionYieldAnalysisDate implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 月份
     */
    private Integer month;

    /**
     * 日
     */
    private Integer date;

    /**
     * 物料id
     */
    private String materialsId;

    /**
     * 物料名称
     */
    private String materialsName;

    /**
     * 计划产量
     */
    private BigDecimal productionPlan;

    /**
     * 实绩产量
     */
    private BigDecimal productionPerformance;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

