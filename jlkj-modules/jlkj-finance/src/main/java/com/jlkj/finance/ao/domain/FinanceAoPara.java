package com.jlkj.finance.ao.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 报支参数维护对象 finance_ao_para
 *
 * @author SunXuTong
 * @date 2023-05-06
 */
public class FinanceAoPara extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String companyId;

    /** 参数键名 */
    @Excel(name = "参数键名")
    private String parameterKey;

    /** 参数名称 */
    @Excel(name = "参数名称")
    private String parameterDesc;

    /** 参数键值 */
    @Excel(name = "参数键值")
    private String parameterValue;

    /** 状态 */
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
            .append("parameterKey", getParameterKey())
            .append("parameterDesc", getParameterDesc())
            .append("parameterValue", getParameterValue())
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
