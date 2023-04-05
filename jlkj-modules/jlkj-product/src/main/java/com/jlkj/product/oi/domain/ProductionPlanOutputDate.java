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
 * 计划管理--日产量计划表
 * @TableName product_oi_plan_output_date
 */
@TableName(value ="product_oi_plan_output_date")
@Data
public class ProductionPlanOutputDate implements Serializable {
    /**
     * 计划编号;UUID
     */
    @TableId
    private String id;

    /**
     * 计划年度
     */
    private Integer planYear;

    /**
     * 计划月份
     */
    private Integer planMonth;

    /**
     * 计划日期
     */
    private Integer planDate;

    /**
     * 指标项编号
     */
    private String targetItemId;

    /**
     * 指标产量
     */
    private BigDecimal targetItemOutput;

    /**
     * 创建人编号
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
     * 修改人编号
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
     * 是否在维修计划内;0.默认不在，1.在维修计划内
     */
    private Integer isRepair;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
