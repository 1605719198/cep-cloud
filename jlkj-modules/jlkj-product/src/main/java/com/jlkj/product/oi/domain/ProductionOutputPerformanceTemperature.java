package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类-出炉实绩测温记录
 * @author sudeyou
 * @since 2022-08-11 14:06:15
 */
@TableName(value ="product_oi_output_performance_temperature")
@Data
public class ProductionOutputPerformanceTemperature implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 测温日期
     */
    private Date measureTime;

    /**
     * 班次
     */
    private String shiftName;

    /**
     * 班别
     */
    private String className;

    /**
     * 炉号
     */
    private Integer cokeOvenNumber;

    /**
     * 测温类型
     */
    private Short measureType;

    /**
     * 标准温度
     */
    private BigDecimal standardTemp;

    /**
     * 高
     */
    private BigDecimal high;

    /**
     * 低
     */
    private BigDecimal low;

    /**
     * 最高温度
     */
    private BigDecimal maximumTemp;

    /**
     * 最低温度
     */
    private BigDecimal minimumTemp;

    /**
     * 平均温度
     */
    private BigDecimal averageTemp;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

