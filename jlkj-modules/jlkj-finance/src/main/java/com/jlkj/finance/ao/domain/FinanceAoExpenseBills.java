package com.jlkj.finance.ao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 我的发票对象 finance_ao_bills
 *
 * @author 116514
 * @date 2023-05-18
 */
public class FinanceAoExpenseBills extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String companyId;

    /** 币别 */
    @Excel(name = "币别")
    private String crcyCode;

    /** 发票介质 */
    @Excel(name = "发票介质")
    private String billMedium;

    /** 发票代码 */
    @Excel(name = "发票代码")
    private String billNo;

    /** 发票号码 */
    @Excel(name = "发票号码")
    private String billName;

    /** 发票类型 */
    @Excel(name = "发票类型")
    private String billType;

    /** 购方名称 */
    @Excel(name = "购方名称")
    private String purchaseName;

    /** 购方纳税人识别号 */
    @Excel(name = "购方纳税人识别号")
    private String purchaseTaxid;

    /** 购方地址电话 */
    @Excel(name = "购方地址电话")
    private String purchaseAddress;

    /** 购方银行账号 */
    @Excel(name = "购方银行账号")
    private String purchaseBank;

    /** 销方名称 */
    @Excel(name = "销方名称")
    private String saleName;

    /** 销方纳税人识别号 */
    @Excel(name = "销方纳税人识别号")
    private String saleTaxid;

    /** 销方地址电话 */
    @Excel(name = "销方地址电话")
    private String saleAddress;

    /** 销方银行账号 */
    @Excel(name = "销方银行账号")
    private String saleBank;

    /** 合计金额 */
    @Excel(name = "合计金额")
    private BigDecimal saleAmt;

    /** 合计税额 */
    @Excel(name = "合计税额")
    private BigDecimal taxAmt;

    /** 价税合计 */
    @Excel(name = "价税合计")
    private BigDecimal totalAmt;

    /** 开票类型 */
    @Excel(name = "开票类型")
    private String plusOrMinus;

    /** 开票人 */
    @Excel(name = "开票人")
    private String billUser;

    /** 开票日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开票日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date billDate;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 关联单号 */
    @Excel(name = "关联单号")
    private String linkNo;

    /** 新增人名称 */
    @Excel(name = "新增人名称")
    private String createName;

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
    public void setCrcyCode(String crcyCode)
    {
        this.crcyCode = crcyCode;
    }

    public String getCrcyCode()
    {
        return crcyCode;
    }
    public void setBillMedium(String billMedium)
    {
        this.billMedium = billMedium;
    }

    public String getBillMedium()
    {
        return billMedium;
    }
    public void setBillNo(String billNo)
    {
        this.billNo = billNo;
    }

    public String getBillNo()
    {
        return billNo;
    }
    public void setBillName(String billName)
    {
        this.billName = billName;
    }

    public String getBillName()
    {
        return billName;
    }
    public void setBillType(String billType)
    {
        this.billType = billType;
    }

    public String getBillType()
    {
        return billType;
    }
    public void setPurchaseName(String purchaseName)
    {
        this.purchaseName = purchaseName;
    }

    public String getPurchaseName()
    {
        return purchaseName;
    }
    public void setPurchaseTaxid(String purchaseTaxid)
    {
        this.purchaseTaxid = purchaseTaxid;
    }

    public String getPurchaseTaxid()
    {
        return purchaseTaxid;
    }
    public void setPurchaseAddress(String purchaseAddress)
    {
        this.purchaseAddress = purchaseAddress;
    }

    public String getPurchaseAddress()
    {
        return purchaseAddress;
    }
    public void setPurchaseBank(String purchaseBank)
    {
        this.purchaseBank = purchaseBank;
    }

    public String getPurchaseBank()
    {
        return purchaseBank;
    }
    public void setSaleName(String saleName)
    {
        this.saleName = saleName;
    }

    public String getSaleName()
    {
        return saleName;
    }
    public void setSaleTaxid(String saleTaxid)
    {
        this.saleTaxid = saleTaxid;
    }

    public String getSaleTaxid()
    {
        return saleTaxid;
    }
    public void setSaleAddress(String saleAddress)
    {
        this.saleAddress = saleAddress;
    }

    public String getSaleAddress()
    {
        return saleAddress;
    }
    public void setSaleBank(String saleBank)
    {
        this.saleBank = saleBank;
    }

    public String getSaleBank()
    {
        return saleBank;
    }
    public void setSaleAmt(BigDecimal saleAmt)
    {
        this.saleAmt = saleAmt;
    }

    public BigDecimal getSaleAmt()
    {
        return saleAmt;
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
    public void setPlusOrMinus(String plusOrMinus)
    {
        this.plusOrMinus = plusOrMinus;
    }

    public String getPlusOrMinus()
    {
        return plusOrMinus;
    }
    public void setBillUser(String billUser)
    {
        this.billUser = billUser;
    }

    public String getBillUser()
    {
        return billUser;
    }
    public void setBillDate(Date billDate)
    {
        this.billDate = billDate;
    }

    public Date getBillDate()
    {
        return billDate;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setLinkNo(String linkNo)
    {
        this.linkNo = linkNo;
    }

    public String getLinkNo()
    {
        return linkNo;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getCreateName()
    {
        return createName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("crcyCode", getCrcyCode())
            .append("billMedium", getBillMedium())
            .append("billNo", getBillNo())
            .append("billName", getBillName())
            .append("billType", getBillType())
            .append("purchaseName", getPurchaseName())
            .append("purchaseTaxid", getPurchaseTaxid())
            .append("purchaseAddress", getPurchaseAddress())
            .append("purchaseBank", getPurchaseBank())
            .append("saleName", getSaleName())
            .append("saleTaxid", getSaleTaxid())
            .append("saleAddress", getSaleAddress())
            .append("saleBank", getSaleBank())
            .append("saleAmt", getSaleAmt())
            .append("taxAmt", getTaxAmt())
            .append("totalAmt", getTotalAmt())
            .append("plusOrMinus", getPlusOrMinus())
            .append("billUser", getBillUser())
            .append("billDate", getBillDate())
            .append("status", getStatus())
            .append("linkNo", getLinkNo())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .toString();
    }
}
