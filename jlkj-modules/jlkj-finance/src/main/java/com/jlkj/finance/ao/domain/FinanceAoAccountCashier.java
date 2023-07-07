package com.jlkj.finance.ao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 出纳确认明细档对象 finance_ao_account_cashier
 *
 * @author 265799
 * @date 2023-07-05
 */
@Data
public class FinanceAoAccountCashier extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String companyId;

    /** 出纳确认id */
    @Excel(name = "出纳确认id")
    private String accountId;

    /** 业务单号 */
    @Excel(name = "业务单号")
    private String billNo;

    /** 业务id */
    @Excel(name = "业务id")
    private String billId;

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

    /** 摘要说明 */
    @Excel(name = "摘要说明")
    private String srlDesc;

    /** 币别 */
    @Excel(name = "币别")
    private String crcyUnit;

    /** 原币金额 */
    @Excel(name = "原币金额")
    private BigDecimal frnAmt;

    /** 记帐币金额 */
    @Excel(name = "记帐币金额")
    private BigDecimal amt;

    /** 新增人姓名 */
    @Excel(name = "新增人姓名")
    private String createName;

    /** 异动人姓名 */
    @Excel(name = "异动人姓名")
    private String updateName;
    /** 编码 */
    private String itemNo;
    /**联动会计科目核算项目一类别 */
    private String calTypeCodea;
    /**联动会计科目核算项目二类别 */
    private String calTypeCodeb;
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
    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    public String getAccountId()
    {
        return accountId;
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
    public void setSrlDesc(String srlDesc)
    {
        this.srlDesc = srlDesc;
    }

    public String getSrlDesc()
    {
        return srlDesc;
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
            .append("accountId", getAccountId())
            .append("billNo", getBillNo())
            .append("billId", getBillId())
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
            .append("srlDesc", getSrlDesc())
            .append("crcyUnit", getCrcyUnit())
            .append("frnAmt", getFrnAmt())
            .append("amt", getAmt())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("updateName", getUpdateName())
            .toString();
    }
}
