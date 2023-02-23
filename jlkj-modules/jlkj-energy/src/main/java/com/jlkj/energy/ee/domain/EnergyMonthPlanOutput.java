package com.jlkj.energy.ee.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 月计划能耗记录档
 * @TableName energy_ee_month_plan_output
 * @author 265675
 */
@TableName(value ="energy_ee_month_plan_output")
@Data
public class EnergyMonthPlanOutput implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 序号
     */
    private String serialNo;

    /**
     * 工序名称
     */
    private String factoryName;

    /**
     * 作业区
     */
    private String factoryArea;

    /**
     * 月计划产量
     */
    private BigDecimal planValue;

    /**
     * 年月(YYYY-MM)
     */
    private String planyearMonth;

    /**
     * 单位
     */
    private String unit;

    /**
     * 新增时间(YYYY-MM-DD HH:mm:ss)
     */
    private String createDate;

    /**
     * 新增人员
     */
    private String createEmpNo;

    /**
     * 修改时间(YYYY-MM-DD HH:mm:ss)
     */
    private String updateEmpNo;

    /**
     * 修改人员
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

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改人姓名
     */
    private String updateUserName;
    /**
     * 备注
     */
    private String memo;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
