package com.jlkj.finance.aa.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 初始化记录对象 finance_aa_init
 *
 * @author 265799
 * @date 2023-05-26
 */
@Data
public class FinanceAaInit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 会计公司id */
    private String companyId;

    /** 会计周期 */
    @Excel(name = "会计周期")
    private String acctPeriod;

    /** 会计科目id */
    private String acctId;

    /** 会计科目编号 */
    @Excel(name = "会计科目编号")
    private String acctCode;

    /** 会计科目中文名称 */
    private String acctName;

    /** 核算项目一id */
    private String calIda;

    /** 核算项目一代码 */
    private String calCodea;

    /** 核算项目一名称 */
    @Excel(name = "核算项目一名称")
    private String calNamea;

    /** 核算项目二id */
    private String calIdb;

    /** 核算项目二代码 */
    private String calCodeb;

    /** 核算项目二名称 */
    @Excel(name = "核算项目二名称")
    private String calNameb;

    /** 核算项目三id */
    private String calIdc;

    /** 核算项目三代码 */
    private String calCodec;

    /** 核算项目三名称 */
    @Excel(name = "核算项目三名称")
    private String calNamec;

    /** 核算项目四id */
    private String calIdd;

    /** 核算项目四代码 */
    private String calCoded;

    /** 核算项目四名称 */
    @Excel(name = "核算项目四名称")
    private String calNamed;

    /** 期初余额 */
    @Excel(name = "期初余额")
    private BigDecimal bgnAmt;

    /** 期初余数量 */
    @Excel(name = "期初余数量")
    private BigDecimal bgnQty;
    /** 错误信息 */
    private String errorMessage;
    /** 新增人姓名 */
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
    public void setAcctCode(String acctCode)
    {
        this.acctCode = acctCode;
    }

    public String getAcctCode()
    {
        return acctCode;
    }
    public void setAcctName(String acctName)
    {
        this.acctName = acctName;
    }

    public String getAcctName()
    {
        return acctName;
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
    public void setCalNamea(String calNamea)
    {
        this.calNamea = calNamea;
    }

    public String getCalNamea()
    {
        return calNamea;
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
    public void setCalNameb(String calNameb)
    {
        this.calNameb = calNameb;
    }

    public String getCalNameb()
    {
        return calNameb;
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
    public void setCalNamec(String calNamec)
    {
        this.calNamec = calNamec;
    }

    public String getCalNamec()
    {
        return calNamec;
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
    public void setCalNamed(String calNamed)
    {
        this.calNamed = calNamed;
    }

    public String getCalNamed()
    {
        return calNamed;
    }
    public void setBgnAmt(BigDecimal bgnAmt)
    {
        this.bgnAmt = bgnAmt;
    }

    public BigDecimal getBgnAmt()
    {
        return bgnAmt;
    }
    public void setBgnQty(BigDecimal bgnQty)
    {
        this.bgnQty = bgnQty;
    }

    public BigDecimal getBgnQty()
    {
        return bgnQty;
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
            .append("acctPeriod", getAcctPeriod())
            .append("acctId", getAcctId())
            .append("acctCode", getAcctCode())
            .append("acctName", getAcctName())
            .append("calIda", getCalIda())
            .append("calCodea", getCalCodea())
            .append("calNamea", getCalNamea())
            .append("calIdb", getCalIdb())
            .append("calCodeb", getCalCodeb())
            .append("calNameb", getCalNameb())
            .append("calIdc", getCalIdc())
            .append("calCodec", getCalCodec())
            .append("calNamec", getCalNamec())
            .append("calIdd", getCalIdd())
            .append("calCoded", getCalCoded())
            .append("calNamed", getCalNamed())
            .append("bgnAmt", getBgnAmt())
            .append("bgnQty", getBgnQty())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("createName", getCreateName())
            .toString();
    }
}
