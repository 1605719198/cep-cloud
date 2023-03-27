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
 * 人员奖惩信息表
 * @TableName human_hm_encourage
 */
@TableName(value ="human_hm_encourage")
@Data
public class Encourage implements Serializable {
    /**
     * 主键id
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
     * 姓名
     */
    private String empName;

    /**
     * 工号
     */
    private String empNo;

    /**
     * 岗位id
     */
    private String postId;

    /**
     * 岗位全称
     */
    private String postFullName;

    /**
     * 生效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date effectDate;

    /**
     * 职位等级
     */
    private String postLevel;

    /**
     * 奖惩类别
     */
    private String encourageType;

    /**
     * 奖惩依据
     */
    private String encourageAccording;

    /**
     * 奖惩原因
     */
    private String encourageReason;

    /**
     * 公文文号
     */
    private String docNo;

    /**
     * 备注
     */
    private String remark;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}