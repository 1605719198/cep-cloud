package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工销假对象 human_hd_person_holiday_cancel
 *
 * @author 266861
 * @date 2023-04-21
 */
public class PersonHolidayCancel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 请假记录id */
    private String personHolidayId;

    /** 公司别 */
    private String compId;

    /** 工号 */
    @Excel(name = "工号")
    private String empNo;

    /** 工号ID */
    private String empId;

    /** 姓名 */
    private String empName;

    /** 岗位名称 */
    private String postName;

    /** 岗位ID */
    private String postId;

    /** 一级组织机构ID */
    private String orgParentId;

    /** 二级组织机构id */
    private String orgId;

    /** 实际请假开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际请假开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 实际请假结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际请假结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 实际请假天数 */
    @Excel(name = "实际请假天数")
    private BigDecimal leaveDays;

    /** 原始请假开始时间 */
    private Date oriStartDate;

    /** 原始请假结束时间 */
    private Date oriEndDate;

    /** 原始请假天数 */
    private BigDecimal oriLeaveDays;

    /** 请假类别 */
    @Excel(name = "请假类别")
    private String leaTypeId;

    /** 本次请假天数 */
    private BigDecimal leaveShifts;

    /** 本次请假时数 */
    private BigDecimal leaveHours;

    /** 本次请假时数（含节假日） */
    private BigDecimal morLeaveHours;

    /** 本次请假天数（含节假日） */
    private BigDecimal morLeaveShifts;

    /** 批示状态 */
    private String status;

    /** 是否全销 */
    private String isAll;

    /** 辅助说明 */
    private String description;

    /** 是否包括节假日 */
    private String isContainHoliday;

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

    /** 输入人 */
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入人日期 */
    private Date createDate;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setPersonHolidayId(String personHolidayId)
    {
        this.personHolidayId = personHolidayId;
    }

    public String getPersonHolidayId()
    {
        return personHolidayId;
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
    public void setPostName(String postName)
    {
        this.postName = postName;
    }

    public String getPostName()
    {
        return postName;
    }
    public void setPostId(String postId)
    {
        this.postId = postId;
    }

    public String getPostId()
    {
        return postId;
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
    public void setLeaveDays(BigDecimal leaveDays)
    {
        this.leaveDays = leaveDays;
    }

    public BigDecimal getLeaveDays()
    {
        return leaveDays;
    }
    public void setOriStartDate(Date oriStartDate)
    {
        this.oriStartDate = oriStartDate;
    }

    public Date getOriStartDate()
    {
        return oriStartDate;
    }
    public void setOriEndDate(Date oriEndDate)
    {
        this.oriEndDate = oriEndDate;
    }

    public Date getOriEndDate()
    {
        return oriEndDate;
    }
    public void setOriLeaveDays(BigDecimal oriLeaveDays)
    {
        this.oriLeaveDays = oriLeaveDays;
    }

    public BigDecimal getOriLeaveDays()
    {
        return oriLeaveDays;
    }
    public void setLeaTypeId(String leaTypeId)
    {
        this.leaTypeId = leaTypeId;
    }

    public String getLeaTypeId()
    {
        return leaTypeId;
    }
    public void setLeaveShifts(BigDecimal leaveShifts)
    {
        this.leaveShifts = leaveShifts;
    }

    public BigDecimal getLeaveShifts()
    {
        return leaveShifts;
    }
    public void setLeaveHours(BigDecimal leaveHours)
    {
        this.leaveHours = leaveHours;
    }

    public BigDecimal getLeaveHours()
    {
        return leaveHours;
    }
    public void setMorLeaveHours(BigDecimal morLeaveHours)
    {
        this.morLeaveHours = morLeaveHours;
    }

    public BigDecimal getMorLeaveHours()
    {
        return morLeaveHours;
    }
    public void setMorLeaveShifts(BigDecimal morLeaveShifts)
    {
        this.morLeaveShifts = morLeaveShifts;
    }

    public BigDecimal getMorLeaveShifts()
    {
        return morLeaveShifts;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setIsAll(String isAll)
    {
        this.isAll = isAll;
    }

    public String getIsAll()
    {
        return isAll;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setIsContainHoliday(String isContainHoliday)
    {
        this.isContainHoliday = isContainHoliday;
    }

    public String getIsContainHoliday()
    {
        return isContainHoliday;
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
            .append("personHolidayId", getPersonHolidayId())
            .append("compId", getCompId())
            .append("empNo", getEmpNo())
            .append("empId", getEmpId())
            .append("empName", getEmpName())
            .append("postName", getPostName())
            .append("postId", getPostId())
            .append("orgParentId", getOrgParentId())
            .append("orgId", getOrgId())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("leaveDays", getLeaveDays())
            .append("oriStartDate", getOriStartDate())
            .append("oriEndDate", getOriEndDate())
            .append("oriLeaveDays", getOriLeaveDays())
            .append("leaTypeId", getLeaTypeId())
            .append("leaveShifts", getLeaveShifts())
            .append("leaveHours", getLeaveHours())
            .append("morLeaveHours", getMorLeaveHours())
            .append("morLeaveShifts", getMorLeaveShifts())
            .append("status", getStatus())
            .append("isAll", getIsAll())
            .append("description", getDescription())
            .append("isContainHoliday", getIsContainHoliday())
            .append("remainingDays", getRemainingDays())
            .append("monthDays", getMonthDays())
            .append("monthHours", getMonthHours())
            .append("yearDays", getYearDays())
            .append("yearHours", getYearHours())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
