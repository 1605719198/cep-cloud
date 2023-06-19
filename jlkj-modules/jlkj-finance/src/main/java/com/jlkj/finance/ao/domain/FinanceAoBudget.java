package com.jlkj.finance.ao.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 预算设置对象 finance_ao_budget
 *
 * @author jlkj
 * @date 2023-06-16
 */
public class FinanceAoBudget extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String companyId;

    /** 年度 */
    @Excel(name = "年度")
    private String year;

    /** 部门编码 */
    @Excel(name = "部门编码")
    private String deptNo;

    /** 预算类别 */
    @Excel(name = "预算类别")
    private String budgetType;

    /** 年月 */
    private String yarMonth;

    /** 预算值 */
    @Excel(name = "预算值")
    private BigDecimal janBudget;

    /** 状态 */
    private String status;

    /** 新增人姓名 */
    private String createName;

    /** 异动人姓名 */
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
    public void setYear(String year)
    {
        this.year = year;
    }

    public String getYear()
    {
        return year;
    }
    public void setDeptNo(String deptNo)
    {
        this.deptNo = deptNo;
    }

    public String getDeptNo()
    {
        return deptNo;
    }
    public void setBudgetType(String budgetType)
    {
        this.budgetType = budgetType;
    }

    public String getBudgetType()
    {
        return budgetType;
    }
    public void setYarMonth(String yarMonth)
    {
        this.yarMonth = yarMonth;
    }

    public String getYarMonth()
    {
        return yarMonth;
    }
    public void setJanBudget(BigDecimal janBudget)
    {
        this.janBudget = janBudget;
    }

    public BigDecimal getJanBudget()
    {
        return janBudget;
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
            .append("companyId", getCompanyId())
            .append("year", getYear())
            .append("deptNo", getDeptNo())
            .append("budgetType", getBudgetType())
            .append("yarMonth", getYarMonth())
            .append("janBudget", getJanBudget())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("updateName", getUpdateName())
            .toString();
    }
}
