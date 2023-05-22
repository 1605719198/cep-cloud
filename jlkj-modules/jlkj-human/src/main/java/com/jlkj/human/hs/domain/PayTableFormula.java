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
 * 公司薪资表项目公式表
 * @TableName human_hs_pay_table_formula
 */
@TableName(value ="human_hs_pay_table_formula")
@Data
public class PayTableFormula implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String uuid;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 薪酬表ID（human_hs_pay_table表uuid）
     */
    private String payTabId;

    /**
     * 条件
     */
    private String conditions;

    /**
     * 公式
     */
    private String formula;

    /**
     * 计算顺序
     */
    private String seqNo;

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