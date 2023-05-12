package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 总账管理-自动结转规则-规则设置对象 finance_aa_company_carry_rules
 *
 * @author 116514
 * @date 2023-04-25
 */
public class FinanceAaCarryRulesCorp extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 会计公司id */
    private String companyId;

    /** 结转ID */
    private String rulesId;

    /** 结转代码 */
    private String rulesNo;

    /** 转出会计科目ID */
    private String accountIdOut;

    /** 转出会计科目编码 */
    @Excel(name = "转出会计科目编码")
    private String accountCodeOut;

    /** 结转方式 */
    @Excel(name = "结转方式")
    private String carryType;

    /** 转让入会计科目ID */
    private String accountIdIn;

    /** 转让入会计科目编码 */
    @Excel(name = "转让入会计科目编码")
    private String accountCodeIn;

    /** 结转方向 */
    @Excel(name = "结转方向")
    private String drcr;

    /** 新增人名称 */
    private String createName;

    /** 异动人名称 */
    private String  updateName;

    /** 转出会计科目名称 */
    private String acctNameOut;
    /** 转入会计科目名称 */
    private String acctNameIn;


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

    public void setRulesId(String rulesId)
    {
        this.rulesId = rulesId;
    }
    public String getRulesId()
    {
        return rulesId;
    }

    public void setRulesNo(String rulesNo)
    {
        this.rulesNo = rulesNo;
    }

    public String getRulesNo()
    {
        return rulesNo;
    }
    public void setAccountIdOut(String accountIdOut)
    {
        this.accountIdOut = accountIdOut;
    }

    public String getAccountIdOut()
    {
        return accountIdOut;
    }
    public void setAccountCodeOut(String accountCodeOut)
    {
        this.accountCodeOut = accountCodeOut;
    }

    public String getAccountCodeOut()
    {
        return accountCodeOut;
    }
    public void setCarryType(String carryType)
    {
        this.carryType = carryType;
    }

    public String getCarryType()
    {
        return carryType;
    }
    public void setAccountIdIn(String accountIdIn)
    {
        this.accountIdIn = accountIdIn;
    }

    public String getAccountIdIn()
    {
        return accountIdIn;
    }
    public void setAccountCodeIn(String accountCodeIn)
    {
        this.accountCodeIn = accountCodeIn;
    }

    public String getAccountCodeIn()
    {
        return accountCodeIn;
    }
    public void setDrcr(String drcr)
    {
        this.drcr = drcr;
    }

    public String getDrcr()
    {
        return drcr;
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

    public String getAcctNameOut() { return acctNameOut;  }

    public void setAcctNameOut(String acctNameOut) { this.acctNameOut = acctNameOut; }

    public String getAcctNameIn() { return acctNameIn; }

    public void setAcctNameIn(String acctNameIn) { this.acctNameIn = acctNameIn; }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("rulesId", getRulesId())
            .append("rulesNo", getRulesNo())
            .append("accountIdOut", getAccountIdOut())
            .append("accountCodeOut", getAccountCodeOut())
            .append("carryType", getCarryType())
            .append("accountIdIn", getAccountIdIn())
            .append("accountCodeIn", getAccountCodeIn())
            .append("drcr", getDrcr())
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
