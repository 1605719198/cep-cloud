package com.jlkj.human.hm.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 人员职业资格表
 * @TableName human_hm_personnel_occupation
 */
@TableName(value ="human_hm_personnel_occupation")
@Data
public class PersonnelOccupation implements Serializable {
    /**
     * uuid 主键ID
     */
    @TableId
    private String uuid;

    /**
     * 公司
     */
    private String compId;

    /**
     * 员工ID（人员基础信息表uuid）
     */
    private String empId;

    /**
     * 职业证书编号
     */
    private String vocCerNo;

    /**
     * 职业类别
     */
    private String vocType;

    /**
     * 名称
     */
    private String name;

    /**
     * 证书等级
     */
    private String cerTier;

    /**
     * 发证日期
     */
    private Date cerDate;

    /**
     * 发证机关
     */
    private String cerComp;

    /**
     * 是否公司内
     */
    private String ifInComp;

    /**
     * 更新人员
     */
    private String creator;

    /**
     * 更新人员ID
     */
    private String creatorId;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 备用字段1
     */
    private String alternateField1;

    /**
     * 备用字段2
     */
    private String alternateField2;

    /**
     * 备用字段3
     */
    private String alternateField3;

    /**
     * 备用字段4
     */
    private String alternateField4;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}