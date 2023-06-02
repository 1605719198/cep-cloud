package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 员工受雇月维护对象 human_hs_salary_employed_month
 *
 * @author 267383
 * @date 2023-05-30
 */
public class SalaryEmployedMonth extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司ID */
    @Excel(name = "公司别")
    private String compId;

    /** 员工ID */
    private String empId;

    /** 员工编码 */
    @Excel(name = "员工编码")
    private String empNo;

    /** 主岗位 */
    @Excel(name = "主岗位")
    private String post;

    /** 受雇月 */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "受雇月", width = 30, dateFormat = "yyyy-MM")
    private Date entDate;

    /** 薪资月 */
    @JsonFormat(pattern = "yyyy-MM")
    @Excel(name = "薪资月", width = 30, dateFormat = "yyyy-MM")
    private Date salaryDate;

    /** 简要说明 */
    @Excel(name = "简要说明")
    private String descript;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入人工号 */
    @Excel(name = "输入人工号")
    private String creatorNo;

    /** 输入人姓名 */
    @Excel(name = "姓名")
    private String empName;

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
    public void setPost(String post)
    {
        this.post = post;
    }

    public String getPost()
    {
        return post;
    }
    public void setEntDate(Date entDate)
    {
        this.entDate = entDate;
    }

    public Date getEntDate()
    {
        return entDate;
    }
    public void setSalaryDate(Date salaryDate)
    {
        this.salaryDate = salaryDate;
    }

    public Date getSalaryDate()
    {
        return salaryDate;
    }
    public void setDescript(String descript)
    {
        this.descript = descript;
    }

    public String getDescript()
    {
        return descript;
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
    public void setCreatorNo(String creatorNo)
    {
        this.creatorNo = creatorNo;
    }

    public String getCreatorNo()
    {
        return creatorNo;
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
            .append("empNo", getEmpNo())
                .append("empName", getEmpName())
            .append("post", getPost())
            .append("entDate", getEntDate())
            .append("salaryDate", getSalaryDate())
            .append("descript", getDescript())
            .append("status", getStatus())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("creatorNo", getCreatorNo())
            .append("createDate", getCreateDate())
            .toString();
    }
}
