package com.jlkj.human.st.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 补休统计对象 human_hd_comptime
 *
 * @author 267383
 * @date 2023-07-04
 */
public class ComptimeStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String compId;

    /** 员工id */
    private String empId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String empName;

    /** 工号 */
    @Excel(name = "工号")
    private String empNo;

    /** 序号 */
    @Excel(name = "序号")
    private String num;

    /** 岗位ID */
    private String postId;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String postName;

    /** 补休开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "补休开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 补休结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "补休结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 存班小时数 */
    @Excel(name = "存班小时数")
    private String saveHours;

    /** 补休小时数 */
    @Excel(name = "补休小时数")
    private String compHours;

    /** 辅助说明 */
    private String description;

    /** 审核状态 */
    @Excel(name = "审核状态")
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

    /** 预留属性1 */
    private String resvAttr1;

    /** 预留属性2 */
    private String resvAttr2;

    /** 预留属性3 */
    private String resvAttr3;

    /** 预留属性4 */
    private String resvAttr4;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date1;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date2;

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
    public void setSaveHours(String saveHours)
    {
        this.saveHours = saveHours;
    }

    public String getSaveHours()
    {
        return saveHours;
    }
    public void setCompHours(String compHours)
    {
        this.compHours = compHours;
    }

    public String getCompHours()
    {
        return compHours;
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
    public void setResvAttr1(String resvAttr1)
    {
        this.resvAttr1 = resvAttr1;
    }

    public String getResvAttr1()
    {
        return resvAttr1;
    }
    public void setResvAttr2(String resvAttr2)
    {
        this.resvAttr2 = resvAttr2;
    }

    public String getResvAttr2()
    {
        return resvAttr2;
    }
    public void setResvAttr3(String resvAttr3)
    {
        this.resvAttr3 = resvAttr3;
    }

    public String getResvAttr3()
    {
        return resvAttr3;
    }
    public void setResvAttr4(String resvAttr4)
    {
        this.resvAttr4 = resvAttr4;
    }

    public String getResvAttr4()
    {
        return resvAttr4;
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
            .append("empName", getEmpName())
            .append("empNo", getEmpNo())
            .append("postId", getPostId())
            .append("postName", getPostName())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("saveHours", getSaveHours())
            .append("compHours", getCompHours())
            .append("description", getDescription())
            .append("status", getStatus())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .append("resvAttr1", getResvAttr1())
            .append("resvAttr2", getResvAttr2())
            .append("resvAttr3", getResvAttr3())
            .append("resvAttr4", getResvAttr4())
                .append("num", getNum())
                .append("date1", getDate1())
                .append("date2", getDate2())
            .toString();
    }
}
