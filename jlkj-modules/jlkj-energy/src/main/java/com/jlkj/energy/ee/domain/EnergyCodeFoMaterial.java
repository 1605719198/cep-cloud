package com.jlkj.energy.ee.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 固、液体能源代码、料号对照
 * @TableName energy_ee_code_fo_material
 * @author 265675
 */
@TableName(value ="energy_ee_code_fo_material")
@Data
public class EnergyCodeFoMaterial implements Serializable {
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
     * 成本中心代号
     */
    private String costCenter;

    /**
     * 能源量类型
     */
    private String engyClass;

    /**
     * 来源系统
     */
    private String engySource;

    /**
     * 料号1
     */
    private String engyCmp1No;

    /**
     * 料号2
     */
    private String engyCmp2No;

    /**
     * 料号3
     */
    private String engyCmp3No;

    /**
     * 料号4
     */
    private String engyCmp4No;

    /**
     * 料号5
     */
    private String engyCmp5No;

    /**
     * 料号6
     */
    private String engyCmp6No;

    /**
     * 料号7
     */
    private String engyCmp7No;

    /**
     * 料号8
     */
    private String engyCmp8No;

    /**
     * 料号9
     */
    private String engyCmp9No;

    /**
     * 料号10
     */
    private String engyCmp10No;

    /**
     * 能源类别
     */
    private String engyType;

    /**
     * 建立人员
     */
    private String createEmpNo;

    /**
     * 建立时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private String createDate;

    /**
     * 修改人员
     */
    private String updateEmpNo;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
