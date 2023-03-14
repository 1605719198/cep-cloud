package com.jlkj.human.hm.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.human.hm.domain.HumanresourcePersonnelEducation;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 人员教育经历表
 * @TableName human_hm_personnel_education
 */
@TableName(value ="human_hm_personnel_education")
@Data
public class HumanresourcePersonnelEducationDTO implements Serializable {
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
     * 入学日期
     */
    private Date startDate;

    /**
     * 毕业日期
     */
    private Date endDate;

    /**
     * 学校
     */
    private String school;

    /**
     * 专业
     */
    private String specId;

    /**
     * 专业细分
     */
    private String psubSpecId;

    /**
     * 培养方式
     */
    private String trainStypeId;

    /**
     * 学历
     */
    private String experId;

    /**
     * 学历证书编号
     */
    private String experNo;

    /**
     * 学位
     */
    private String degreeId;

    /**
     * 学位证书编号
     */
    private String degreeNo;

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

    /**
     * 教育经历List
     */
    List<HumanresourcePersonnelEducation> educationalExperienceList;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}