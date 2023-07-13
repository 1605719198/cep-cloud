package com.jlkj.human.st.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 存班统计对象 human_hd_save_time
 *
 * @author 267383
 * @date 2023-07-05
 */
public class SaveTimeStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String compId;

    /** 年 */
    private Long year;

    /** 月（1-1月，2-1月，…… 12-12月，13-15季度，14-2季度，15-3季度，16-4季度，17-年） */
    private Long month;

    /** 员工id */
    private String empId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String empName;

    /** 工号 */
    @Excel(name = "工号")
    private String empNo;

    /** 岗位ID */
    private String postId;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String postName;

    /** 加班天数（加班审批结束时，写入） */
    private String overtimeDays;

    /** 加班小时数（加班审批结束时，写入） */
    @Excel(name = "加班小时数", readConverterExp = "加=班审批结束时，写入")
    private String overtimeHours;

    /** 存班天数（加班审批结束时，累加；补休审批结束时，减少） */
    private String saveDays;

    /** 存班小时数（加班审批结束时，累加；补休审批结束时，减少） */
    @Excel(name = "存班小时数", readConverterExp = "加=班审批结束时，累加；补休审批结束时，减少")
    private String saveHours;

    /** 补休天数（补休审批结束时，减少） */
    private String compDays;

    /** 补休小时数（补休审批结束时，写入） */
    @Excel(name = "补休小时数", readConverterExp = "补=休审批结束时，写入")
    private String compHours;

    /** 输入人 */
    private String creator;

    /** 序号 */
    @Excel(name = "序号")
    private String num;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
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
    public void setYear(Long year)
    {
        this.year = year;
    }

    public Long getYear()
    {
        return year;
    }
    public void setMonth(Long month)
    {
        this.month = month;
    }

    public Long getMonth()
    {
        return month;
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
    public void setEmpNo(String empNo)
    {
        this.empNo = empNo;
    }

    public String getEmpNo()
    {
        return empNo;
    }
    public void setPostId(String postId)
    {
        this.postId = postId;
    }

    public String getPostId()
    {
        return postId;
    }
    public void setPostName(String postName)
    {
        this.postName = postName;
    }

    public String getPostName()
    {
        return postName;
    }
    public void setOvertimeDays(String overtimeDays)
    {
        this.overtimeDays = overtimeDays;
    }

    public String getOvertimeDays()
    {
        return overtimeDays;
    }
    public void setOvertimeHours(String overtimeHours)
    {
        this.overtimeHours = overtimeHours;
    }

    public String getOvertimeHours()
    {
        return overtimeHours;
    }
    public void setSaveDays(String saveDays)
    {
        this.saveDays = saveDays;
    }

    public String getSaveDays()
    {
        return saveDays;
    }
    public void setSaveHours(String saveHours)
    {
        this.saveHours = saveHours;
    }

    public String getSaveHours()
    {
        return saveHours;
    }
    public void setCompDays(String compDays)
    {
        this.compDays = compDays;
    }

    public String getCompDays()
    {
        return compDays;
    }
    public void setCompHours(String compHours)
    {
        this.compHours = compHours;
    }

    public String getCompHours()
    {
        return compHours;
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
    public void setNum(String num)
    {
        this.num = num;
    }

    public String getNum()
    {
        return num;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compId", getCompId())
            .append("year", getYear())
            .append("month", getMonth())
            .append("empId", getEmpId())
            .append("empName", getEmpName())
            .append("empNo", getEmpNo())
            .append("postId", getPostId())
            .append("postName", getPostName())
            .append("overtimeDays", getOvertimeDays())
            .append("overtimeHours", getOvertimeHours())
            .append("saveDays", getSaveDays())
            .append("saveHours", getSaveHours())
            .append("compDays", getCompDays())
            .append("compHours", getCompHours())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
                .append("num", getNum())
            .toString();
    }
}
