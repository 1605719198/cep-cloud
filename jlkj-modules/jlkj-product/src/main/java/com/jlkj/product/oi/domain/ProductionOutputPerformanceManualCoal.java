package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类-生产管理-炼焦实绩-出炉实绩-手动装煤
 *
 * @author sudeyou
 * @since 2022-12-28 15:56:04
 */
@TableName(value ="product_oi_output_performance_manual_coal")
@Data
public class ProductionOutputPerformanceManualCoal implements Serializable {
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
     * 储煤塔号
     */
    private Integer coalTowerNumber;

    /**
     * 生产日期
     */
    private Date productionDate;

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
     * 装煤量
     */
    private BigDecimal loadingCoal;

    /**
     * 装煤后累加重量
     */
    private BigDecimal loadingCoalAfter;

    /**
     * 数据来源(0:手工填报；1:数据采集)
     */
    private Short dataSources;

    /**
     * 配煤计划编号
     */
    private String coalPlanNumber;

    /**
     * 焦炭等级(物料代码)
     */
    private String materialsCode;

    /**
     * 焦炭等级名称
     */
    private String materialsName;

    /**
     * 创建人id(数据采集为空)
     */
    private String createUserId;

    /**
     * 创建人姓名(数据采集为空)
     */
    private String createUserName;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

