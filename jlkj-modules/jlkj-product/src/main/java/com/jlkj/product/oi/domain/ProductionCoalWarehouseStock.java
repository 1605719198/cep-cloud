package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yzl
 * @create 2022-12-08 14:32:11.531027
 */
@TableName(value ="product_oi_coal_warehouse_stock")
@Data
public class ProductionCoalWarehouseStock  implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 配煤仓号 */
	private Integer coalWarehouseNumber ;

    /** 剩余煤量(KG) */
	private BigDecimal coalSurplus ;

    /** 料位 */
	private Integer materialLevel ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

