package com.jlkj.finance.ao.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 还款单资料档对象 finance_ao_repayment
 *
 * @author 116514
 * @date 2023-06-15
 */
public class FinanceAoRepayment extends BaseEntity
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

    /** 借支单id */
    @Excel(name = "借支单id")
    private String loanId;

    /** 还款方式 */
    private String payType;

    /** 借款人 */
    private String loanBy;

    /** 借款人姓名 */
    @Excel(name = "借款人姓名")
    private String loanName;

    /** 本次还款金额 */
    @Excel(name = "本次还款金额")
    private BigDecimal amt;

    /** 还款日期 */
    private Date payDate;

    /** 传票号码 */
    private String voucherNo;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 新增人名称 */
    private String createName;

    /** 异动名称 */
    private String updateName;

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
    public void setLoanId(String loanId)
    {
        this.loanId = loanId;
    }

    public String getLoanId()
    {
        return loanId;
    }
    public void setPayType(String payType)
    {
        this.payType = payType;
    }

    public String getPayType()
    {
        return payType;
    }
    public void setLoanBy(String loanBy)
    {
        this.loanBy = loanBy;
    }

    public String getLoanBy()
    {
        return loanBy;
    }
    public void setLoanName(String loanName)
    {
        this.loanName = loanName;
    }

    public String getLoanName()
    {
        return loanName;
    }
    public void setAmt(BigDecimal amt)
    {
        this.amt = amt;
    }

    public BigDecimal getAmt()
    {
        return amt;
    }
    public void setPayDate(Date payDate)
    {
        this.payDate = payDate;
    }

    public Date getPayDate()
    {
        return payDate;
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
    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }

    public String getUpdateName()
    {
        return updateName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("billNo", getBillNo())
            .append("loanId", getLoanId())
            .append("payType", getPayType())
            .append("loanBy", getLoanBy())
            .append("loanName", getLoanName())
            .append("amt", getAmt())
            .append("payDate", getPayDate())
            .append("voucherNo", getVoucherNo())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
