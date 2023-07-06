package com.jlkj.finance.ft.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 账务代码设定对象 finance_ft_acct_type
 *
 * @author SunXuTong
 * @date 2023-06-27
 */
public class FinanceFtAcctType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 公司 */
    private String companyId;

    /** 账务分类代码 */
    @Excel(name = "账务分类代码")
    private String acctType;

    /** 账务分类名称 */
    @Excel(name = "账务分类名称")
    private String acctName;

    /** 备注 */
    @Excel(name = "备注")
    private String acctRemark;

    /** 新增人名称 */
    private String createName;

    /** 异动名称 */
    private String updateName;

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUuid()
    {
        return uuid;
    }
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setAcctType(String acctType)
    {
        this.acctType = acctType;
    }

    public String getAcctType()
    {
        return acctType;
    }
    public void setAcctName(String acctName)
    {
        this.acctName = acctName;
    }

    public String getAcctName()
    {
        return acctName;
    }
    public void setAcctRemark(String acctRemark)
    {
        this.acctRemark = acctRemark;
    }

    public String getAcctRemark()
    {
        return acctRemark;
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
            .append("uuid", getUuid())
            .append("companyId", getCompanyId())
            .append("acctType", getAcctType())
            .append("acctName", getAcctName())
            .append("acctRemark", getAcctRemark())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
