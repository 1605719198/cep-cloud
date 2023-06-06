package com.jlkj.finance.ao.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 报支类别公司设定明细档对象 finance_ao_item_comp_detail
 *
 * @author 116514
 * @date 2023-05-30
 */
public class FinanceAoItemCompDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String companyId;

    /** 报支类别编码 */
    @Excel(name = "报支类别编码")
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
    @Excel(name = "预算类别")
    private String budgetType;

    /** 成本科目 */
    @Excel(name = "成本科目")
    private String costCode;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 其他类别 */
    @Excel(name = "其他类别")
    private String otherType;

    /** 备注 */
    private String otherDesc;

    /** 新增人名称 */
    private String createName;

    /** 异动名称 */
    private String updateName;

    /** 借方科目名称 */
    private String drAcctName;
    /** 借方核算项目一名称 */
    private String drCalTypeName1;
    /** 借方核算项目二名称 */
    private String drCalTypeName2;
    /** 贷方科目名称 */
    private String crAcctName;
    /** 贷方核算项目一名称 */
    private String crCalTypeName1;
    /** 贷方核算项目二名称 */
    private String crCalTypeName2;


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
    public void setOtherType(String otherType)
    {
        this.otherType = otherType;
    }

    public String getOtherType()
    {
        return otherType;
    }
    public void setOtherDesc(String otherDesc)
    {
        this.otherDesc = otherDesc;
    }

    public String getOtherDesc()
    {
        return otherDesc;
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
    public String getDrAcctName() {
        return drAcctName;
    }

    public void setDrAcctName(String drAcctName) {
        this.drAcctName = drAcctName;
    }

    public String getDrCalTypeName1() {
        return drCalTypeName1;
    }

    public void setDrCalTypeName1(String drCalTypeName1) {
        this.drCalTypeName1 = drCalTypeName1;
    }

    public String getDrCalTypeName2() {
        return drCalTypeName2;
    }

    public void setDrCalTypeName2(String drCalTypeName2) {
        this.drCalTypeName2 = drCalTypeName2;
    }

    public String getCrAcctName() {
        return crAcctName;
    }

    public void setCrAcctName(String crAcctName) {
        this.crAcctName = crAcctName;
    }

    public String getCrCalTypeName1() {
        return crCalTypeName1;
    }

    public void setCrCalTypeName1(String crCalTypeName1) {
        this.crCalTypeName1 = crCalTypeName1;
    }

    public String getCrCalTypeName2() {
        return crCalTypeName2;
    }

    public void setCrCalTypeName2(String crCalTypeName2) {
        this.crCalTypeName2 = crCalTypeName2;
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
            .append("otherType", getOtherType())
            .append("otherDesc", getOtherDesc())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
