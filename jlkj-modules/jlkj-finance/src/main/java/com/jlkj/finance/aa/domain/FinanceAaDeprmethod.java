package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 折旧方法维护对象 finance_aa_deprMethod
 *
 * @author SunXuTong
 * @date 2023-05-10
 */
public class FinanceAaDeprmethod extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 折旧方法代码 */
    @Excel(name = "折旧方法代码")
    private String deprNo;

    /** 折旧方法名称 */
    @Excel(name = "折旧方法名称")
    private String deprName;

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
    public void setDeprNo(String deprNo)
    {
        this.deprNo = deprNo;
    }

    public String getDeprNo()
    {
        return deprNo;
    }
    public void setDeprName(String deprName)
    {
        this.deprName = deprName;
    }

    public String getDeprName()
    {
        return deprName;
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
            .append("deprNo", getDeprNo())
            .append("deprName", getDeprName())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
