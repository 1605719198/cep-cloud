package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 会计系统参数维护对象 finance_aa_para
 *
 * @author 265799
 * @date 2023-04-13
 */
public class FinanceAaPara extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 会计公司id */
    private String companyId;

    /** 参数 */
    @Excel(name = "参数")
    private String parameterKey;

    /** 参数说明 */
    @Excel(name = "参数说明")
    private String parameterDesc;

    /** 参数值 */
    @Excel(name = "参数值")
    private String parameterValue;

    /** 状态 */
    private String status;

    /** 异动名称 */
    private String updateName;

    /** 新增人名称 */
    private String createName;

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
    public void setParameterKey(String parameterKey)
    {
        this.parameterKey = parameterKey;
    }

    public String getParameterKey()
    {
        return parameterKey;
    }
    public void setParameterDesc(String parameterDesc)
    {
        this.parameterDesc = parameterDesc;
    }

    public String getParameterDesc()
    {
        return parameterDesc;
    }
    public void setParameterValue(String parameterValue)
    {
        this.parameterValue = parameterValue;
    }

    public String getParameterValue()
    {
        return parameterValue;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }

    public String getUpdateName()
    {
        return updateName;
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
                .append("parameterKey", getParameterKey())
                .append("parameterDesc", getParameterDesc())
                .append("parameterValue", getParameterValue())
                .append("status", getStatus())
                .append("updateBy", getUpdateBy())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("updateName", getUpdateName())
                .append("createName", getCreateName())
                .toString();
    }
}
