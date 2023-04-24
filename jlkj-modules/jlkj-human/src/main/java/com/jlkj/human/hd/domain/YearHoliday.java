package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 年休假天数设定对象 human_hd_year_holiday
 *
 * @author jiangbingchen
 * @date 2023-04-12
 */
public class YearHoliday extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String compId;

    /** 公司名 */
    private String companyName;

    /** 年度 */
    @Excel(name = "年度")
    private String year;

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
    private String postName;

    /** 岗位ID */
    private String postId;

    /** 本年度可休年休假天数 */
    @Excel(name = "本年度可休年休假天数")
    private BigDecimal totalDays;

    /** 上年度剩余可休年休假天数 */
    @Excel(name = "上年度剩余可休年休假天数")
    private BigDecimal curYearDays;

    /** 累计可休年休假天数 */
    @Excel(name = "累计可休年休假天数")
    private BigDecimal preYearDays;

    /** 本年度已休年休假天数 */
    @Excel(name = "本年度已休年休假天数")
    private BigDecimal restDays;

    /** 本年度已放弃年休假天数 */
    @Excel(name = "本年度已放弃年休假天数")
    private BigDecimal giveupDays;

    /** 可结转到下一年度年休假天数 */
    @Excel(name = "可结转到下一年度年休假天数")
    private BigDecimal gotoDays;

    /** 需付未休年休假加班费天数 */
    @Excel(name = "需付未休年休假加班费天数")
    private BigDecimal overtimePay;

    /** 输入人 */
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
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
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getCompanyName()
    {
        return companyName;
    }
    public void setYear(String year)
    {
        this.year = year;
    }

    public String getYear()
    {
        return year;
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
    public void setTotalDays(BigDecimal totalDays)
    {
        this.totalDays = totalDays;
    }

    public BigDecimal getTotalDays()
    {
        return totalDays;
    }
    public void setCurYearDays(BigDecimal curYearDays)
    {
        this.curYearDays = curYearDays;
    }

    public BigDecimal getCurYearDays()
    {
        return curYearDays;
    }
    public void setPreYearDays(BigDecimal preYearDays)
    {
        this.preYearDays = preYearDays;
    }

    public BigDecimal getPreYearDays()
    {
        return preYearDays;
    }
    public void setRestDays(BigDecimal restDays)
    {
        this.restDays = restDays;
    }

    public BigDecimal getRestDays()
    {
        return restDays;
    }
    public void setGiveupDays(BigDecimal giveupDays)
    {
        this.giveupDays = giveupDays;
    }

    public BigDecimal getGiveupDays()
    {
        return giveupDays;
    }
    public void setGotoDays(BigDecimal gotoDays)
    {
        this.gotoDays = gotoDays;
    }

    public BigDecimal getGotoDays()
    {
        return gotoDays;
    }
    public void setOvertimePay(BigDecimal overtimePay)
    {
        this.overtimePay = overtimePay;
    }

    public BigDecimal getOvertimePay()
    {
        return overtimePay;
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
                .append("companyName",getCompanyName())
                .append("year", getYear())
                .append("empNo", getEmpNo())
                .append("empId", getEmpId())
                .append("empName", getEmpName())
                .append("postName", getPostName())
                .append("postId", getPostId())
                .append("totalDays", getTotalDays())
                .append("curYearDays", getCurYearDays())
                .append("preYearDays", getPreYearDays())
                .append("restDays", getRestDays())
                .append("giveupDays", getGiveupDays())
                .append("gotoDays", getGotoDays())
                .append("overtimePay", getOvertimePay())
                .append("creator", getCreator())
                .append("creatorId", getCreatorId())
                .append("createDate", getCreateDate())
                .toString();
    }
}


