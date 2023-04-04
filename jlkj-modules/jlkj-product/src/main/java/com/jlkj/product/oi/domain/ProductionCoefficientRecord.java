package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zyf
 * 焦炉系数记录
 * @TableName product_oi_coefficient_record
 */
@TableName(value ="product_oi_coefficient_record")
@Data
public class ProductionCoefficientRecord implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

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
     * 测温日期
     */
    private Date measureTime;

    /**
     * K均
     */
    private BigDecimal kJun;

    /**
     * K安
     */
    private BigDecimal kAn;

    /**
     * K1
     */
    private BigDecimal k1;

    /**
     * K2
     */
    private BigDecimal k2;

    /**
     * K3
     */
    private BigDecimal k3;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
