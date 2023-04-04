package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 生产实绩-配煤仓配煤记录
 * @TableName product_oi_coal_warehouse_blended_coal_record
 */
@TableName(value ="product_oi_coal_warehouse_blended_coal_record")
@Data
public class ProductionCoalWarehouseBlendedCoalRecord implements Serializable {
    /**
     * 主键标识;UUID
     */
    private String id;

    /**
     * 班次代码
     */
    private String shiftName;

    /**
     * 班别名称
     */
    private String className;

    /**
     * 储煤塔配煤记录编号
     */
    private String coalTowerRecordId;

    /**
     * 配煤实绩编号
     */
    private String blendedCoalActualPerformanceId;

    /**
     * 计划编号
     */
    private String planId;

    /**
     * 焦炭等级料号
     */
    private String coalLevelMaterialsCode;

    /**
     * 焦炭等级名称
     */
    private String coalLevelMaterialsName;

    /**
     * 配煤仓号
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
     * 数据来源（0:手工填报；1:数据采集）
     */
    private Integer dataSources;

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
     * 上班时间
     */
    private Date shiftStartTime;

    /**
     * 下班时间
     */
    private Date shiftEndTime;
    /**
     * 小煤种编号
     */
    private String materialSmallCode;

    /**
     * 小煤种名称
     */
    private String materialSmallName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
