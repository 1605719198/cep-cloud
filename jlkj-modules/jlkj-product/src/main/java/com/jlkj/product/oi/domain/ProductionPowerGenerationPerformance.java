package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类-发电实绩
 * @author sudeyou
 * @since 2022-09-01 09:44:19
 */
@TableName(value ="product_oi_power_generation_performance")
@Data
public class ProductionPowerGenerationPerformance implements Serializable {
    /**
     * 主键;UUID
     */
    @TableId
    private String id;

    /**
     * 汽轮机号
     */
    private String turbineNo;

    /**
     * 班次
     */
    private String shift;

    /**
     * 班别
     */
    private String classes;

    /**
     * 时间
     */
    private Date statTime;

    /**
     * 产量
     */
    private BigDecimal yield;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

