package com.jlkj.finance.AC.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 成本科目基本资料
 * @author wang'mai
 * @TableName t_finance_cost_code
 */
@TableName(value ="t_finance_cost_code")
@Data
public class FinanceCostCode implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 成本科目
     */
    private String costCode;

    /**
     * 成本科目中文说明
     */
    private String costDesc;

    /**
     * 会计科目
     */
    private String accountCode;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * AA抛AC码
     */
    private String assignMark;

    /**
     * 费用来源属性
     */
    private String expenseSource;

    /**
     * 费用性质属性
     */
    private String expenseProperty;

    /**
     * 生管费用科目属性
     */
    private String prodManaCostAttri;

    /**
     * 费用分类属性
     */
    private String expenseType;

    /**
     * 状态
     */
    private String stusCode;

    /**
     * 建立人
     */
    private String createUser;

    /**
     * 建立日期
     */
    private String createDate;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 最后修改日期
     */
    private String updateDate;

    /**
     * 建立时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}