package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 币别管理对象 finance_aa_crcy
 *
 * @author SunXuTong
 * @date 2023-04-07
 */
public class FinanceAaCrcy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 货币代码 */
    @Excel(name = "货币代码")
    private String crcyCode;

    /** 货币简称 */
    private String crcyAbbr;

    /** 货币英文全名 */
    @Excel(name = "货币英文全名")
    private String crcyEngName;

    /** 货币中文名称 */
    @Excel(name = "货币中文名称")
    private String crcyLocalName;

    /** 货币符号 */
    @Excel(name = "货币符号")
    private String crcySymbol;

    /** 图档 */
    @Excel(name = "图档")
    private String crcyPath;

    /** 默认货币 */
    @Excel(name = "默认货币")
    private String defaultCrcy;

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
    public void setCrcyCode(String crcyCode)
    {
        this.crcyCode = crcyCode;
    }

    public String getCrcyCode()
    {
        return crcyCode;
    }
    public void setCrcyAbbr(String crcyAbbr)
    {
        this.crcyAbbr = crcyAbbr;
    }

    public String getCrcyAbbr()
    {
        return crcyAbbr;
    }
    public void setCrcyEngName(String crcyEngName)
    {
        this.crcyEngName = crcyEngName;
    }

    public String getCrcyEngName()
    {
        return crcyEngName;
    }
    public void setCrcyLocalName(String crcyLocalName)
    {
        this.crcyLocalName = crcyLocalName;
    }

    public String getCrcyLocalName()
    {
        return crcyLocalName;
    }
    public void setCrcySymbol(String crcySymbol)
    {
        this.crcySymbol = crcySymbol;
    }

    public String getCrcySymbol()
    {
        return crcySymbol;
    }
    public void setCrcyPath(String crcyPath)
    {
        this.crcyPath = crcyPath;
    }

    public String getCrcyPath()
    {
        return crcyPath;
    }
    public void setDefaultCrcy(String defaultCrcy)
    {
        this.defaultCrcy = defaultCrcy;
    }

    public String getDefaultCrcy()
    {
        return defaultCrcy;
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
            .append("crcyCode", getCrcyCode())
            .append("crcyAbbr", getCrcyAbbr())
            .append("crcyEngName", getCrcyEngName())
            .append("crcyLocalName", getCrcyLocalName())
            .append("crcySymbol", getCrcySymbol())
            .append("crcyPath", getCrcyPath())
            .append("defaultCrcy", getDefaultCrcy())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
