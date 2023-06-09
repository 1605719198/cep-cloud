package com.jlkj.finance.ap.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 报支类别公司细项对象 finance_ap_item_detail_company
 *
 * @author SunXuTong
 * @date 2023-06-08
 */
public class FinanceApItemDetailCompany extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String companyId;

    /** 报支类别编码 */
    private String itemNo;

    /** 细项编码 */
    @Excel(name = "细项编码")
    private String detailNo;

    /** 细项名称 */
    @Excel(name = "细项名称")
    private String detailName;

    /** 费用类别 */
    @Excel(name = "费用类别")
    private String expenseType;

    /** 借方会计科目 */
    @Excel(name = "借方会计科目")
    private String drAcctCode;

    /** 贷方会计科目 */
    @Excel(name = "贷方会计科目")
    private String crAcctCode;

    /** 预算类别 */
    private String budgetType;

    /** 成本科目 */
    private String costCode;

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
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setItemNo(String itemNo)
    {
        this.itemNo = itemNo;
    }

    public String getItemNo()
    {
        return itemNo;
    }
    public void setDetailNo(String detailNo)
    {
        this.detailNo = detailNo;
    }

    public String getDetailNo()
    {
        return detailNo;
    }
    public void setDetailName(String detailName)
    {
        this.detailName = detailName;
    }

    public String getDetailName()
    {
        return detailName;
    }
    public void setExpenseType(String expenseType)
    {
        this.expenseType = expenseType;
    }

    public String getExpenseType()
    {
        return expenseType;
    }
    public void setDrAcctCode(String drAcctCode)
    {
        this.drAcctCode = drAcctCode;
    }

    public String getDrAcctCode()
    {
        return drAcctCode;
    }
    public void setCrAcctCode(String crAcctCode)
    {
        this.crAcctCode = crAcctCode;
    }

    public String getCrAcctCode()
    {
        return crAcctCode;
    }
    public void setBudgetType(String budgetType)
    {
        this.budgetType = budgetType;
    }

    public String getBudgetType()
    {
        return budgetType;
    }
    public void setCostCode(String costCode)
    {
        this.costCode = costCode;
    }

    public String getCostCode()
    {
        return costCode;
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
            .append("itemNo", getItemNo())
            .append("detailNo", getDetailNo())
            .append("detailName", getDetailName())
            .append("expenseType", getExpenseType())
            .append("drAcctCode", getDrAcctCode())
            .append("crAcctCode", getCrAcctCode())
            .append("budgetType", getBudgetType())
            .append("costCode", getCostCode())
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
