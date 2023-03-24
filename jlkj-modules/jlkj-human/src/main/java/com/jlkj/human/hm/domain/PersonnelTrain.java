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
 * 人员培训经历表
 * @author huangbing
 * @TableName human_hm_personnel_train
 */
@TableName(value ="human_hm_personnel_train")
@Data
public class PersonnelTrain implements Serializable {
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
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;

    /**
     * 授课单位
     */
    private String trainComp;

    /**
     * 类别（内容）
     */
    private String content;

    /**
     * 项目
     */
    private String project;

    /**
     * 获得资格
     */
    private String qualification;

    /**
     * 获得证书
     */
    private String certificate;

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