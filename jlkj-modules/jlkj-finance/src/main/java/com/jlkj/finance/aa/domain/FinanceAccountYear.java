package com.jlkj.finance.aa.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 会计周期及传票号码控制档
 * @author wang'mai
 * @TableName t_finance_account_year
 */
@TableName(value ="finance_aa_account_year")
@Data
public class FinanceAccountYear implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 公司代码
     */
    private String compId;

    /**
     * 会计周期
     */
    private String accountPeriod;

    /**
     * 起始日期
     */
    private String startDate;

    /**
     * 终止日期
     */
    private String endDate;

    /**
     * 会计周期结束
     */
    private String isClosed;

    /**
     * 人工转账传票本期代码
     */
    private String manVoucherhrCurrentId;

    /**
     * 机器转账传票本期代码
     */
    private String transVoucherCurrentId;

    /**
     * 支出传票本期代码
     */
    private String payVoucherCurrentId;

    /**
     * 收入转账传票本期代码
     */
    private String receiveVoucherCurrentId;

    /**
     * 本期人工传票序号
     */
    private Long manVoucherhrSrl;

    /**
     * 本期机器传票序号
     */
    private Long transVoucherCurrentSrl;

    /**
     * 本期特殊机器传票序号
     */
    private Long trans3VoucherCurrentSrl;

    /**
     * 本期支出(报支)传票序号
     */
    private Long payVoucherCurrentSrl;

    /**
     * 本期收入传票序号
     */
    private Long receiveVoucherCurrentSrl;

    /**
     * 创建人
     */
    private String creatUser;
    /**
     * 建立时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;
    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 修改人姓名
     */
    private String updateUserName;

    /**
     * 创建人姓名
     */
    private String createUserName;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}