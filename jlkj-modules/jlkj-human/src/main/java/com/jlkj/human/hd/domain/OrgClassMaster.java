package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 出勤身份对象 human_hd_org_class_master
 *
 * @author 266861
 * @date 2023-04-12
 */

public class OrgClassMaster extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String compId;

    /** 组织机构id */
    @Excel(name = "组织机构id")
    private String deptId;

    /** 组织机构姓名 */
    @Excel(name = "组织机构姓名")
    private String deptName;

    /** 一级单位名称 */
    private String firstDeptName;

    /** 轮班方式ID */
    @Excel(name = "轮班方式ID")
    private String shiftmodeId;

    /** 轮班方式名称 */
    @Excel(name = "轮班方式名称")
    private String shiftmodeName;

    /** 班别ID */
    @Excel(name = "班别ID")
    private String classId;

    /** 班别名称 */
    @Excel(name = "班别名称")
    private String className;

    /** 排班开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "排班开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 排班结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "排班结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

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
    public void setDeptId(String deptId)
    {
        this.deptId = deptId;
    }

    public String getDeptId()
    {
        return deptId;
    }
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getDeptName()
    {
        return deptName;
    }
    public void setFirstDeptName(String firstDeptName)
    {
        this.firstDeptName = firstDeptName;
    }

    public String getFirstDeptName()
    {
        return firstDeptName;
    }
    public void setShiftmodeId(String shiftmodeId)
    {
        this.shiftmodeId = shiftmodeId;
    }

    public String getShiftmodeId()
    {
        return shiftmodeId;
    }
    public void setShiftmodeName(String shiftmodeName)
    {
        this.shiftmodeName = shiftmodeName;
    }

    public String getShiftmodeName()
    {
        return shiftmodeName;
    }
    public void setClassId(String classId)
    {
        this.classId = classId;
    }

    public String getClassId()
    {
        return classId;
    }
    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getClassName()
    {
        return className;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
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
            .append("deptId", getDeptId())
            .append("shiftmodeId", getShiftmodeId())
            .append("classId", getClassId())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("remark", getRemark())
            .append("status", getStatus())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
