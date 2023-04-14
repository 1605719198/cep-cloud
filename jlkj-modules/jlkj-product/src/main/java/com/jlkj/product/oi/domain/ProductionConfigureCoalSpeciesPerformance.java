package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 生产管理-煤焦实绩-配煤实绩主记录
 * @TableName product_oi_configure_coal_species_performance
 * @author Liukuiyan
 */
@TableName(value ="product_oi_configure_coal_species_performance")
@Data
public class ProductionConfigureCoalSpeciesPerformance implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 计划编号
     */
    private String planId;

    /**
     * 班次
     */
    private String shiftName;

    /**
     * 班别
     */
    private String className;

    /**
     * 配煤日期
     */
    private Date configureCoalSpeciesDate;

    /**
     * 储煤塔号
     */
    private Integer coalTowerNumber;

    /**
     * 入煤前料位
     */
    private BigDecimal beforeMaterialLevel;

    /**
     * 入煤后料位
     */
    private BigDecimal afterMaterialLevel;

    /**
     * 入煤前存量
     */
    private BigDecimal beforeStock;

    /**
     * 入煤后存量
     */
    private BigDecimal afterStock;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 配煤时长
     */
    private BigDecimal duration;

    /**
     * 配煤总量
     */
    private BigDecimal coalTowerWeight;

    /**
     * 实际配煤比
     */
    private BigDecimal actualProportion;

    /**
     * 配煤精准度
     */
    private BigDecimal coalBlendingAccuracy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 焦炭等级料号
     */
    private String coalLevelMaterialsCode;

    /**
     * 焦炭等级名称
     */
    private String coalLevelMaterialsName;

    /**
     * 数据来源（0:手工填报；1:数据采集）
     */
    private Integer dataSources;

    /**
     * 上班时间
     */
    private Date shiftStartTime;

    /**
     * 下班时间
     */
    private Date shiftEndTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
