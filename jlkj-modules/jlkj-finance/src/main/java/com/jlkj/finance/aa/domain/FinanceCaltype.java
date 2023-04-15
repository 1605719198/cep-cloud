package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 核算项目-类别维护对象 finance_aa_calType
 *
 * @author 116514
 * @date 2023-04-06
 */
public class FinanceCaltype extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private String id;

    /** 会计公司id */
    @Excel(name = "会计公司id")
    private String companyId;

    /** 核算项目类别代码 */
    @Excel(name = "核算项目类别代码")
    private String calTypeCode;

    /** 核算项目类别名称 */
    @Excel(name = "核算项目类别名称")
    private String calTypeName;

    /** 核算项目规则 */
    @Excel(name = "核算项目规则")
    private String calRule;

    /** 新增人名称 */
    private String createName;

    /** 异动人名称 */
    @Excel(name = "异动人名称")
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
    public void setCalTypeCode(String calTypeCode)
    {
        this.calTypeCode = calTypeCode;
    }

    public String getCalTypeCode()
    {
        return calTypeCode;
    }
    public void setCalTypeName(String calTypeName)
    {
        this.calTypeName = calTypeName;
    }

    public String getCalTypeName()
    {
        return calTypeName;
    }
    public void setCalRule(String calRule)
    {
        this.calRule = calRule;
    }

    public String getCalRule()
    {
        return calRule;
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
            .append("calTypeCode", getCalTypeCode())
            .append("calTypeName", getCalTypeName())
            .append("calRule", getCalRule())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
