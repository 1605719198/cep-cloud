package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 自动结转规则-基本资料-集团对象 finance_aa_carry_base
 *
 * @author 116514
 * @date 2023-04-24
 */
public class FinanceAaCarryBase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;

    /** 会计公司id */
    private String companyId;

    /** 结转类别 */
    private String carry;

    /** 结转代码 */
    @Excel(name = "结转代码")
    private String rulesNo;

    /** 结转名称 */
    @Excel(name = "结转名称")
    private String rulesName;

    /** 凭证类别 */
    @Excel(name = "凭证类别")
    private String voucherType;

    /** 凭证状态 */
    @Excel(name = "凭证状态")
    private String voucherStatus;

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
    public void setRulesName(String rulesName)
    {
        this.rulesName = rulesName;
    }

    public String getRulesName()
    {
        return rulesName;
    }
    public void setVoucherType(String voucherType)
    {
        this.voucherType = voucherType;
    }

    public String getVoucherType()
    {
        return voucherType;
    }
    public void setVoucherStatus(String voucherStatus)
    {
        this.voucherStatus = voucherStatus;
    }

    public String getVoucherStatus()
    {
        return voucherStatus;
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
            .append("carry", getCarry())
            .append("rulesNo", getRulesNo())
            .append("rulesName", getRulesName())
            .append("voucherType", getVoucherType())
            .append("voucherStatus", getVoucherStatus())
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
