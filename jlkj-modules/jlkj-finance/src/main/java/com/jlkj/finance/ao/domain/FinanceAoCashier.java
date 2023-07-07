package com.jlkj.finance.ao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 出纳确认主档对象 finance_ao_cashier
 *
 * @author 265799
 * @date 2023-07-05
 */
@Data
public class FinanceAoCashier extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String companyId;

    /** 业务单号 */
    @Excel(name = "业务单号")
    private String billNo;

    /** 业务id */
    @Excel(name = "业务id")
    private String billId;

    /** 出纳 */
    @Excel(name = "出纳")
    private String cashierBy;

    /** 出纳姓名 */
    @Excel(name = "出纳姓名")
    private String cashierName;

    /** 付款方式 */
    @Excel(name = "付款方式")
    private String payType;

    /** 抛帐日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "抛帐日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date postDate;

    /** 传票编号 */
    @Excel(name = "传票编号")
    private String voucherNo;

    /** 确认时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "确认时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date approvalDate;

    /** 批示意见 */
    @Excel(name = "批示意见")
    private String approvalComments;

    /** 批示结论 */
    @Excel(name = "批示结论")
    private String approvalStatus;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 新增人姓名 */
    @Excel(name = "新增人姓名")
    private String createName;

    /** 异动人姓名 */
    @Excel(name = "异动人姓名")
    private String updateName;

    /** 出纳确认明细档信息 */
    private List<FinanceAoAccountCashier> detailAccountLoanList;

    /** 费用报销明细档信息 */
    private List<FinanceAoReimbursementDetail> financeAoReimbursementDetailList;

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
    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setBillNo(String billNo)
    {
        this.billNo = billNo;
    }

    public String getBillNo()
    {
        return billNo;
    }
    public void setBillId(String billId)
    {
        this.billId = billId;
    }

    public String getBillId()
    {
        return billId;
    }
    public void setCashierBy(String cashierBy)
    {
        this.cashierBy = cashierBy;
    }

    public String getCashierBy()
    {
        return cashierBy;
    }
    public void setCashierName(String cashierName)
    {
        this.cashierName = cashierName;
    }

    public String getCashierName()
    {
        return cashierName;
    }
    public void setPayType(String payType)
    {
        this.payType = payType;
    }

    public String getPayType()
    {
        return payType;
    }
    public void setPostDate(Date postDate)
    {
        this.postDate = postDate;
    }

    public Date getPostDate()
    {
        return postDate;
    }
    public void setVoucherNo(String voucherNo)
    {
        this.voucherNo = voucherNo;
    }

    public String getVoucherNo()
    {
        return voucherNo;
    }
    public void setApprovalDate(Date approvalDate)
    {
        this.approvalDate = approvalDate;
    }

    public Date getApprovalDate()
    {
        return approvalDate;
    }
    public void setApprovalComments(String approvalComments)
    {
        this.approvalComments = approvalComments;
    }

    public String getApprovalComments()
    {
        return approvalComments;
    }
    public void setApprovalStatus(String approvalStatus)
    {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalStatus()
    {
        return approvalStatus;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getCreateName()
    {
        return createName;
    }
    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }

    public String getUpdateName()
    {
        return updateName;
    }

    public List<FinanceAoAccountCashier> getDetailAccountLoanList()
    {
        return detailAccountLoanList;
    }

    public void setFinanceAoAccountCashierList(List<FinanceAoAccountCashier> detailAccountLoanList)
    {
        this.detailAccountLoanList = detailAccountLoanList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("billNo", getBillNo())
            .append("billId", getBillId())
            .append("cashierBy", getCashierBy())
            .append("cashierName", getCashierName())
            .append("payType", getPayType())
            .append("postDate", getPostDate())
            .append("voucherNo", getVoucherNo())
            .append("approvalDate", getApprovalDate())
            .append("approvalComments", getApprovalComments())
            .append("approvalStatus", getApprovalStatus())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("updateName", getUpdateName())
            .append("detailAccountLoanList", getDetailAccountLoanList())
            .toString();
    }
}
