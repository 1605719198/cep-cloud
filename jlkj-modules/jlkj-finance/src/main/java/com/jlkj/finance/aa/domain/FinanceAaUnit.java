package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 计量单位维护对象 finance_aa_unit
 *
 * @author SunXuTong
 * @date 2023-04-27
 */
public class FinanceAaUnit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private String unitNo;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 组名 */
    @Excel(name = "组名")
    private String groupId;

    /** 基本计量单位  */
    @Excel(name = "基本计量单位 ")
    private String baseUnitId;

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
    public void setUnitNo(String unitNo)
    {
        this.unitNo = unitNo;
    }

    public String getUnitNo()
    {
        return unitNo;
    }
    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public String getUnitName()
    {
        return unitName;
    }
    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getGroupId()
    {
        return groupId;
    }
    public void setBaseUnitId(String baseUnitId)
    {
        this.baseUnitId = baseUnitId;
    }

    public String getBaseUnitId()
    {
        return baseUnitId;
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
            .append("unitNo", getUnitNo())
            .append("unitName", getUnitName())
            .append("groupId", getGroupId())
            .append("baseUnitId", getBaseUnitId())
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
