package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 现金流量代码公司级-对应会计科目对象 finance_aa_cashflow_item_compid
 *
 * @author 265799
 * @date 2023-05-04
 */
public class FinanceAaCashflowItemCompid extends BaseEntity
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

    /** 现金流量表代码id */
    @Excel(name = "现金流量表代码id")
    private String cashFlowId;

    /** 会计科目编号  */
    @Excel(name = "会计科目编号 ")
    private String acctCode;

    /** 加减项 */
    @Excel(name = "加减项")
    private String operator;

    /** 金额取消方式 */
    @Excel(name = "金额取消方式")
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
    public void setCashFlowId(String cashFlowId)
    {
        this.cashFlowId = cashFlowId;
    }

    public String getCashFlowId()
    {
        return cashFlowId;
    }
    public void setAcctCode(String acctCode)
    {
        this.acctCode = acctCode;
    }

    public String getAcctCode()
    {
        return acctCode;
    }
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public String getOperator()
    {
        return operator;
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
            .append("cashFlowId", getCashFlowId())
            .append("acctCode", getAcctCode())
            .append("operator", getOperator())
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
