package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类-生产实绩-上煤实绩
 *
 * @author sudeyou
 * @since 2022-11-18 09:39:27
 */
@TableName(value ="product_oi_conveying_coal_actual")
@Data
public class ProductionConveyingCoalActual implements Serializable {
    /**
     * 主键标识;UUID
     */
    @TableId
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
     * 配煤计划编号
     */
    private String configCokePlanId;

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
    private Integer cokeWarehouseNumber;

    /**
     * 煤的料号
     */
    private String cokeMaterialsCode;

    /**
     * 煤的料号名称
     */
    private String cokeMaterialsName;

    /**
     * 上煤重量
     */
    private Integer cokeWeight;

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
     * 小煤种编号
     */
    private String cokeMaterialsSmallCode;

    /**
     * 小煤种名称
     */
    private String cokeMaterialsSmallName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
