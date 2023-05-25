package com.jlkj.finance.aa.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 自动结转规则-执行记录对象 finance_aa_carry_record
 *
 * @author 265799
 * @date 2023-05-19
 */
@Data
public class FinanceAaCarryRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 会计公司id */
    @Excel(name = "会计公司id")
    private String companyId;

    /** 结转类别 */
    @Excel(name = "结转类别")
    private String carry;

    /** 结转代码 */
    @Excel(name = "结转代码")
    private String rulesNo;
    /** 结转代码 */

    private String rulesName;

    /** 会计周期 */
    @Excel(name = "会计周期")
    private String acctPeriod;

    /** 执行凭证号码 */
    @Excel(name = "执行凭证号码")
    private String executeVoucherNo;

    /** 取消凭证号码 */
    @Excel(name = "取消凭证号码")
    private String cancelVoucherNo;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 执行人姓名 */
    private String createName;

    /** 状态 */

    private String statusA;
    /** 备注 */
    private String remark;

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
    public void setCarry(String carry)
    {
        this.carry = carry;
    }

    public String getCarry()
    {
        return carry;
    }
    public void setRulesNo(String rulesNo)
    {
        this.rulesNo = rulesNo;
    }

    public String getRulesNo()
    {
        return rulesNo;
    }
    public void setAcctPeriod(String acctPeriod)
    {
        this.acctPeriod = acctPeriod;
    }

    public String getAcctPeriod()
    {
        return acctPeriod;
    }
    public void setExecuteVoucherNo(String executeVoucherNo)
    {
        this.executeVoucherNo = executeVoucherNo;
    }

    public String getExecuteVoucherNo()
    {
        return executeVoucherNo;
    }
    public void setCancelVoucherNo(String cancelVoucherNo)
    {
        this.cancelVoucherNo = cancelVoucherNo;
    }

    public String getCancelVoucherNo()
    {
        return cancelVoucherNo;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("carry", getCarry())
            .append("rulesNo", getRulesNo())
            .append("acctPeriod", getAcctPeriod())
            .append("executeVoucherNo", getExecuteVoucherNo())
            .append("cancelVoucherNo", getCancelVoucherNo())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("createName", getCreateName())
            .toString();
    }
}
