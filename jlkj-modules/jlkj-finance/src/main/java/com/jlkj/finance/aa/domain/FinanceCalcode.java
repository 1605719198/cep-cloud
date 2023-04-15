package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 核算项目-内容维护对象 finance_aa_calcode
 *
 * @author 116514
 * @date 2023-04-10
 */
public class FinanceCalcode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private String id;

    /** 会计公司id */
    private String companyId;

    /** 核算项目类别id */
    private String calTypeId;

    /** 核算项目类别代码 */
    private String calTypeCode;

    /** 核算项目代码 */
    @Excel(name = "核算项目代码")
    private String calCode;

    /** 核算项目名称 */
    @Excel(name = "核算项目名称")
    private String calName;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 新增人名称 */
    private String createName;

    /** 异动人名称 */
    @Excel(name = "异动人名称")
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
    public void setCalTypeId(String calTypeId)
    {
        this.calTypeId = calTypeId;
    }

    public String getCalTypeId()
    {
        return calTypeId;
    }
    public void setCalTypeCode(String calTypeCode)
    {
        this.calTypeCode = calTypeCode;
    }

    public String getCalTypeCode()
    {
        return calTypeCode;
    }
    public void setCalCode(String calCode)
    {
        this.calCode = calCode;
    }

    public String getCalCode()
    {
        return calCode;
    }
    public void setCalName(String calName)
    {
        this.calName = calName;
    }

    public String getCalName()
    {
        return calName;
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
            .append("calTypeId", getCalTypeId())
            .append("calTypeCode", getCalTypeCode())
            .append("calCode", getCalCode())
            .append("calName", getCalName())
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
