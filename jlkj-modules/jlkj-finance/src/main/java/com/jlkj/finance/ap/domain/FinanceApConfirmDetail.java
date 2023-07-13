package com.jlkj.finance.ap.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 会计确认明细对象 finance_ap_confirm_detail
 *
 * @author 265799
 * @date 2023-07-10
 */
public class FinanceApConfirmDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 应付凭id */
    @Excel(name = "应付凭id")
    private String billId;

    /** 应付凭单号 */
    @Excel(name = "应付凭单号")
    private String billNo;

    /** 公司别 */
    @Excel(name = "公司别")
    private String companyId;

    /** 序号 */
    @Excel(name = "序号")
    private Long seqNo;

    /** 借贷别 */
    @Excel(name = "借贷别")
    private String drCr;

    /** 会计科目id */
    @Excel(name = "会计科目id")
    private String acctId;

    /** 会计科目编号 */
    @Excel(name = "会计科目编号")
    private String acctCode;

    /** 核算项目一id */
    @Excel(name = "核算项目一id")
    private String calIda;

    /** 核算项目一代码 */
    @Excel(name = "核算项目一代码")
    private String calCodea;

    /** 核算项目二id */
    @Excel(name = "核算项目二id")
    private String calIdb;

    /** 核算项目二代码 */
    @Excel(name = "核算项目二代码")
    private String calCodeb;

    /** 核算项目三id */
    @Excel(name = "核算项目三id")
    private String calIdc;

    /** 核算项目三代码 */
    @Excel(name = "核算项目三代码")
    private String calCodec;

    /** 核算项目四id */
    @Excel(name = "核算项目四id")
    private String calIdd;

    /** 核算项目四代码 */
    @Excel(name = "核算项目四代码")
    private String calCoded;

    /** 到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String contractNo;

    /** 合同说明 */
    @Excel(name = "合同说明")
    private String contractName;

    /** 摘要说明 */
    @Excel(name = "摘要说明")
    private String srlDesc;

    /** 收支项目 */
    @Excel(name = "收支项目")
    private String plancode;

    /** 厂商编码id */
    @Excel(name = "厂商编码id")
    private String manufacturerId;

    /** 厂商编码 */
    @Excel(name = "厂商编码")
    private String manufacturerNo;

    /** 币别 */
    @Excel(name = "币别")
    private String crcyUnit;

    /** 原币金额 */
    @Excel(name = "原币金额")
    private BigDecimal frnAmt;

    /** 记帐币金额 */
    @Excel(name = "记帐币金额")
    private BigDecimal amt;

    /** 已付款记账金额 */
    @Excel(name = "已付款记账金额")
    private BigDecimal paidAmt;

    /** 已付款原币金额 */
    @Excel(name = "已付款原币金额")
    private BigDecimal paidFrnAmt;

    /** 已调度记账金额 */
    @Excel(name = "已调度记账金额")
    private BigDecimal dispatchAmt;

    /** 已调度原币金额 */
    @Excel(name = "已调度原币金额")
    private BigDecimal dispatchFrnAmt;

    /** 是否冲预付款项 */
    @Excel(name = "是否冲预付款项")
    private String writeoffLoan;

    /** 是否支付项 */
    @Excel(name = "是否支付项")
    private String payMark;

    public void setBillId(String billId)
    {
        this.billId = billId;
    }

    public String getBillId()
    {
        return billId;
    }
    public void setBillNo(String billNo)
    {
        this.billNo = billNo;
    }

    public String getBillNo()
    {
        return billNo;
    }
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setSeqNo(Long seqNo)
    {
        this.seqNo = seqNo;
    }

    public Long getSeqNo()
    {
        return seqNo;
    }
    public void setDrCr(String drCr)
    {
        this.drCr = drCr;
    }

    public String getDrCr()
    {
        return drCr;
    }
    public void setAcctId(String acctId)
    {
        this.acctId = acctId;
    }

    public String getAcctId()
    {
        return acctId;
    }
    public void setAcctCode(String acctCode)
    {
        this.acctCode = acctCode;
    }

    public String getAcctCode()
    {
        return acctCode;
    }
    public void setCalIda(String calIda)
    {
        this.calIda = calIda;
    }

    public String getCalIda()
    {
        return calIda;
    }
    public void setCalCodea(String calCodea)
    {
        this.calCodea = calCodea;
    }

    public String getCalCodea()
    {
        return calCodea;
    }
    public void setCalIdb(String calIdb)
    {
        this.calIdb = calIdb;
    }

    public String getCalIdb()
    {
        return calIdb;
    }
    public void setCalCodeb(String calCodeb)
    {
        this.calCodeb = calCodeb;
    }

    public String getCalCodeb()
    {
        return calCodeb;
    }
    public void setCalIdc(String calIdc)
    {
        this.calIdc = calIdc;
    }

    public String getCalIdc()
    {
        return calIdc;
    }
    public void setCalCodec(String calCodec)
    {
        this.calCodec = calCodec;
    }

    public String getCalCodec()
    {
        return calCodec;
    }
    public void setCalIdd(String calIdd)
    {
        this.calIdd = calIdd;
    }

    public String getCalIdd()
    {
        return calIdd;
    }
    public void setCalCoded(String calCoded)
    {
        this.calCoded = calCoded;
    }

    public String getCalCoded()
    {
        return calCoded;
    }
    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    public Date getDueDate()
    {
        return dueDate;
    }
    public void setContractNo(String contractNo)
    {
        this.contractNo = contractNo;
    }

    public String getContractNo()
    {
        return contractNo;
    }
    public void setContractName(String contractName)
    {
        this.contractName = contractName;
    }

    public String getContractName()
    {
        return contractName;
    }
    public void setSrlDesc(String srlDesc)
    {
        this.srlDesc = srlDesc;
    }

    public String getSrlDesc()
    {
        return srlDesc;
    }
    public void setPlancode(String plancode)
    {
        this.plancode = plancode;
    }

    public String getPlancode()
    {
        return plancode;
    }
    public void setManufacturerId(String manufacturerId)
    {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerId()
    {
        return manufacturerId;
    }
    public void setManufacturerNo(String manufacturerNo)
    {
        this.manufacturerNo = manufacturerNo;
    }

    public String getManufacturerNo()
    {
        return manufacturerNo;
    }
    public void setCrcyUnit(String crcyUnit)
    {
        this.crcyUnit = crcyUnit;
    }

    public String getCrcyUnit()
    {
        return crcyUnit;
    }
    public void setFrnAmt(BigDecimal frnAmt)
    {
        this.frnAmt = frnAmt;
    }

    public BigDecimal getFrnAmt()
    {
        return frnAmt;
    }
    public void setAmt(BigDecimal amt)
    {
        this.amt = amt;
    }

    public BigDecimal getAmt()
    {
        return amt;
    }
    public void setPaidAmt(BigDecimal paidAmt)
    {
        this.paidAmt = paidAmt;
    }

    public BigDecimal getPaidAmt()
    {
        return paidAmt;
    }
    public void setPaidFrnAmt(BigDecimal paidFrnAmt)
    {
        this.paidFrnAmt = paidFrnAmt;
    }

    public BigDecimal getPaidFrnAmt()
    {
        return paidFrnAmt;
    }
    public void setDispatchAmt(BigDecimal dispatchAmt)
    {
        this.dispatchAmt = dispatchAmt;
    }

    public BigDecimal getDispatchAmt()
    {
        return dispatchAmt;
    }
    public void setDispatchFrnAmt(BigDecimal dispatchFrnAmt)
    {
        this.dispatchFrnAmt = dispatchFrnAmt;
    }

    public BigDecimal getDispatchFrnAmt()
    {
        return dispatchFrnAmt;
    }
    public void setWriteoffLoan(String writeoffLoan)
    {
        this.writeoffLoan = writeoffLoan;
    }

    public String getWriteoffLoan()
    {
        return writeoffLoan;
    }
    public void setPayMark(String payMark)
    {
        this.payMark = payMark;
    }

    public String getPayMark()
    {
        return payMark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("billId", getBillId())
            .append("billNo", getBillNo())
            .append("companyId", getCompanyId())
            .append("seqNo", getSeqNo())
            .append("drCr", getDrCr())
            .append("acctId", getAcctId())
            .append("acctCode", getAcctCode())
            .append("calIda", getCalIda())
            .append("calCodea", getCalCodea())
            .append("calIdb", getCalIdb())
            .append("calCodeb", getCalCodeb())
            .append("calIdc", getCalIdc())
            .append("calCodec", getCalCodec())
            .append("calIdd", getCalIdd())
            .append("calCoded", getCalCoded())
            .append("dueDate", getDueDate())
            .append("contractNo", getContractNo())
            .append("contractName", getContractName())
            .append("srlDesc", getSrlDesc())
            .append("plancode", getPlancode())
            .append("manufacturerId", getManufacturerId())
            .append("manufacturerNo", getManufacturerNo())
            .append("crcyUnit", getCrcyUnit())
            .append("frnAmt", getFrnAmt())
            .append("amt", getAmt())
            .append("paidAmt", getPaidAmt())
            .append("paidFrnAmt", getPaidFrnAmt())
            .append("dispatchAmt", getDispatchAmt())
            .append("dispatchFrnAmt", getDispatchFrnAmt())
            .append("writeoffLoan", getWriteoffLoan())
            .append("payMark", getPayMark())
            .toString();
    }
}
