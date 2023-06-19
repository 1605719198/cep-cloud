package com.jlkj.finance.ft.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 选单设定对象 finance_ft_dict_type
 *
 * @author SunXuTong
 * @date 2023-06-15
 */
public class FinanceFtDictType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String dictId;

    /** 公司别 */
    private String companyId;

    /** 选单名称 */
    @Excel(name = "选单名称")
    private String dictName;

    /** 选单编码 */
    @Excel(name = "选单编码")
    private String dictType;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 备注 */
    @Excel(name = "备注")
    private String dictRemark;

    /** 新增人名称 */
    private String createName;

    /** 异动名称 */
    private String updateName;

    public void setDictId(String dictId)
    {
        this.dictId = dictId;
    }

    public String getDictId()
    {
        return dictId;
    }
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setDictName(String dictName)
    {
        this.dictName = dictName;
    }

    public String getDictName()
    {
        return dictName;
    }
    public void setDictType(String dictType)
    {
        this.dictType = dictType;
    }

    public String getDictType()
    {
        return dictType;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setDictRemark(String dictRemark)
    {
        this.dictRemark = dictRemark;
    }

    public String getDictRemark()
    {
        return dictRemark;
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
            .append("dictId", getDictId())
            .append("companyId", getCompanyId())
            .append("dictName", getDictName())
            .append("dictType", getDictType())
            .append("status", getStatus())
            .append("dictRemark", getDictRemark())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
