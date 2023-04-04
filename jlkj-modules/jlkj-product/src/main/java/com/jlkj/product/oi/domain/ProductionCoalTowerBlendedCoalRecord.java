package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 生产实绩-储煤塔配煤记录
 * @TableName product_oi_coal_tower_blended_coal_record
 */
@TableName(value ="product_oi_coal_tower_blended_coal_record")
@Data
public class ProductionCoalTowerBlendedCoalRecord implements Serializable {
    /**
     * 主键标识;UUID
     */
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
    private String coalBlendingPlanId;

    /**
     * 焦炭等级料号
     */
    private String coalLevelMaterialsCode;

    /**
     * 焦炭等级名称
     */
    private String coalLevelMaterialsName;

    /**
     * 储煤塔号
     */
    private Integer coalTowerNumber;

    /**
     * 配煤开始时间
     */
    private Date startTime;

    /**
     * 配煤结束时间
     */
    private Date endTime;

    /**
     * 配煤重量
     */
    private BigDecimal coalWeight;

    /**
     * 配煤前重量
     */
    private BigDecimal coalWeightBefor;

    /**
     * 配煤后重量
     */
    private BigDecimal coalWeightAfter;

    /**
     * 配煤前料位
     */
    private BigDecimal coalMaterialLevelBefor;

    /**
     * 配煤后料位
     */
    private BigDecimal coalMaterialLevelAfter;

    /**
     * 数据来源（0:手工填报；1:数据采集）
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
     * 上班时间
     */
    private Date shiftStartTime;

    /**
     * 下班时间
     */
    private Date shiftEndTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
