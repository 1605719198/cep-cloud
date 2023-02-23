package com.jlkj.energy.ee.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 能源项目基本资料档
 * @TableName energy_ee_code
 * @author 265675
 */
@TableName(value ="energy_ee_code")
@Data
public class EnergyCode implements Serializable {
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
     * 能源代码
     */
    private String engyId;

    /**
     * 能源名称
     */
    private String engyName;

    /**
     * 能源缩写
     */
    private String engyAc;

    /**
     * 计量单位
     */
    private String engyUnit;

    /**
     * 热值
     */
    private BigDecimal calValue;

    /**
     * 热值单位
     */
    private String calUnit;

    /**
     * 能源类别
     */
    private String engyType;

    /**
     * 来源方式
     */
    private String srcType;

    /**
     * 抛帐对象
     */
    private String acctSys;

    /**
     * 建立人员
     */
    private String createEmpNo;

    /**
     * 建立时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改人员
     */
    private String updateEmpNo;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
