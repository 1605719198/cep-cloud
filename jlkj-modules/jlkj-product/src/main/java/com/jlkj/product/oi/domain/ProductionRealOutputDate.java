package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 实体类-生产管理-日产量
 * @author sudeyou
 * @since 2022-07-13 14:48:56
 */
@TableName(value ="product_oi_real_output_date")
@Data
public class ProductionRealOutputDate implements Serializable {
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
     * 日期
     */
    private Integer date;

    /**
     * 指标项编号
     */
    private String targetItemId;

    /**
     * 指标项名称
     */
    private String targetItemName;

    /**
     * 指标项产量
     */
    private BigDecimal targetItemOutput;

    /**
     * 去年平均
     */
    private BigDecimal averageLastYear;

    /**
     * 去年同期
     */
    private BigDecimal sameTimeLastYear;

    /**
     * 历史最大值
     */
    private BigDecimal historicalMaximum;

    /**
     * 历史最小值
     */
    private BigDecimal historicalMinimum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

