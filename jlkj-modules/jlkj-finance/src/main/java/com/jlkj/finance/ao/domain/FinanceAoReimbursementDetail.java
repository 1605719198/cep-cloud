package com.jlkj.finance.ao.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 费用报销明细档对象 finance_ao_reimbursement_detail
 *
 * @author 265799
 * @date 2023-06-27
 */
public class FinanceAoReimbursementDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 父id */
    @Excel(name = "父id")
    private String parentId;

    /** 序号 */
    @Excel(name = "序号")
    private Long seqNo;

    /** 支出类别 */
    @Excel(name = "支出类别")
    private String itemNo;

    /** 费用细项 */
    @Excel(name = "费用细项")
    private String detailNo;

    /** 凭证类型 */
    @Excel(name = "凭证类型")
    private String invoiceType;

    /** 币别 */
    @Excel(name = "币别")
    private String crcyCode;

    /** 汇率 */
    @Excel(name = "汇率")
    private BigDecimal exchangeRate;

    /** 税前记账金额 */
    @Excel(name = "税前记账金额")
    private BigDecimal netAmt;

    /** 税额 */
    @Excel(name = "税额")
    private BigDecimal taxAmt;

    /** 总额 */
    @Excel(name = "总额")
    private BigDecimal totalAmt;

    /** 预算项目 */
    @Excel(name = "预算项目")
    private String budgetNo;

    /** 预算项目说明 */
    @Excel(name = "预算项目说明")
    private String budgetName;

    /** 摘要说明 */
    @Excel(name = "摘要说明")
    private String srlDesc;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

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
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getParentId()
    {
        return parentId;
    }
    public void setSeqNo(Long seqNo)
    {
        this.seqNo = seqNo;
    }

    public Long getSeqNo()
    {
        return seqNo;
    }
    public void setItemNo(String itemNo)
    {
        this.itemNo = itemNo;
    }

    public String getItemNo()
    {
        return itemNo;
    }
    public void setDetailNo(String detailNo)
    {
        this.detailNo = detailNo;
    }

    public String getDetailNo()
    {
        return detailNo;
    }
    public void setInvoiceType(String invoiceType)
    {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceType()
    {
        return invoiceType;
    }
    public void setCrcyCode(String crcyCode)
    {
        this.crcyCode = crcyCode;
    }

    public String getCrcyCode()
    {
        return crcyCode;
    }
    public void setExchangeRate(BigDecimal exchangeRate)
    {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getExchangeRate()
    {
        return exchangeRate;
    }
    public void setNetAmt(BigDecimal netAmt)
    {
        this.netAmt = netAmt;
    }

    public BigDecimal getNetAmt()
    {
        return netAmt;
    }
    public void setTaxAmt(BigDecimal taxAmt)
    {
        this.taxAmt = taxAmt;
    }

    public BigDecimal getTaxAmt()
    {
        return taxAmt;
    }
    public void setTotalAmt(BigDecimal totalAmt)
    {
        this.totalAmt = totalAmt;
    }

    public BigDecimal getTotalAmt()
    {
        return totalAmt;
    }
    public void setBudgetNo(String budgetNo)
    {
        this.budgetNo = budgetNo;
    }

    public String getBudgetNo()
    {
        return budgetNo;
    }
    public void setBudgetName(String budgetName)
    {
        this.budgetName = budgetName;
    }

    public String getBudgetName()
    {
        return budgetName;
    }
    public void setSrlDesc(String srlDesc)
    {
        this.srlDesc = srlDesc;
    }

    public String getSrlDesc()
    {
        return srlDesc;
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
            .append("parentId", getParentId())
            .append("seqNo", getSeqNo())
            .append("itemNo", getItemNo())
            .append("detailNo", getDetailNo())
            .append("invoiceType", getInvoiceType())
            .append("crcyCode", getCrcyCode())
            .append("exchangeRate", getExchangeRate())
            .append("netAmt", getNetAmt())
            .append("taxAmt", getTaxAmt())
            .append("totalAmt", getTotalAmt())
            .append("budgetNo", getBudgetNo())
            .append("budgetName", getBudgetName())
            .append("srlDesc", getSrlDesc())
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
