package com.jlkj.human.hm.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.human.hm.domain.HumanresourcePersonnelWork;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 人员工作经历表
 * @TableName human_hm_personnel_work
 */
@TableName(value ="human_hm_personnel_work")
@Data
public class HumanresourcePersonnelWorkDTO implements Serializable {
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
     * 员工ID
     */
    private String empId;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 截止日期
     */
    private Date endDate;

    /**
     * 单位
     */
    private String comp;

    /**
     * 部门
     */
    private String dept;

    /**
     * 职务（岗位）
     */
    private String post;

    /**
     * 证明人
     */
    private String prover;

    /**
     * 证明人联系方式
     */
    private String tele;

    /**
     * 工作变动原因
     */
    private String changeReasonId;

    /**
     * 输入类型
     */
    private String inputType;

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
     * 工作经历List
     */
    List<HumanresourcePersonnelWork> workExperienceList;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}