package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sudeyou
 * 计划管理--配煤计划配煤录
 * @TableName product_oi_plan_config_coke_detail
 */
@TableName(value ="product_oi_plan_config_coke_detail")
@Data
public class ProductionPlanConfigCokeDetail implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
    private String id;

    /**
     * 计划编号
     */
    private String planId;

    /**
     * 配煤仓号
     */
    private Integer warehouseNumber;

    /**
     * 煤种编号
     */
    private String coalTypeId;

    /**
     * 煤种名称
     */
    private String coalTypeName;

    /**
     * 小煤种代码
     */
    private String materialsSmallCode;

    /**
     * 小煤种简称
     */
    private String materialsSmallName;

    /**
     * 配置比例
     */
    private BigDecimal proportion;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
