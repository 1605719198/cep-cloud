package com.jlkj.human.hs.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 薪资项目金额追补扣
 * @TableName human_hs_salary_add
 */
@TableName(value ="human_hs_salary_add")
@Data
public class SalaryAdd implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 公司薪资表项目ID（human_hs_pay_table的ID）
     */
    private String payTableId;

    /**
     * 备注
     */
    private String comm;

    /**
     * 公司ID
     */
    private String compId;

    /**
     * 员工ID
     */
    @Excel(name = "工号*", cellType = Excel.ColumnType.NUMERIC, type = Excel.Type.IMPORT)
    private String empId;

    /**
     * 薪资类别ID
     */
    @Excel(name = "薪资类别", type = Excel.Type.IMPORT)
    private String payType;

    /**
     * 年
     */
    @Excel(name = "年", type = Excel.Type.IMPORT)
    private String year;

    /**
     * 月
     */
    @Excel(name = "月", type = Excel.Type.IMPORT)
    private String month;

    /**
     * 薪资表项目编码
     */
    @Excel(name = "薪资表项目编码*", type = Excel.Type.IMPORT)
    private String payTabCode;

    /**
     * 薪资表项目名称
     */
    @Excel(name = "薪资表项目名称", type = Excel.Type.IMPORT)
    private String payTabName;

    /**
     * 金额
     */
    @Excel(name = "金额*", type = Excel.Type.IMPORT)
    private BigDecimal mon;

    /**
     * 输入人
     */
    private String creator;

    /**
     * 输入人ID
     */
    private String creatorId;

    /**
     * 输入日期
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}