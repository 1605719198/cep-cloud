package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 往来银行对象 ipms_de_bank_account
 *
 * @author SunXuTong
 * @date 2023-05-22
 */
public class IpmsDeBankAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司 */
    private String compId;

    /** 银行代码 */
    @Excel(name = "银行代码")
    private String bankId;

    /** 银行名称 */
    @Excel(name = "银行名称")
    private String bankName;

    /** 金融机构 */
    private String branchNo;

    /** 账户类别 */
    @Excel(name = "账户类别")
    private String acctType;

    /** 账户名称 */
    @Excel(name = "账户名称")
    private String acctTypeName;

    /** 账号 */
    @Excel(name = "账号")
    private String remitAcctno;

    /** 会计科目id */
    private String acctId;

    /** 会计科目 */
    private String acctCode;

    /** 核算项目一类别id */
    private String calTypeIda;

    /** 核算项目一类别 */
    private String calTypeCodea;

    /** 核算项目二类别id */
    private String calTypeIdb;

    /** 核算项目二类别 */
    private String calTypeCodeb;

    /** 核算项目三类别id */
    private String calTypeIdc;

    /** 核算项目三类别 */
    private String calTypeCodec;

    /** 核算项目四类别id */
    private String calTypeIdd;

    /** 核算项目四类别 */
    private String calTypeCoded;

    /** 联络电话 */
    private String phoneNo;

    /** 联络地址 */
    private String address;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 新增人名称 */
    private String createName;

    /** 异动人名称 */
    private String updateName;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setBankId(String bankId)
    {
        this.bankId = bankId;
    }

    public String getBankId()
    {
        return bankId;
    }
    public void setBankName(String bankName)
    {
        this.bankName = bankName;
    }

    public String getBankName()
    {
        return bankName;
    }
    public void setBranchNo(String branchNo)
    {
        this.branchNo = branchNo;
    }

    public String getBranchNo()
    {
        return branchNo;
    }
    public void setAcctType(String acctType)
    {
        this.acctType = acctType;
    }

    public String getAcctType()
    {
        return acctType;
    }
    public void setAcctTypeName(String acctTypeName)
    {
        this.acctTypeName = acctTypeName;
    }

    public String getAcctTypeName()
    {
        return acctTypeName;
    }
    public void setRemitAcctno(String remitAcctno)
    {
        this.remitAcctno = remitAcctno;
    }

    public String getRemitAcctno()
    {
        return remitAcctno;
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
    public void setPhoneNo(String phoneNo)
    {
        this.phoneNo = phoneNo;
    }

    public String getPhoneNo()
    {
        return phoneNo;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
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
            .append("compId", getCompId())
            .append("bankId", getBankId())
            .append("bankName", getBankName())
            .append("branchNo", getBranchNo())
            .append("acctType", getAcctType())
            .append("acctTypeName", getAcctTypeName())
            .append("remitAcctno", getRemitAcctno())
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
            .append("phoneNo", getPhoneNo())
            .append("address", getAddress())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
