package com.jlkj.energy.ee.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 实体类-能源耗用产出账务信息
 * @author sudeyou
 * @since 2022-09-16 16:10:10
 */
@TableName(value ="energy_ee_consumption_output_accounting")
@Data
public class EnergyConsumptionOutputAccounting implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 成本中心代号
     */
    private String costCenter;

    /**
     * 工厂产线代码
     */
    private String factoryId;

    /**
     * 产线别代号
     */
    private String millId;

    /**
     * 能源计量日期
     */
    private String eountDate;

    /**
     * 能源代码
     */
    private String engyId;

    /**
     * 能源量类别代号
     */
    private String ioTypeId;

    /**
     * 能源计量单位
     */
    private String engyUnitF;

    /**
     * 能源量
     */
    private BigDecimal proConQty;

    /**
     * 能源热值
     */
    private BigDecimal calValue;

    /**
     * 能量值
     */
    private BigDecimal engyValue;

    /**
     * 卖方成本中心
     */
    private String chargeCostCenter;

    /**
     * 卖方工场产线代码
     */
    private String chargeFactoryId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

