package com.jlkj.human.hs.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 薪资核定导入记录表
 * @TableName human_hs_salary_note
 */
@TableName(value ="human_hs_salary_note")
@Data
public class SalaryNote implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 状态
     */
    private String status;

    /**
     * 薪资类别ID
     */
    private String payType;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 年/月
     */
    private String month;

    /**
     * 信息
     */
    private String mark;

    /**
     * 输入人
     */
    private String creator;

    /**
     * 输入人ID
     */
    private String creatorId;

    /**
     * 输入人工号
     */
    private String creatorNo;

    /**
     * 输入日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}