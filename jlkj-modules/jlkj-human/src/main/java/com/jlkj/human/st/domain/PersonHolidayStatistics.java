package com.jlkj.human.st.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工请假统计对象 human_hd_person_holiday
 *
 * @author jiangbingchen
 * @date 2023-07-03
 */
public class PersonHolidayStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Excel(name = "主键")
    private String id;

    /** 公司别 */
    private String compId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date1;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date2;

    /** 工号 */
    @Excel(name = "工号")
    private String empNo;

    /** 工号ID */
    private String empId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String empName;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String postname;

    /** 岗位ID */
    private String postid;

    /** 一级组织机构ID */
    private String orgParentId;

    /** 二级组织机构ID */
    private String orgId;

    /** 请假类别 */
    @Excel(name = "请假类别")
    private String leaTypeId;

    /** 是否包括节假日 */
    private String isContainHoliday;

    /** 辅助说明 */
    @Excel(name = "辅助说明")
    private String description;

    /** 剩余可请假天数 */
    private BigDecimal remainingDays;

    /** 本月累计天数 */
    private BigDecimal monthDays;

    /** 本月累计小时数 */
    private BigDecimal monthHours;

    /** 本年累计天数 */
    private BigDecimal yearDays;

    /** 本年累计小时数 */
    private BigDecimal yearHours;

    /** 请假批示状态 */
    @Excel(name = "请假批示状态")
    private String status;

    /** 销假批示状态 */
    private String statusCancel;

    /** 请假小时数 */
    private BigDecimal leaveHours;

    /** 请假天数 */
    @Excel(name = "请假天数")
    private BigDecimal leaveShifts;

    /** 请假开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "请假开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 请假结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "请假结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

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
    public void setDate1(Date date1)
    {
        this.date1 = date1;
    }
    public Date getDate1()
    {
        return date1;
    }

    public void setDate2(Date date2)
    {
        this.date2 = date2;
    }
    public Date getDate2()
    {
        return date2;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setEmpNo(String empNo)
    {
        this.empNo = empNo;
    }

    public String getEmpNo()
    {
        return empNo;
    }
    public void setEmpId(String empId)
    {
        this.empId = empId;
    }

    public String getEmpId()
    {
        return empId;
    }
    public void setEmpName(String empName)
    {
        this.empName = empName;
    }

    public String getEmpName()
    {
        return empName;
    }
    public void setPostname(String postname)
    {
        this.postname = postname;
    }

    public String getPostname()
    {
        return postname;
    }
    public void setPostid(String postid)
    {
        this.postid = postid;
    }

    public String getPostid()
    {
        return postid;
    }
    public void setOrgParentId(String orgParentId)
    {
        this.orgParentId = orgParentId;
    }

    public String getOrgParentId()
    {
        return orgParentId;
    }
    public void setOrgId(String orgId)
    {
        this.orgId = orgId;
    }

    public String getOrgId()
    {
        return orgId;
    }
    public void setLeaTypeId(String leaTypeId)
    {
        this.leaTypeId = leaTypeId;
    }

    public String getLeaTypeId()
    {
        return leaTypeId;
    }
    public void setIsContainHoliday(String isContainHoliday)
    {
        this.isContainHoliday = isContainHoliday;
    }

    public String getIsContainHoliday()
    {
        return isContainHoliday;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setRemainingDays(BigDecimal remainingDays)
    {
        this.remainingDays = remainingDays;
    }

    public BigDecimal getRemainingDays()
    {
        return remainingDays;
    }
    public void setMonthDays(BigDecimal monthDays)
    {
        this.monthDays = monthDays;
    }

    public BigDecimal getMonthDays()
    {
        return monthDays;
    }
    public void setMonthHours(BigDecimal monthHours)
    {
        this.monthHours = monthHours;
    }

    public BigDecimal getMonthHours()
    {
        return monthHours;
    }
    public void setYearDays(BigDecimal yearDays)
    {
        this.yearDays = yearDays;
    }

    public BigDecimal getYearDays()
    {
        return yearDays;
    }
    public void setYearHours(BigDecimal yearHours)
    {
        this.yearHours = yearHours;
    }

    public BigDecimal getYearHours()
    {
        return yearHours;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setStatusCancel(String statusCancel)
    {
        this.statusCancel = statusCancel;
    }

    public String getStatusCancel()
    {
        return statusCancel;
    }
    public void setLeaveHours(BigDecimal leaveHours)
    {
        this.leaveHours = leaveHours;
    }

    public BigDecimal getLeaveHours()
    {
        return leaveHours;
    }
    public void setLeaveShifts(BigDecimal leaveShifts)
    {
        this.leaveShifts = leaveShifts;
    }

    public BigDecimal getLeaveShifts()
    {
        return leaveShifts;
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
            .append("empNo", getEmpNo())
            .append("empId", getEmpId())
            .append("empName", getEmpName())
            .append("postname", getPostname())
            .append("postid", getPostid())
            .append("orgParentId", getOrgParentId())
            .append("orgId", getOrgId())
            .append("leaTypeId", getLeaTypeId())
            .append("isContainHoliday", getIsContainHoliday())
            .append("description", getDescription())
            .append("remainingDays", getRemainingDays())
            .append("monthDays", getMonthDays())
            .append("monthHours", getMonthHours())
            .append("yearDays", getYearDays())
            .append("yearHours", getYearHours())
            .append("status", getStatus())
            .append("statusCancel", getStatusCancel())
            .append("leaveHours", getLeaveHours())
            .append("leaveShifts", getLeaveShifts())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
                .append("date1", getDate1())
                .append("date2", getDate2())
            .toString();
    }
}
