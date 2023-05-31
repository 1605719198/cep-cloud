package com.jlkj.finance.aa.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 账薄-会计科目余额对象 finance_aa_ledger_acct
 *
 * @author jlkj
 * @date 2023-04-29
 */
@Data
public class FinanceAaLedgerAcct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 会计公司id */
    @Excel(name = "会计公司id")
    private String companyId;

    /** 会计周期 */
    @Excel(name = "会计周期")
    private String acctPeriod;
    /** 会计周期 */
    @Excel(name = "会计编号")
    private String acctCode;

    /** 会计科目id */
    @Excel(name = "会计科目id")
    private String acctId;

    /** 会计科目中文名称 */
    @Excel(name = "会计科目中文名称")
    private String acctName;

    /** 期初余额 */
    @Excel(name = "期初余额")
    private BigDecimal bgnAmt;

    /** 借方发生额 */
    @Excel(name = "借方发生额")
    private BigDecimal drAmt;

    /** 贷方发生额 */
    @Excel(name = "贷方发生额")
    private BigDecimal crAmt;

    /** 期初余数量 */
    @Excel(name = "期初余数量")
    private BigDecimal bgnQty;

    /** 借方发生数量 */
    @Excel(name = "借方发生数量")
    private BigDecimal drQty;

    /** 贷方发生数量 */
    @Excel(name = "贷方发生数量")
    private BigDecimal crQty;

    /** 本年累计借方发生额 */
    @Excel(name = "本年累计借方发生额")
    private BigDecimal yearDrAmt;

    /** 本年累计贷方发生额 */
    @Excel(name = "本年累计贷方发生额")
    private BigDecimal yearCrAmt;

    /** 本年累计借方发生数量 */
    @Excel(name = "本年累计借方发生数量")
    private BigDecimal yearDrQty;

    /** 本年累计贷方发生数量 */
    @Excel(name = "本年累计贷方发生数量")
    private BigDecimal yearCrQty;

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
    public void setAcctPeriod(String acctPeriod)
    {
        this.acctPeriod = acctPeriod;
    }

    public String getAcctPeriod()
    {
        return acctPeriod;
    }
    public void setAcctId(String acctId)
    {
        this.acctId = acctId;
    }

    public String getAcctId()
    {
        return acctId;
    }
    public void setAcctName(String acctName)
    {
        this.acctName = acctName;
    }

    public String getAcctName()
    {
        return acctName;
    }
    public void setBgnAmt(BigDecimal bgnAmt)
    {
        this.bgnAmt = bgnAmt;
    }

    public BigDecimal getBgnAmt()
    {
        return bgnAmt;
    }
    public void setDrAmt(BigDecimal drAmt)
    {
        this.drAmt = drAmt;
    }

    public BigDecimal getDrAmt()
    {
        return drAmt;
    }
    public void setCrAmt(BigDecimal crAmt)
    {
        this.crAmt = crAmt;
    }

    public BigDecimal getCrAmt()
    {
        return crAmt;
    }
    public void setBgnQty(BigDecimal bgnQty)
    {
        this.bgnQty = bgnQty;
    }

    public BigDecimal getBgnQty()
    {
        return bgnQty;
    }
    public void setDrQty(BigDecimal drQty)
    {
        this.drQty = drQty;
    }

    public BigDecimal getDrQty()
    {
        return drQty;
    }
    public void setCrQty(BigDecimal crQty)
    {
        this.crQty = crQty;
    }

    public BigDecimal getCrQty()
    {
        return crQty;
    }
    public void setYearDrAmt(BigDecimal yearDrAmt)
    {
        this.yearDrAmt = yearDrAmt;
    }

    public BigDecimal getYearDrAmt()
    {
        return yearDrAmt;
    }
    public void setYearCrAmt(BigDecimal yearCrAmt)
    {
        this.yearCrAmt = yearCrAmt;
    }

    public BigDecimal getYearCrAmt()
    {
        return yearCrAmt;
    }
    public void setYearDrQty(BigDecimal yearDrQty)
    {
        this.yearDrQty = yearDrQty;
    }

    public BigDecimal getYearDrQty()
    {
        return yearDrQty;
    }
    public void setYearCrQty(BigDecimal yearCrQty)
    {
        this.yearCrQty = yearCrQty;
    }

    public BigDecimal getYearCrQty()
    {
        return yearCrQty;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("acctPeriod", getAcctPeriod())
            .append("acctId", getAcctId())
            .append("acctName", getAcctName())
            .append("bgnAmt", getBgnAmt())
            .append("drAmt", getDrAmt())
            .append("crAmt", getCrAmt())
            .append("bgnQty", getBgnQty())
            .append("drQty", getDrQty())
            .append("crQty", getCrQty())
            .append("yearDrAmt", getYearDrAmt())
            .append("yearCrAmt", getYearCrAmt())
            .append("yearDrQty", getYearDrQty())
            .append("yearCrQty", getYearCrQty())
            .toString();
    }
}
