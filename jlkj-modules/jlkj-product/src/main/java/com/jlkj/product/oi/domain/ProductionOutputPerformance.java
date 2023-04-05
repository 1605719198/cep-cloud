package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author  generator
 * 出炉实绩
 * @TableName product_oi_output_performance
 */
@TableName(value ="product_oi_output_performance")
@Data
public class ProductionOutputPerformance implements Serializable {
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
     * 炭化室号
     */
    private Integer carbonizationChamberNumber;

    /**
     * 生产日期
     */
    private Date productionDate;

    /**
     * 计划推焦时间
     */
    private Date planningProductionTime;

    /**
     * 实际推焦时间
     */
    private Date actualProductionTime;

    /**
     * 推焦电流
     */
    private BigDecimal cokeCurrent;

    /**
     * 计划装煤时间
     */
    private Date planningLoadCoalTime;

    /**
     * 实际装煤时间
     */
    private Date actualLoadCoalTime;

    /**
     * 装煤电流
     */
    private BigDecimal coalCurrent;

    /**
     * 计划运转周期
     */
    private BigDecimal planningRunCycle;

    /**
     * 实际运转周期
     */
    private BigDecimal actualRunCycle;

    /**
     * 单孔产量
     */
    private BigDecimal singleHoleProduction;

    /**
     * 装煤量
     */
    private BigDecimal loadingCoal;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
