package com.jlkj.energy.ee.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 月计划能耗记录
 * @TableName energy_ee_month_plan_consume
 * @author 265675
 */
@TableName(value ="energy_ee_month_plan_consume")
@Data
public class EnergyMonthPlanConsume implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 能源介质名称
     */
    private String energyName;

    /**
     * 能源介质代码
     */
    private String energyCode;

    /**
     * 类别
     */
    private String types;

    /**
     * 工序名称
     */
    private String factoryName;

    /**
     * 作业区
     */
    private String factoryArea;

    /**
     * 年月(YYYY-MM)
     */
    private String planyearMonth;

    /**
     * 月计划值
     */
    private BigDecimal planValue;

    /**
     * 单位
     */
    private String unit;

    /**
     * 备注
     */
    private String memo;

    /**
     * 日计划公式
     */
    private String memoa;

    /**
     * 行业先进值
     */
    private BigDecimal advancedValue;

    /**
     * 内控指标值
     */
    private BigDecimal lawsValue;

    /**
     * 新增时间(YYYY-MM-DD HH:mm:ss)
     */
    private String createDate;

    /**
     * 新增人员(XXXXX NNNNN)
     */
    private String createEmpNo;

    /**
     * 修改时间(YYYY-MM-DD HH:mm:ss)
     */
    private String updateEmpNo;

    /**
     * 修改人员(XXXXX NNNNN)
     */
    private String updateDate;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
