package com.jlkj.finance.aa.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

import java.util.List;

/**
 * 现金流量代码对象 finance_aa_cashflow_code
 *
 * @author jlkj
 * @date 2023-04-17
 */
@TableName(value ="finance_aa_cashflow_code")
@Data
public class FinanceAaCashflowCode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /** 会计公司id */
    @Excel(name = "会计公司id")

    @TableField("company_id")
    private String companyId;

    /** 现金流量表代码 */
    @Excel(name = "现金流量表代码")
    @TableField("cash_flow_code")
    private String cashFlowCode;

    /** 现金流量表代码名称 */
    @Excel(name = "现金流量表代码名称")
    @TableField("cash_flow_name")
    private String cashFlowName;

    /** 上层ID */
    @Excel(name = "上层ID")
    @TableField("parent_id")
    private String parentId;

    /** 上层名称 */
    @Excel(name = "上层名称")
    @TableField("parent_name")
    private String parentName;

    /** 上层代码 */
    @Excel(name = "上层代码")
    @TableField("parent_code")
    private String parentCode;

    /** 是否可用 */
    @Excel(name = "是否可用")
    @TableField("isunabl")
    private String isunabl;

    /** 流入流出 */
    @Excel(name = "流入流出")
    @TableField("inorout")
    private String inorout;

    /** 取值方式 */
    @Excel(name = "取值方式")
    @TableField("queDataWay")
    private String quedataway;

    /** 新增人名称 */
    @Excel(name = "新增人名称")
    @TableField("create_name")
    private String createName;

    /** 异动人名称 */
    @Excel(name = "异动人名称")
    @TableField("update_name")
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
