package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 实体类-生产实绩-上煤记录
 *
 * @author sudeyou
 * @since 2022-11-18 09:25:55
 */
@TableName(value ="product_oi_conveying_coal_record")
@Data
public class ProductionConveyingCoalRecord implements Serializable {
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
     * 班次代码
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
     * 上煤开始时间
     */
    private Date startTime;

    /**
     * 上煤结束时间
     */
    private Date endTime;

    /**
     * 上煤重量
     */
    private Integer cokeWeight;

    /**
     * 上煤前重量
     */
    private Integer cokeWeightBefor;

    /**
     * 上煤后重量
     */
    private Integer cokeWeightAfter;

    /**
     * 上煤前料位
     */
    private Integer cokeMaterialLevelBefor;

    /**
     * 上煤后料位
     */
    private Integer cokeMaterialLevelAfter;

    /**
     * 数据来源(0:手工填报；1:数据采集)
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

