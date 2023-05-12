package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 资产大类维护对象 finance_aa_assetCategory
 *
 * @author SunXuTong
 * @date 2023-05-09
 */
public class FinanceAaAssetcategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 资产大类代码 */
    @Excel(name = "资产大类代码")
    private String assetGroup;

    /** 资产大类名称 */
    @Excel(name = "资产大类名称")
    private String assetGroupName;

    /** 资产类别 */
    @Excel(name = "资产类别")
    private String assetCategory;

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
    public void setAssetGroup(String assetGroup)
    {
        this.assetGroup = assetGroup;
    }

    public String getAssetGroup()
    {
        return assetGroup;
    }
    public void setAssetGroupName(String assetGroupName)
    {
        this.assetGroupName = assetGroupName;
    }

    public String getAssetGroupName()
    {
        return assetGroupName;
    }
    public void setAssetCategory(String assetCategory)
    {
        this.assetCategory = assetCategory;
    }

    public String getAssetCategory()
    {
        return assetCategory;
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
            .append("assetGroup", getAssetGroup())
            .append("assetGroupName", getAssetGroupName())
            .append("assetCategory", getAssetCategory())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
