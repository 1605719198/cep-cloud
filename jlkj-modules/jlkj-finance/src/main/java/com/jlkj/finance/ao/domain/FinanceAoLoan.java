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
 * 借支申请主档对象 finance_ao_loan
 *
 * @author 116514
 * @date 2023-06-02
 */
@Data
public class FinanceAoLoan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String companyId;

    /** 申请单号 */
    @Excel(name = "申请单号")
    private String billNo;

    /** 支出类别 */
    @Excel(name = "支出类别")
    private String itemNo;

    /** 期望付款方式 */
    private String payType;

    /** 申请人 */
    @Excel(name = "申请人")
    private String applyBy;

    /** 申请人姓名 */
    private String applyName;

    /** 签核会计 */
    @Excel(name = "签核会计")
    private String accountBy;

    /** 签核会计姓名 */
    private String accountName;

    /** 摘要 */
    private String billDesc;

    /** 到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    /** 会计确认日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date accountDate;

    /** 实际付款日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际付款日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payDate;

    /** 资金调度编号 */
    @Excel(name = "资金调度编号")
    private String arrangeNo;

    /** 传票号码 */
    @Excel(name = "传票号码")
    private String voucherNo;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 新增人名称 */
    @Excel(name = "新增人名称")
    private String createName;

    /** 新增人部门 */
    private String createDept;

    /** 新增人部门名称 */
    @Excel(name = "新增人部门名称")
    private String createDeptName;

    /** 异动名称 */
    private String updateName;

    /**借支总额*/
    private BigDecimal totalAmt;

    /**联动支出类别名称 */
    private String itemName;

    /** 报支管理-借支申请明细档信息 */
    private List<FinanceAoLoanDetail> financeAoLoanDetailList;

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
    public void setItemNo(String itemNo)
    {
        this.itemNo = itemNo;
    }

    public String getItemNo()
    {
        return itemNo;
    }
    public void setPayType(String payType)
    {
        this.payType = payType;
    }

    public String getPayType()
    {
        return payType;
    }
    public void setApplyBy(String applyBy)
    {
        this.applyBy = applyBy;
    }

    public String getApplyBy()
    {
        return applyBy;
    }
    public void setApplyName(String applyName)
    {
        this.applyName = applyName;
    }

    public String getApplyName()
    {
        return applyName;
    }
    public void setAccountBy(String accountBy)
    {
        this.accountBy = accountBy;
    }

    public String getAccountBy()
    {
        return accountBy;
    }
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getAccountName()
    {
        return accountName;
    }
    public void setBillDesc(String billDesc)
    {
        this.billDesc = billDesc;
    }

    public String getBillDesc()
    {
        return billDesc;
    }
    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    public Date getDueDate()
    {
        return dueDate;
    }
    public void setAccountDate(Date accountDate)
    {
        this.accountDate = accountDate;
    }

    public Date getAccountDate()
    {
        return accountDate;
    }
    public void setPayDate(Date payDate)
    {
        this.payDate = payDate;
    }

    public Date getPayDate()
    {
        return payDate;
    }
    public void setArrangeNo(String arrangeNo)
    {
        this.arrangeNo = arrangeNo;
    }

    public String getArrangeNo()
    {
        return arrangeNo;
    }
    public void setVoucherNo(String voucherNo)
    {
        this.voucherNo = voucherNo;
    }

    public String getVoucherNo()
    {
        return voucherNo;
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
    public void setCreateDept(String createDept)
    {
        this.createDept = createDept;
    }

    public String getCreateDept()
    {
        return createDept;
    }
    public void setCreateDeptName(String createDeptName)
    {
        this.createDeptName = createDeptName;
    }

    public String getCreateDeptName()
    {
        return createDeptName;
    }
    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }

    public String getUpdateName()
    {
        return updateName;
    }

    public void setTotalAmt(BigDecimal totalAmt)
    {
        this.totalAmt = totalAmt;
    }
    public BigDecimal getTotalAmt()
    {
        return totalAmt;
    }

    public List<FinanceAoLoanDetail> getFinanceAoLoanDetailList()
    {
        return financeAoLoanDetailList;
    }

    public void setFinanceAoLoanDetailList(List<FinanceAoLoanDetail> financeAoLoanDetailList)
    {
        this.financeAoLoanDetailList = financeAoLoanDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("billNo", getBillNo())
            .append("itemNo", getItemNo())
            .append("payType", getPayType())
            .append("applyBy", getApplyBy())
            .append("applyName", getApplyName())
            .append("accountBy", getAccountBy())
            .append("accountName", getAccountName())
            .append("billDesc", getBillDesc())
            .append("dueDate", getDueDate())
            .append("accountDate", getAccountDate())
            .append("payDate", getPayDate())
            .append("arrangeNo", getArrangeNo())
            .append("voucherNo", getVoucherNo())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("createDept", getCreateDept())
            .append("createDeptName", getCreateDeptName())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .append("financeAoLoanDetailList", getFinanceAoLoanDetailList())
            .toString();
    }
}
