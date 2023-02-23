package com.jlkj.energy.ee.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 产线代码对应成本中心资料
 * @TableName energy_ee_mill_of_cost
 * @author 265675
 */
@TableName(value ="energy_ee_mill_of_cost")
@Data
public class EnergyMillOfCost implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 公司别
     */
    @TableField(fill = FieldFill.INSERT)
    private String compId;

    /**
     * 工场产线代号
     */
    private String millIdCode;

    /**
     * 工场产线中文名称
     */
    private String millIdName;

    /**
     * 成本中心代号
     */
    private String costCenter;

    /**
     * 建立人员
     */
    private String createEmpNo;

    /**
     * 建立日期
     */
    private String createDate;

    /**
     * 修改人员
     */
    private String updateEmpNo;

    /**
     * 修改日期
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
