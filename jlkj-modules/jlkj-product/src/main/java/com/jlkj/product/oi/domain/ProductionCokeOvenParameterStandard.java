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
 * 生产参数-焦炉参数标准
 * @TableName product_oi_coke_oven_parameter_standard
 */
@TableName(value ="product_oi_coke_oven_parameter_standard")
@Data
public class ProductionCokeOvenParameterStandard implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 规定装煤量
     */
    private BigDecimal standardCoalLoading;

    /**
     * 规定结焦时间
     */
    private BigDecimal cokingCycle;

    /**
     * 焦侧标准火道温度
     */
    private BigDecimal fireTemperature;

    /**
     * 直行标准温度
     */
    private BigDecimal zhiHangTemperature;

    /**
     * K装正负
     */
    private BigDecimal coalLoadingFactor;

    /**
     * K计正负
     */
    private BigDecimal planningFactor;

    /**
     * K均正负
     */
    private BigDecimal averageCoefficient;

    /**
     * K安正负
     */
    private BigDecimal stabilityFactor;

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
