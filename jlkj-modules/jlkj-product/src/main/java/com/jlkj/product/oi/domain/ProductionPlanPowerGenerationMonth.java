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
 * 计划管理--发电月指标计划
 * @TableName product_oi_plan_power_generation_month
 */
@TableName(value ="product_oi_plan_power_generation_month")
@Data
public class ProductionPlanPowerGenerationMonth implements Serializable {
    /**
     * 计划编号;
     */
    @TableId
    private String id;

    /**
     * 计划年度;
     */
    private Integer planYear;

    /**
     * 计划月份
     */
    private Integer planMonth;

    /**
     * 指标项编号;
     */
    private String targetItemId;

    /**
     * 指标项名称;
     */
    private String targetItemName;

    /**
     * 指标项值;
     */
    private BigDecimal targetItemValue;

    /**
     * 措施及完成目标
     */
    private String measuresAndGoals;

    /**
     * 责任人
     */
    private String responsiblePerson;

    /**
     * 创建人编号;
     */
    private String createUserId;

    /**
     * 创建人姓名;
     */
    private String createUserName;

    /**
     * 创建时间;
     */
    private Date createTime;

    /**
     * 修改人编号;
     */
    private String modifyUserId;

    /**
     * 修改人姓名;
     */
    private String modifyUserName;

    /**
     * 修改时间;
     */
    private Date modifyTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
