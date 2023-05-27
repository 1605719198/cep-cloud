package com.jlkj.finance.ap.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 付款抛账规则维护对象 finance_ap_accrule
 *
 * @author SunXuTong
 * @date 2023-05-17
 */
public class FinanceApAccrule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司 */
    private String companyId;

    /** 付款方式 */
    @Excel(name = "付款方式")
    private String payMethod;

    /** 付款名称 */
    @Excel(name = "付款名称")
    private String payMethodName;

    /** 凭证类型 */
    @Excel(name = "凭证类型")
    private String voucherType;

    /** 是否生成应付单 */
    @Excel(name = "是否生成应付单")
    private String isGen;

    /** 会计科目 */
    @Excel(name = "会计科目")
    private String acctId;

    /** 会计科目编号  */
    private String acctCode;

    /** 核算项目一类别 */
    @Excel(name = "核算项目一类别")
    private String calTypeIda;

    /** 核算项目一类别编号 */
    private String calTypeCodea;

    /** 核算项目二类别 */
    @Excel(name = "核算项目二类别")
    private String calTypeIdb;

    /** 核算项目二类别编号 */
    private String calTypeCodeb;

    /** 核算项目三类别 */
    private String calTypeIdc;

    /** 核算项目三类别编号 */
    private String calTypeCodec;

    /** 核算项目四类别 */
    private String calTypeIdd;

    /** 核算项目四类别编号 */
    private String calTypeCoded;

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
    public void setPayMethod(String payMethod)
    {
        this.payMethod = payMethod;
    }

    public String getPayMethod()
    {
        return payMethod;
    }
    public void setPayMethodName(String payMethodName)
    {
        this.payMethodName = payMethodName;
    }

    public String getPayMethodName()
    {
        return payMethodName;
    }
    public void setVoucherType(String voucherType)
    {
        this.voucherType = voucherType;
    }

    public String getVoucherType()
    {
        return voucherType;
    }
    public void setIsGen(String isGen)
    {
        this.isGen = isGen;
    }

    public String getIsGen()
    {
        return isGen;
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
    public void setCalTypeIda(String calTypeIda)
    {
        this.calTypeIda = calTypeIda;
    }

    public String getCalTypeIda()
    {
        return calTypeIda;
    }
    public void setCalTypeCodea(String calTypeCodea)
    {
        this.calTypeCodea = calTypeCodea;
    }

    public String getCalTypeCodea()
    {
        return calTypeCodea;
    }
    public void setCalTypeIdb(String calTypeIdb)
    {
        this.calTypeIdb = calTypeIdb;
    }

    public String getCalTypeIdb()
    {
        return calTypeIdb;
    }
    public void setCalTypeCodeb(String calTypeCodeb)
    {
        this.calTypeCodeb = calTypeCodeb;
    }

    public String getCalTypeCodeb()
    {
        return calTypeCodeb;
    }
    public void setCalTypeIdc(String calTypeIdc)
    {
        this.calTypeIdc = calTypeIdc;
    }

    public String getCalTypeIdc()
    {
        return calTypeIdc;
    }
    public void setCalTypeCodec(String calTypeCodec)
    {
        this.calTypeCodec = calTypeCodec;
    }

    public String getCalTypeCodec()
    {
        return calTypeCodec;
    }
    public void setCalTypeIdd(String calTypeIdd)
    {
        this.calTypeIdd = calTypeIdd;
    }

    public String getCalTypeIdd()
    {
        return calTypeIdd;
    }
    public void setCalTypeCoded(String calTypeCoded)
    {
        this.calTypeCoded = calTypeCoded;
    }

    public String getCalTypeCoded()
    {
        return calTypeCoded;
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
            .append("payMethod", getPayMethod())
            .append("payMethodName", getPayMethodName())
            .append("voucherType", getVoucherType())
            .append("isGen", getIsGen())
            .append("acctId", getAcctId())
            .append("acctCode", getAcctCode())
            .append("calTypeIda", getCalTypeIda())
            .append("calTypeCodea", getCalTypeCodea())
            .append("calTypeIdb", getCalTypeIdb())
            .append("calTypeCodeb", getCalTypeCodeb())
            .append("calTypeIdc", getCalTypeIdc())
            .append("calTypeCodec", getCalTypeCodec())
            .append("calTypeIdd", getCalTypeIdd())
            .append("calTypeCoded", getCalTypeCoded())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
