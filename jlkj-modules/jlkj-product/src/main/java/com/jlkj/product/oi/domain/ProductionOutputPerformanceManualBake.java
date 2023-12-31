package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 实体类-生产管理-炼焦实绩-出炉实绩-手动出炉
 *
 * @author sudeyou
 * @since 2022-12-28 15:20:12
 */
@TableName(value ="product_oi_output_performance_manual_bake")
@Data
public class ProductionOutputPerformanceManualBake implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 本炉对应装煤记录编号
     */
    private String coalRecordId;

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
     * 1:干熄焦；2:湿熄焦
     */
    private Short cokeType;

    /**
     * 计划运转周期
     */
    private String planningRunCycle;

    /**
     * 实际运转周期
     */
    private String actualRunCycle;

    /**
     * 单孔产量
     */
    private BigDecimal singleHoleProduction;

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

    /**
     * 配煤实绩主记录id
     */
    private String coalPerformanceId;

    /**
     * 出炉后装煤记录编号
     */
    private String nextCoalRecordId;
    /**
     * 配煤实绩明细开始时间
     */
    private Date coalDetailStartTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

