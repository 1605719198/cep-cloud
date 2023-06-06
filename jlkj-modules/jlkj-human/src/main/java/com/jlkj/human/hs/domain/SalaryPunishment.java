package com.jlkj.human.hs.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 专业奖惩金额追补扣
 * @TableName human_hs_salary_punishment
 */
@TableName(value ="human_hs_salary_punishment")
@Data
public class SalaryPunishment implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 年
     */
    private String year;

    /**
     * 月
     */
    private String month;

    /**
     * 公司薪资表项目ID（(human_hs_pay_table的ID）
     */
    private String payTableId;

    /**
     * 薪资表项目编码
     */
    private String payTabCode;

    /**
     * 薪资表项目名称
     */
    private String payTabName;

    /**
     * 金额
     */
    private BigDecimal mon;

    /**
     * 备注
     */
    private String comm;

    /**
     * 薪资类别ID（5-专项奖罚）
     */
    private String payType;

    /**
     * 公司ID
     */
    private String compId;

    /**
     * 员工ID
     */
    private String empId;

    /**
     * 输入人
     */
    private String creator;

    /**
     * 输入日期
     */
    private Date createDate;

    /**
     * 输入人ID
     */
    private String creatorId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}