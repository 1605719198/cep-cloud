package com.jlkj.finance.ao.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 报支管理-费用报销冲借支单档对象 finance_ao_reimbursement_loan
 *
 * @author 265799
 * @date 2023-06-28
 */
public class FinanceAoReimbursementLoan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 报销单id */
    @Excel(name = "报销单id")
    private String billId;

    /** 费用报销单Id */
    @Excel(name = "费用报销单Id")
    private String reimbursementId;

    /** 借支单id */
    @Excel(name = "借支单id")
    private String loanId;

    /** 本次销账金额 */
    @Excel(name = "本次销账金额")
    private BigDecimal amt;

    /** 新增人名称 */
    @Excel(name = "新增人名称")
    private String createName;

    /** 异动名称 */
    @Excel(name = "异动名称")
    private String updateName;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setBillId(String billId)
    {
        this.billId = billId;
    }

    public String getBillId()
    {
        return billId;
    }
    public void setReimbursementId(String reimbursementId)
    {
        this.reimbursementId = reimbursementId;
    }

    public String getReimbursementId()
    {
        return reimbursementId;
    }
    public void setLoanId(String loanId)
    {
        this.loanId = loanId;
    }

    public String getLoanId()
    {
        return loanId;
    }
    public void setAmt(BigDecimal amt)
    {
        this.amt = amt;
    }

    public BigDecimal getAmt()
    {
        return amt;
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
            .append("billId", getBillId())
            .append("reimbursementId", getReimbursementId())
            .append("loanId", getLoanId())
            .append("amt", getAmt())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
