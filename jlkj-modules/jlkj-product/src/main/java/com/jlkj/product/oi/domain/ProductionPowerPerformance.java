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
 * 发电量实绩
 * @TableName product_oi_power_performance
 */
@TableName(value ="product_oi_power_performance")
@Data
public class ProductionPowerPerformance implements Serializable {
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
     * 班组开始时间
     */
    private Date classStartTime;

    /**
     * 班组结束时间
     */
    private Date classEndTime;

    /**
     * 发电量
     */
    private BigDecimal powerGeneration;

    /**
     * 上网电量
     */
    private BigDecimal powerInternet;

    /**
     * 发电厂用电量
     */
    private BigDecimal powerPlantEnergy;

    /**
     * 发电机组汽耗率
     */
    private BigDecimal generatorConsumptionRate;

    /**
     * 机组进气量
     */
    private BigDecimal unitAir;

    /**
     * 除盐水补水量
     */
    private BigDecimal desalinationWaterVolume;

    /**
     * 端差
     */
    private BigDecimal endDifference;

    /**
     * 真空
     */
    private BigDecimal vacuum;

    /**
     * 新水消耗
     */
    private BigDecimal newWaterVolume;

    /**
     * 降低发电单位成本
     */
    private BigDecimal lowerUnitElectricity;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
