package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 探亲假天数设定对象 human_hd_home_leave_holiday
 *
 * @author jiangbingchen
 * @date 2023-04-08
 */
public class HomeLeaveHoliday extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String companyId;

    /** 公司名 */
    private String companyName;

    /** 年度 */
    @Excel(name = "年度")
    private String year;

    /** 工号 */
    @Excel(name = "工号")
    private String empno;

    /** 工号ID */
    private String empld;

    /** 姓名 */
    @Excel(name = "姓名")
    private String empname;

    /** 岗位名称 */
    @Excel(name = "岗位名称")
    private String postname;

    /** 岗位ID */
    private String postid;

    /** 本年度可休探亲假天数 */
    @Excel(name = "本年度可休探亲假天数")
    private BigDecimal dueHomeDays;

    /** 本年度年休假冲抵探亲假天数 */
    @Excel(name = "本年度年休假冲抵探亲假天数")
    private BigDecimal offHomeDays;

    /** 已休探亲假天数 */
    @Excel(name = "已休探亲假天数")
    private BigDecimal restHomeDays;

    /** 已休反探亲假次数 */
    @Excel(name = "已休反探亲假次数")
    private BigDecimal restFhomeNums;

    /** 已休反探亲假天数 */
    @Excel(name = "已休反探亲假天数")
    private BigDecimal restFhomeDays;

    /** 剩余探亲假天数 */
    @Excel(name = "剩余探亲假天数")
    private BigDecimal preHomeDays;

    /** 已休次数 */
    @Excel(name = "已休次数")
    private BigDecimal restHomeNums;

    /** 输入人 */
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
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
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
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
    public void setEmpno(String empno)
    {
        this.empno = empno;
    }

    public String getEmpno()
    {
        return empno;
    }
    public void setEmpld(String empld)
    {
        this.empld = empld;
    }

    public String getEmpld()
    {
        return empld;
    }
    public void setEmpname(String empname)
    {
        this.empname = empname;
    }

    public String getEmpname()
    {
        return empname;
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
    public void setDueHomeDays(BigDecimal dueHomeDays)
    {
        this.dueHomeDays = dueHomeDays;
    }

    public BigDecimal getDueHomeDays()
    {
        return dueHomeDays;
    }
    public void setOffHomeDays(BigDecimal offHomeDays)
    {
        this.offHomeDays = offHomeDays;
    }

    public BigDecimal getOffHomeDays()
    {
        return offHomeDays;
    }
    public void setRestHomeDays(BigDecimal restHomeDays)
    {
        this.restHomeDays = restHomeDays;
    }

    public BigDecimal getRestHomeDays()
    {
        return restHomeDays;
    }
    public void setRestFhomeNums(BigDecimal restFhomeNums)
    {
        this.restFhomeNums = restFhomeNums;
    }

    public BigDecimal getRestFhomeNums()
    {
        return restFhomeNums;
    }
    public void setRestFhomeDays(BigDecimal restFhomeDays)
    {
        this.restFhomeDays = restFhomeDays;
    }

    public BigDecimal getRestFhomeDays()
    {
        return restFhomeDays;
    }
    public void setPreHomeDays(BigDecimal preHomeDays)
    {
        this.preHomeDays = preHomeDays;
    }

    public BigDecimal getPreHomeDays()
    {
        return preHomeDays;
    }
    public void setRestHomeNums(BigDecimal restHomeNums)
    {
        this.restHomeNums = restHomeNums;
    }

    public BigDecimal getRestHomeNums()
    {
        return restHomeNums;
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
            .append("companyId", getCompanyId())
                .append("companyName",getCompanyName())
            .append("year", getYear())
            .append("empno", getEmpno())
            .append("empld", getEmpld())
            .append("empname", getEmpname())
            .append("postname", getPostname())
            .append("postid", getPostid())
            .append("dueHomeDays", getDueHomeDays())
            .append("offHomeDays", getOffHomeDays())
            .append("restHomeDays", getRestHomeDays())
            .append("restFhomeNums", getRestFhomeNums())
            .append("restFhomeDays", getRestFhomeDays())
            .append("preHomeDays", getPreHomeDays())
            .append("restHomeNums", getRestHomeNums())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
