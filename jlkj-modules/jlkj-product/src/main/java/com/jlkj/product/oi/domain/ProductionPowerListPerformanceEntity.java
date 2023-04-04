package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Mapper 接口  发电实绩中的发电量实绩实体
 * @author zzh
 * @since 2022年9月7日15:12:57
 */
@TableName(value ="product_oi_power_generation_performance")
@Data
public class ProductionPowerListPerformanceEntity implements Serializable {
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
