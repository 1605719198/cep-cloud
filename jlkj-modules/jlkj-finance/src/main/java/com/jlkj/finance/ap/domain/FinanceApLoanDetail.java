package com.jlkj.finance.ap.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 预付申请明细档对象 finance_ap_loan_detail
 *
 * @author 265799
 * @date 2023-07-10
 */
@Data
public class FinanceApLoanDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /** 父id */
    @Excel(name = "父id")
    private String parentId;

    /** 支出类别 */
    @Excel(name = "支出类别")
    private String itemNo;

    /** 费用细项 */
    @Excel(name = "费用细项")
    @NotNull(message = "detailNo为必须项")
    private String detailNo;

    /** 汇率 */
    @Excel(name = "汇率")
    private BigDecimal exchangeRate;

    /** 凭证类型 */
    @Excel(name = "凭证类型")
    @NotNull(message = "invoiceType为必须项")
    private String invoiceType;

    /** 税前金额 */
    @Excel(name = "税前金额")
    private BigDecimal netAmt;

    /** 税额 */
    @Excel(name = "税额")
    private BigDecimal taxAmt;

    /** 税后金额 */
    @Excel(name = "税后金额")
    @NotNull(message = "totalAmt为必须项")
    private BigDecimal totalAmt;

    /** 合同号码 */
    @Excel(name = "合同号码")
    @NotNull(message = "contract为必须项")
    private String contract;

    /** 预算项目 */
    @Excel(name = "预算项目")
    @NotNull(message = "budget为必须项")
    private String budget;

    /** 摘要说明 */
    @Excel(name = "摘要说明")
    @NotNull(message = "srlDesc为必须项")
    private String srlDesc;

    /** 状态 */
    @Excel(name = "状态")
    private String status;
    /** 记账币金额 */
    @Excel(name = "记账币金额")
    private BigDecimal amt;
    /** 会计确认日期 */
    private Date accountDate;
    /** 付款凭证号 */
    private String payVoucherNo;
    /** 付款日期 */
    private Date payVoucherDate;

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
    public void setExchangeRate(BigDecimal exchangeRate)
    {
        this.exchangeRate = exchangeRate;
    }

    public BigDecimal getExchangeRate()
    {
        return exchangeRate;
    }
    public void setInvoiceType(String invoiceType)
    {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceType()
    {
        return invoiceType;
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
    public void setContract(String contract)
    {
        this.contract = contract;
    }

    public String getContract()
    {
        return contract;
    }
    public void setBudget(String budget)
    {
        this.budget = budget;
    }

    public String getBudget()
    {
        return budget;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("itemNo", getItemNo())
            .append("detailNo", getDetailNo())
            .append("exchangeRate", getExchangeRate())
            .append("invoiceType", getInvoiceType())
            .append("netAmt", getNetAmt())
            .append("taxAmt", getTaxAmt())
            .append("totalAmt", getTotalAmt())
            .append("contract", getContract())
            .append("budget", getBudget())
            .append("srlDesc", getSrlDesc())
            .append("status", getStatus())
            .toString();
    }
}
