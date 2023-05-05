package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 现金流量代码公司级对象 finance_aa_cashflow_code_compid
 *
 * @author 265799
 * @date 2023-05-04
 */
public class FinanceAaCashflowCodeCompid extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 会计公司id */
    @Excel(name = "会计公司id")
    private String companyId;

    /** 现金流量表代码 */
    @Excel(name = "现金流量表代码")
    private String cashFlowCode;

    /** 现金流量表代码名称 */
    @Excel(name = "现金流量表代码名称")
    private String cashFlowName;

    /** 上层ID */
    @Excel(name = "上层ID")
    private String parentId;

    /** 上层名称 */
    @Excel(name = "上层名称")
    private String parentName;

    /** 上层代码 */
    @Excel(name = "上层代码")
    private String parentCode;

    /** 是否可用 */
    @Excel(name = "是否可用")
    private String isunabl;

    /** 流入流出 */
    @Excel(name = "流入流出" )
    private String inorout;

    /** 取值方式 */
    @Excel(name = "取值方式")
    private String quedataway;

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
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setCashFlowCode(String cashFlowCode)
    {
        this.cashFlowCode = cashFlowCode;
    }

    public String getCashFlowCode()
    {
        return cashFlowCode;
    }
    public void setCashFlowName(String cashFlowName)
    {
        this.cashFlowName = cashFlowName;
    }

    public String getCashFlowName()
    {
        return cashFlowName;
    }
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getParentId()
    {
        return parentId;
    }
    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public String getParentName()
    {
        return parentName;
    }
    public void setParentCode(String parentCode)
    {
        this.parentCode = parentCode;
    }

    public String getParentCode()
    {
        return parentCode;
    }
    public void setIsunabl(String isunabl)
    {
        this.isunabl = isunabl;
    }

    public String getIsunabl()
    {
        return isunabl;
    }
    public void setInorout(String inorout)
    {
        this.inorout = inorout;
    }

    public String getInorout()
    {
        return inorout;
    }
    public void setQuedataway(String quedataway)
    {
        this.quedataway = quedataway;
    }

    public String getQuedataway()
    {
        return quedataway;
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
            .append("cashFlowCode", getCashFlowCode())
            .append("cashFlowName", getCashFlowName())
            .append("parentId", getParentId())
            .append("parentName", getParentName())
            .append("parentCode", getParentCode())
            .append("remark", getRemark())
            .append("isunabl", getIsunabl())
            .append("inorout", getInorout())
            .append("quedataway", getQuedataway())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
