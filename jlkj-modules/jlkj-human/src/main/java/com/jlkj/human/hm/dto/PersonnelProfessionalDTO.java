package com.jlkj.human.hm.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.human.hm.domain.PersonnelProfessional;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 人员职称信息表
 * @TableName human_hm_personnel_professional
 */
@TableName(value ="human_hm_personnel_professional")
@Data
public class PersonnelProfessionalDTO implements Serializable {
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
     * 职称类别
     */
    private String proTypeId;

    /**
     * 职称专业
     */
    private String proSpecId;

    /**
     * 职称级别
     */
    private String proTierId;

    /**
     * 证书编号
     */
    private String cerNo;

    /**
     * 评定日期
     */
    private Date cerDate;

    /**
     * 发证机关
     */
    private String cerComp;

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

    /**
     * 职称信息List
     */
    List<PersonnelProfessional> professionalList;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}