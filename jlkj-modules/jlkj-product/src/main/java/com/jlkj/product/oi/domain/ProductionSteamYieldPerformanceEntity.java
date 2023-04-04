package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zzh
 * 发电实绩中的蒸汽产量实绩的实体类
 * @time 2022年9月8日14:09:40
 */
@TableName(value ="product_oi_steam_performance")
@Data
public class ProductionSteamYieldPerformanceEntity implements Serializable {

    /**
     * 主键;UUID
     */
    @TableId
    private String id;

    /**
     * 锅炉号
     */
    private String boilerNo;

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
