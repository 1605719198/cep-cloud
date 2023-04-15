package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 金融机构管理对象 finance_aa_bank
 *
 * @author SunXuTong
 * @date 2023-04-12
 */
public class FinanceAaBank extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Excel(name = "主键")
    private String id;

    /** 总行代码 */
    @Excel(name = "总行代码")
    private String bankNo;

    /** 总行国别 */
    @Excel(name = "总行国别")
    private String countryId;

    /** 银行型态 */
    @Excel(name = "银行型态")
    private String bankType;

    /** 代码来源 */
    @Excel(name = "代码来源")
    private String banknoSrc;

    /** 总行统一编号 */
    @Excel(name = "总行统一编号")
    private String bankuniformNo;

    /** 总行中文全名 */
    @Excel(name = "总行中文全名")
    private String bankchnName;

    /** 总行英文全名 */
    private String bankengName;

    /** 总行中文简称 */
    @Excel(name = "总行中文简称")
    private String bankChnabbr;

    /** 总行英文简称 */
    private String bankEngabbr;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

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
    public void setBankNo(String bankNo)
    {
        this.bankNo = bankNo;
    }

    public String getBankNo()
    {
        return bankNo;
    }
    public void setCountryId(String countryId)
    {
        this.countryId = countryId;
    }

    public String getCountryId()
    {
        return countryId;
    }
    public void setBankType(String bankType)
    {
        this.bankType = bankType;
    }

    public String getBankType()
    {
        return bankType;
    }
    public void setBanknoSrc(String banknoSrc)
    {
        this.banknoSrc = banknoSrc;
    }

    public String getBanknoSrc()
    {
        return banknoSrc;
    }
    public void setBankuniformNo(String bankuniformNo)
    {
        this.bankuniformNo = bankuniformNo;
    }

    public String getBankuniformNo()
    {
        return bankuniformNo;
    }
    public void setBankchnName(String bankchnName)
    {
        this.bankchnName = bankchnName;
    }

    public String getBankchnName()
    {
        return bankchnName;
    }
    public void setBankengName(String bankengName)
    {
        this.bankengName = bankengName;
    }

    public String getBankengName()
    {
        return bankengName;
    }
    public void setBankChnabbr(String bankChnabbr)
    {
        this.bankChnabbr = bankChnabbr;
    }

    public String getBankChnabbr()
    {
        return bankChnabbr;
    }
    public void setBankEngabbr(String bankEngabbr)
    {
        this.bankEngabbr = bankEngabbr;
    }

    public String getBankEngabbr()
    {
        return bankEngabbr;
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
            .append("bankNo", getBankNo())
            .append("countryId", getCountryId())
            .append("bankType", getBankType())
            .append("banknoSrc", getBanknoSrc())
            .append("bankuniformNo", getBankuniformNo())
            .append("bankchnName", getBankchnName())
            .append("bankengName", getBankengName())
            .append("bankChnabbr", getBankChnabbr())
            .append("bankEngabbr", getBankEngabbr())
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
