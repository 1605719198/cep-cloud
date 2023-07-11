package com.jlkj.human.st.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 倒班人员法定假日加班统计对象 human_hd_ holiday_overtime
 *
 * @author 267383
 * @date 2023-07-10
 */
public class HolidayOvertimeStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 序号 */
    @Excel(name = "序号")
    private String num;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date1;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date2;

    /** 公司别 */
    private String compId;

    /** 员工Id */
    private String empId;

    /** 工号 */
    @Excel(name = "工号")
    private String empNo;

    /** 姓名 */
    @Excel(name = "姓名")
    private String empName;

    /** 岗位全称 */
    @Excel(name = "岗位全称")
    private String postFullName;

    /** 岗位ID */
    private String postId;

    /** 法定假日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "法定假日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date legalHolDate;

    /** 轮班方式ID */
    private String turnTypeId;

    /** 轮班方式中文 */
    @Excel(name = "轮班方式中文")
    private String turnTypeName;

    /** 班别ID */
    private String classId;

    /** 班别中文 */
    @Excel(name = "班别中文")
    private String className;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectDate;

    /** 班次开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "班次开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date extraWorkBegin;

    /** 班次结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "班次结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date extraWorkEnd;

    /** 加班时数 */
    @Excel(name = "加班时数")
    private BigDecimal extraWorkHours;

    /** 刷卡时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "刷卡时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date slotCardTime;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setNum(String num)
    {
        this.num = num;
    }

    public String getNum()
    {
        return num;
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
    public void setEmpNo(String empNo)
    {
        this.empNo = empNo;
    }

    public String getEmpNo()
    {
        return empNo;
    }
    public void setEmpName(String empName)
    {
        this.empName = empName;
    }

    public String getEmpName()
    {
        return empName;
    }
    public void setPostFullName(String postFullName)
    {
        this.postFullName = postFullName;
    }

    public String getPostFullName()
    {
        return postFullName;
    }
    public void setPostId(String postId)
    {
        this.postId = postId;
    }

    public String getPostId()
    {
        return postId;
    }
    public void setLegalHolDate(Date legalHolDate)
    {
        this.legalHolDate = legalHolDate;
    }

    public Date getLegalHolDate()
    {
        return legalHolDate;
    }
    public void setTurnTypeId(String turnTypeId)
    {
        this.turnTypeId = turnTypeId;
    }

    public String getTurnTypeId()
    {
        return turnTypeId;
    }
    public void setTurnTypeName(String turnTypeName)
    {
        this.turnTypeName = turnTypeName;
    }

    public String getTurnTypeName()
    {
        return turnTypeName;
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
    public void setEffectDate(Date effectDate)
    {
        this.effectDate = effectDate;
    }

    public Date getEffectDate()
    {
        return effectDate;
    }
    public void setExtraWorkBegin(Date extraWorkBegin)
    {
        this.extraWorkBegin = extraWorkBegin;
    }

    public Date getExtraWorkBegin()
    {
        return extraWorkBegin;
    }
    public void setExtraWorkEnd(Date extraWorkEnd)
    {
        this.extraWorkEnd = extraWorkEnd;
    }

    public Date getExtraWorkEnd()
    {
        return extraWorkEnd;
    }
    public void setExtraWorkHours(BigDecimal extraWorkHours)
    {
        this.extraWorkHours = extraWorkHours;
    }

    public BigDecimal getExtraWorkHours()
    {
        return extraWorkHours;
    }
    public void setSlotCardTime(Date slotCardTime)
    {
        this.slotCardTime = slotCardTime;
    }

    public Date getSlotCardTime()
    {
        return slotCardTime;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compId", getCompId())
            .append("empId", getEmpId())
            .append("empNo", getEmpNo())
            .append("empName", getEmpName())
            .append("postFullName", getPostFullName())
            .append("postId", getPostId())
            .append("legalHolDate", getLegalHolDate())
            .append("turnTypeId", getTurnTypeId())
            .append("turnTypeName", getTurnTypeName())
            .append("classId", getClassId())
            .append("className", getClassName())
            .append("effectDate", getEffectDate())
            .append("extraWorkBegin", getExtraWorkBegin())
            .append("extraWorkEnd", getExtraWorkEnd())
            .append("extraWorkHours", getExtraWorkHours())
            .append("slotCardTime", getSlotCardTime())
                .append("num", getNum())
                .append("date1", getDate1())
                .append("date2", getDate2())
            .toString();
    }
}
