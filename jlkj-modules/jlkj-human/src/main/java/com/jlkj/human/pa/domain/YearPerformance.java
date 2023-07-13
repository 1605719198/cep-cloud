package com.jlkj.human.pa.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.annotation.Excels;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 年度绩效主档对象 human_pa_year_performance
 *
 * @author 267383
 * @date 2023-06-09
 */
@TableName(value ="human_pa_year_performance")
public class YearPerformance implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId
    private String id;

    /** 序号 */
    @Excel(name = "序号", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String num;

    /** 公司别 */
    @Excel(name = "公司", type = Excel.Type.EXPORT)
    private String compId;

    /** 年度 */
    @Excels({
            @Excel(name = "考评年度", type = Excel.Type.IMPORT),
            @Excel(name = "年度", type = Excel.Type.EXPORT)
    })
    private String year;

    /** 一级单位 */
    @Excels({
            @Excel(name = "单位", type = Excel.Type.IMPORT),
            @Excel(name = "一级单位", type = Excel.Type.EXPORT)
    })
    private String dept;

    /** 状态 */
    @Excel(name = "状态", type = Excel.Type.EXPORT)
    private String status;

    /** 人数 */
    @Excel(name = "人数", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String people;

    /** 比例 */
    @Excel(name = "比例", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String proportion;

    /** 岗位 */
    private String postId;

    /** 员工ID */
    @Excel(name = "职工编号", type = Excel.Type.IMPORT)
    private String empId;

    /** 年度绩效分数 */
    @Excel(name = "年度绩效", type = Excel.Type.IMPORT)
    private BigDecimal yearScore;

    /** 等第 */
    @Excel(name = "年度等第", type = Excel.Type.IMPORT)
    private String grade;

    /** 主管复核得分 */
    private BigDecimal admScore;

    /** 复核主管 */
    private String admEmpId;

    /** 年度绩效最终得分 */
    private BigDecimal score;

    /** 输入人 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入人工号 */
    private String creatorNo;

    /** 输入日期 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

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
    public void setPeople(String people)
    {
        this.people = people;
    }

    public String getPeople()
    {
        return people;
    }
    public void setProportion(String proportion)
    {
        this.proportion = proportion;
    }

    public String getProportion()
    {
        return proportion;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setDept(String dept)
    {
        this.dept = dept;
    }

    public String getDept()
    {
        return dept;
    }
    public void setPostId(String postId)
    {
        this.postId = postId;
    }

    public String getPostId()
    {
        return postId;
    }
    public void setYear(String year)
    {
        this.year = year;
    }

    public String getYear()
    {
        return year;
    }
    public void setEmpId(String empId)
    {
        this.empId = empId;
    }

    public String getEmpId()
    {
        return empId;
    }
    public void setYearScore(BigDecimal yearScore)
    {
        this.yearScore = yearScore;
    }

    public BigDecimal getYearScore()
    {
        return yearScore;
    }
    public void setGrade(String grade)
    {
        this.grade = grade;
    }

    public String getGrade()
    {
        return grade;
    }
    public void setAdmScore(BigDecimal admScore)
    {
        this.admScore = admScore;
    }

    public BigDecimal getAdmScore()
    {
        return admScore;
    }
    public void setAdmEmpId(String admEmpId)
    {
        this.admEmpId = admEmpId;
    }

    public String getAdmEmpId()
    {
        return admEmpId;
    }
    public void setScore(BigDecimal score)
    {
        this.score = score;
    }

    public BigDecimal getScore()
    {
        return score;
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
            .append("people", getPeople())
            .append("proportion", getProportion())
            .append("num", getNum())
            .append("compId", getCompId())
            .append("dept", getDept())
            .append("postId", getPostId())
            .append("year", getYear())
            .append("empId", getEmpId())
            .append("yearScore", getYearScore())
            .append("grade", getGrade())
            .append("admScore", getAdmScore())
            .append("admEmpId", getAdmEmpId())
            .append("score", getScore())
            .append("status", getStatus())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("creatorNo", getCreatorNo())
            .append("createDate", getCreateDate())
            .toString();
    }
}
