package com.jlkj.finance.ft.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 资产使用状态对象 finance_ft_status
 *
 * @author SunXuTong
 * @date 2023-07-03
 */
public class FinanceFtStatus extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 公司别 */
    private String companyId;

    /** 使用状态代码 */
    @Excel(name = "使用状态代码")
    private String statusCode;

    /** 使用状态名称 */
    @Excel(name = "使用状态名称")
    private String statusName;

    /** 备注 */
    @Excel(name = "备注")
    private String statusRemark;

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
    public void setStatusCode(String statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getStatusCode()
    {
        return statusCode;
    }
    public void setStatusName(String statusName)
    {
        this.statusName = statusName;
    }

    public String getStatusName()
    {
        return statusName;
    }
    public void setStatusRemark(String statusRemark)
    {
        this.statusRemark = statusRemark;
    }

    public String getStatusRemark()
    {
        return statusRemark;
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
            .append("statusCode", getStatusCode())
            .append("statusName", getStatusName())
            .append("statusRemark", getStatusRemark())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
