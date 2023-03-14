package com.jlkj.human.hm.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.human.hm.domain.HumanresourcePersonnelFamily;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 人员家属信息表
 * @TableName human_hm_personnel_family
 */
@TableName(value ="human_hm_personnel_family")
@Data
public class HumanresourcePersonnelFamilyDTO implements Serializable {
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
     * 与本人关系
     */
    private String relationId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 出生日期
     */
    private Date birthDate;

    /**
     * 现居住地
     */
    private String homeAddress;

    /**
     * 户口所在地
     */
    private String registerAddress;

    /**
     * 工作单位
     */
    private String workComp;

    /**
     * 联系电话
     */
    private String tele;

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
     * 家属信息List
     */
    List<HumanresourcePersonnelFamily> familyList;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}