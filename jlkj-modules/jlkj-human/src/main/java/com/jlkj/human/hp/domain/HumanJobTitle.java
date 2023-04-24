package com.jlkj.human.hp.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 职位名称数据维护对象 human_hp_job_title
 *
 * @author 266861
 * @date 2023-03-13
 */
public class HumanJobTitle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 职称代号 */
    @Excel(name = "职称代号")
    private String jobTitleNo;

    /** 中文职称说明 */
    @Excel(name = "中文职称说明")
    private String jobTitleName;

    /** 英文职称说明 */
    @Excel(name = "英文职称说明")
    private String jobTitleNameEng;

    /** 申请加班 */
    @Excel(name = "申请加班")
    private String overTime;

    /** 人员类别 */
    @Excel(name = "人员类别")
    private String kind;

    /** 人员层级 */
    @Excel(name = "人员层级")
    private String titleLevel;

    /** 状态码 */
    @Excel(name = "状态码")
    private String status;

    /** 现有人数 */
    private Long number;

    /** 维护人 */
    private String creator;

    /** 维护日期 */
    private Date createDate;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setJobTitleNo(String jobTitleNo)
    {
        this.jobTitleNo = jobTitleNo;
    }

    public String getJobTitleNo()
    {
        return jobTitleNo;
    }
    public void setJobTitleName(String jobTitleName)
    {
        this.jobTitleName = jobTitleName;
    }

    public String getJobTitleName()
    {
        return jobTitleName;
    }
    public void setJobTitleNameEng(String jobTitleNameEng)
    {
        this.jobTitleNameEng = jobTitleNameEng;
    }

    public String getJobTitleNameEng()
    {
        return jobTitleNameEng;
    }
    public void setOverTime(String overTime)
    {
        this.overTime = overTime;
    }

    public String getOverTime()
    {
        return overTime;
    }
    public void setKind(String kind)
    {
        this.kind = kind;
    }

    public String getKind()
    {
        return kind;
    }
    public void setTitleLevel(String titleLevel)
    {
        this.titleLevel = titleLevel;
    }

    public String getTitleLevel()
    {
        return titleLevel;
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
    public void setCreateDate(Date createDate)
    {
        this.createDate = createDate;
    }

    public Date getCreateDate()
    {
        return createDate;
    }

    public void setNumber(Long number)
    {
        this.number = number;
    }

    public Long getNumber()
    {
        return number;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("jobTitleNo", getJobTitleNo())
            .append("jobTitleName", getJobTitleName())
            .append("jobTitleNameEng", getJobTitleNameEng())
            .append("overTime", getOverTime())
            .append("kind", getKind())
            .append("titleLevel", getTitleLevel())
            .append("status", getStatus())
            .append("creator", getCreator())
            .append("createDate", getCreateDate())
            .append("number",getNumber())
            .toString();
    }
}
