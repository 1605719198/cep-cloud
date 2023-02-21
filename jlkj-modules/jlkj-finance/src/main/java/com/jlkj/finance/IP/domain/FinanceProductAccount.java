package com.jlkj.finance.IP.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 产副品帐务科目档
 * @author wang'mai
 * @TableName t_finance_product_account
 */
@TableName(value ="t_finance_product_account")
@Data
public class FinanceProductAccount implements Serializable {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 存货特性
     */
    private String productInventoryNature;

    /**
     * 产副品编码
     */
    private String productCode;

    /**
     * 帐务名称
     */
    private String accountName;

    /**
     * 成本科目
     */
    private String costCode;

    /**
     * 会计科目
     */
    private String accountCode;

    /**
     * 会计科目户号指示
     */
    private String accountIdIdex;

    /**
     * 会计科目参号指示
     */
    private String accountRefnoIndex;

    /**
     * 建立人
     */
    private String createUser;

    /**
     * 建立日期
     */
    private String createDate;

    /**
     * 异动人
     */
    private String updateUser;

    /**
     * 异动日期
     */
    private String updateDate;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 建立日期
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}