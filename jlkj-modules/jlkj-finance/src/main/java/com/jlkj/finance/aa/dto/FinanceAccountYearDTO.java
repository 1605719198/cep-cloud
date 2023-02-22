package com.jlkj.finance.aa.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author wang'mai
 */
@Data
public class FinanceAccountYearDTO {
    /**
     * 当前页
     */
    private Long pageNum;
    /**
     * 每页记录数
     */
    private Long pageSize;
    /**
     * 系统码uuid
     */
    @TableId
    private String id;
    /**
     * 会计月份
     */
    private String stMonth;

    /**
     * 会计年度
     */
    private String accountYear;

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
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
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
