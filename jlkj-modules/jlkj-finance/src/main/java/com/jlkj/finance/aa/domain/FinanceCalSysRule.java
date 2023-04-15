package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 核算项目-系统设定对象 finance_aa_cal_sysrule
 *
 * @author 116514
 * @date 2023-04-12
 */
public class FinanceCalSysRule extends BaseEntity
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

    /** 系统别 */
    @Excel(name = "系统别")
    private String sys;

    /** 公用class */
    @Excel(name = "公用class")
    private String className;

    /** 语法字串 */
    @Excel(name = "语法字串")
    private String sqlString;

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
    public void setSys(String sys)
    {
        this.sys = sys;
    }

    public String getSys()
    {
        return sys;
    }
    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getClassName()
    {
        return className;
    }
    public void setSqlString(String sqlString)
    {
        this.sqlString = sqlString;
    }

    public String getSqlString()
    {
        return sqlString;
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
            .append("sys", getSys())
            .append("className", getClassName())
            .append("sqlString", getSqlString())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
