package com.jlkj.energy.ee.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 能源介质记录档
 * @TableName energy_ee_tree
 * @author 265675
 */
@TableName(value ="energy_ee_tree")
@Data
public class EnergyTree implements Serializable {
    /**
     * ID
     */
    @TableId
    private String id;

    /**
     * 主档id
     */
    private String pid;

    /**
     * 序号
     */
    private String serialNo;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 类别
     */
    private String types;

    /**
     * 状态
     */
    private String status;

    /**
     * 字段1的值
     */
    private String column1;

    /**
     * 字段2的值
     */
    private String column2;

    /**
     * 字段3的值
     */
    private String column3;

    /**
     * 字段4的值
     */
    private String column4;

    /**
     * 字段5的值
     */
    private String column5;

    /**
     * 能源代码(ERP)
     */
    private String engyId;

    /**
     * 新增人员
     */
    private String createEmp;

    /**
     * 维护人员
     */
    private String updateEmp;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

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
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
