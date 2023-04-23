package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 班别数据对象 human_hd_class
 *
 * @author 266861
 * @date 2023-04-07
 */
public class ShiftClass extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String compId;

    /** 轮班方式ID */
    private String shiftmodeId;

    /** 班别编码 */
    @Excel(name = "班别编码")
    private String classMode;

    /** 班别名称 */
    @Excel(name = "班别名称")
    private String className;

    /** 说明 */
    @Excel(name = "说明")
    private String description;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 是否使用 */
    private String isUsed;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setShiftmodeId(String shiftmodeId)
    {
        this.shiftmodeId = shiftmodeId;
    }

    public String getShiftmodeId()
    {
        return shiftmodeId;
    }
    public void setClassMode(String classMode)
    {
        this.classMode = classMode;
    }

    public String getClassMode()
    {
        return classMode;
    }
    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getClassName()
    {
        return className;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setIsUsed(String isUsed)
    {
        this.isUsed = isUsed;
    }

    public String getIsUsed()
    {
        return isUsed;
    }
    public void setCreator(String creator)
    {
        this.creator = creator;
    }

    public String getCreator()
    {
        return creator;
    }
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }

    public String getCreatorId()
    {
        return creatorId;
    }
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compId", getCompId())
            .append("shiftmodeId", getShiftmodeId())
            .append("classMode", getClassMode())
            .append("className", getClassName())
            .append("description", getDescription())
            .append("status", getStatus())
            .append("isUsed", getIsUsed())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
