package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 配煤实绩明细
 * @TableName product_oi_configure_coal_species_performance_detail
 */
@TableName(value ="product_oi_configure_coal_species_performance_detail")
@Data
public class ProductionConfigureCoalSpeciesPerformanceDetail implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 主记录编号
     */
    private String mainId;

    /**
     * 班次
     */
    private String shiftName;

    /**
     * 班别
     */
    private String className;

    /**
     * 计划编号
     */
    private String planId;

    /**
     * 上煤仓号
     */
    private Integer bunkerNumber;

    /**
     * 储煤塔号
     */
    private Integer coalTowerNumber;

    /**
     * 配煤前料位
     */
    private BigDecimal beforeMaterialLevel;

    /**
     * 配煤后料位
     */
    private BigDecimal afterMaterialLevel;

    /**
     * 配煤前存量
     */
    private BigDecimal beforeStock;

    /**
     * 配煤后存量
     */
    private BigDecimal afterStock;

    /**
     * 配煤日期
     */
    private Date coalDeliveryDate;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 配煤时长
     */
    private BigDecimal duration;

    /**
     * 配煤重量
     */
    private BigDecimal materialWeight;

    /**
     * 物料编号
     */
    private String materialNumber;

    /**
     * 物料名称
     */
    private String materialName;

    /**
     * 物料代码
     */
    private String materialCode;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建人
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人id
     */
    private String modifyUserId;

    /**
     * 修改人
     */
    private String modifyUserName;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 来源(0:手工填报；1:数据采集)
     */
    private Integer dataSources;

    /**
     * 上班时间
     */
    private Date shiftStartTime;

    /**
     * 下班时间
     */
    private Date shiftEndTime;

    /**
     * 实际配煤比
     */
    private BigDecimal actualProportion;

    /**
     * 配煤精准度
     */
    private BigDecimal coalBlendingAccuracy;

    /**
     * 小煤种变好
     */
    private String materialSmallCode;

    /**
     * 小煤种名称
     */
    private String materialSmallName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
