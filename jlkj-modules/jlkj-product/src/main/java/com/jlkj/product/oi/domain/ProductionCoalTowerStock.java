package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 生产实绩-储煤塔存量
 * @TableName product_oi_coal_tower_stock
 */
@TableName(value ="product_oi_coal_tower_stock")
@Data
public class ProductionCoalTowerStock implements Serializable {
    /**
     * 主键标识;UUID
     */
    private String id;

    /**
     * 储煤塔号
     */
    private Integer coalTowerNumber;

    /**
     * 煤余量
     */
    private BigDecimal coalSurplus;

    /**
     * 料位
     */
    private BigDecimal materialLevel;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
