package com.jlkj.human.hd.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 出勤身份对象 human_hd_person_class_master
 *
 * @author 266861
 * @date 2023-04-12
 */
@Data
public class PersonClassMaster extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String compId;

    /** 工号 */
    @Excel(name = "工号")
    private String empId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String nickName;

    /** 一级单位名称 */
    private String firstDeptName;

    /** 组织机构主档id */
    private String orgId;

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
    public void setEmpId(String empId)
    {
        this.empId = empId;
    }

    public String getEmpId()
    {
        return empId;
    }
    public void setOrgId(String orgId)
    {
        this.orgId = orgId;
    }

    public String getOrgId()
    {
        return orgId;
    }
    public void setShiftmodeId(String shiftmodeId)
    {
        this.shiftmodeId = shiftmodeId;
    }

    public String getShiftmodeId()
    {
        return shiftmodeId;
    }
    public void setClassId(String classId)
    {
        this.classId = classId;
    }

    public String getClassId()
    {
        return classId;
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
            .append("empId", getEmpId())
            .append("orgId", getOrgId())
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
