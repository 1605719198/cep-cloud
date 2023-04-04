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
 * 计划管理-能源月计划
 * @TableName product_oi_plan_energy_month
 */
@TableName(value ="product_oi_plan_energy_month")
@Data
public class ProductionPlanEnergyMonth implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 计划月份
     */
    private String planMonth;

    /**
     * 部门1编号
     */
    private String department1Id;

    /**
     * 部门1名称
     */
    private String department1Name;

    /**
     * 部门2编号
     */
    private String department2Id;

    /**
     * 部门2名称
     */
    private String department2Name;

    /**
     * 能源分类编号
     */
    private String energyClassificationId;

    /**
     * 能源分类
     */
    private String energyClassificationName;

    /**
     * 能源编号
     */
    private String energyId;

    /**
     * 能源名称
     */
    private String energyName;

    /**
     * 能源代码
     */
    private String energyCode;

    /**
     * 类别
     */
    private String category;

    /**
     * 计划量
     */
    private BigDecimal plannedAmount;

    /**
     * 日计划公式
     */
    private String formulaDay;

    /**
     * 单位
     */
    private String unitName;

    /**
     * 行业先进值
     */
    private BigDecimal industryAdvancedValue;

    /**
     * 内控指标值
     */
    private BigDecimal internalControValue;

    /**
     * 备注
     */
    private String remark;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
