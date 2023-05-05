package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 计量单位分组对象 finance_aa_unitGroup
 *
 * @author SunXuTong
 * @date 2023-04-17
 */
public class FinanceAaUnitgroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 分组代号 */
    @Excel(name = "分组代号")
    private String groupNo;

    /** 分组名称 */
    @Excel(name = "分组名称")
    private String groupName;

    /** 序号 */
    @Excel(name = "序号")
    private Long srlno;

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
    public void setGroupNo(String groupNo)
    {
        this.groupNo = groupNo;
    }

    public String getGroupNo()
    {
        return groupNo;
    }
    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getGroupName()
    {
        return groupName;
    }
    public void setSrlno(Long srlno)
    {
        this.srlno = srlno;
    }

    public Long getSrlno()
    {
        return srlno;
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
            .append("groupNo", getGroupNo())
            .append("groupName", getGroupName())
            .append("srlno", getSrlno())
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
