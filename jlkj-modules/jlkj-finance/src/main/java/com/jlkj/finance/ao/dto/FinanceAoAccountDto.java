package com.jlkj.finance.ao.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import com.jlkj.finance.ao.domain.FinanceAoAccountDetail;
import com.jlkj.finance.ao.domain.FinanceAoLoanDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @version 1.0
 * @className FinanceAoAccountDto
 * @description TDD0
 * @Author: wang'mai
 * @date: 2023/7/3 8:18
 */
@Data
public class FinanceAoAccountDto extends BaseEntity {

    /** 报支管理-借支申请明细档信息 */
    private List<FinanceAoLoanDetail> financeAoLoanDetailList;
    /** 抛账信息 */
    private List<FinanceAoAccountDetail> detailAccountLoanList;

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */

    private String companyId;

    /** 业务单号 */

    private String billNo;

    /** 业务id */

    private String billId;

    /** 签核会计 */

    private String accountBy;

    /** 签核会计姓名 */

    private String accountName;

    /** 期望付款方式 */

    private String payType;

    /** 抛帐日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date postDate;

    /** 确认时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date approvalDate;

    /** 批示意见 */
    private String approvalComments;

    /** 批示结论 */
    private String approvalStatus;
    /** 处理状态 */

    private String  handleStatus;
    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 新增人姓名 */
    private String createName;

    /** 异动人姓名 */
    private String updateName;
    /** 记帐币金额 */
    private BigDecimal amt;
    /** 编号类型 */
    private String  billType;
    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;
    /**
     * 处理状态
     */
    private String processingStatus;


    /** 会计确认单号 */

    private String accountId;



    /** 序号 */

    private Long seqNo;

    /** 借贷别 */

    private String drCr;


    private String acctId;

    /** 会计科目编号 */

    private String acctCode;

    /** 核算项目一id */

    private String calIda;

    /** 核算项目一代码 */

    private String calCodea;

    /** 核算项目二id */

    private String calIdb;

    /** 核算项目二代码 */

    private String calCodeb;

    /** 核算项目三id */

    private String calIdc;

    /** 核算项目三代码 */

    private String calCodec;

    /** 核算项目四id */

    private String calIdd;

    /** 核算项目四代码 */

    private String calCoded;

    /** 到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    /** 摘要说明 */
    private String srlDesc;
    /** 币别 */
    private String crcyUnit;
    /** 原币金额 */
    private BigDecimal frnAmt;

    /** 编码 */
    private String itemNo;
    /**联动会计科目核算项目一类别 */
    private String calTypeCodea;
    /**联动会计科目核算项目二类别 */
    private String calTypeCodeb;

    /** 申请人 */

    private String applyBy;

    /** 申请人姓名 */
    private String applyName;



    /** 摘要 */
    private String billDesc;



    /** 会计确认日期 */
    private Date accountDate;

    /** 实际付款日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date payDate;

    /** 资金调度编号 */
    private String arrangeNo;

    /** 传票号码 */

    private String voucherNo;



    /** 新增人部门 */
    private String createDept;

    /** 新增人部门名称 */
    @Excel(name = "新增人部门名称")
    private String createDeptName;


    /**借支总额*/
    private BigDecimal totalAmt;

    /**联动支出类别名称 */
    private String itemName;



    /** 父id */

    private String parentId;


    /** 费用细项 */

    private String detailNo;

    /** 币别 */

    private String crcyCode;

    /** 汇率 */

    private BigDecimal exchangeRate;

    /** 凭证类型 */

    private String invoiceType;

    /** 税前金额 */

    private BigDecimal netAmt;

    /** 税额 */

    private BigDecimal taxAmt;


    /** 预算项目 */

    private String budgetNo;

    /** 预算项目说明 */

    private String budgetName;


}
