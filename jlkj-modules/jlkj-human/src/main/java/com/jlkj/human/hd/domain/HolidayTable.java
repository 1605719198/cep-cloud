package com.jlkj.human.hd.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 假日设定对象 human_hd_holiday
 *
 * @author 266861
 * @date 2023-04-10
 */
public class HolidayTable extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String compId;

    /** 年 */
    private String year;

    /** 月 */
    private String month;

    /** 日 */
    private String day;

    /** 日期类型 */
    private String dateType;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date holDay;

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
    public void setYear(String year)
    {
        this.year = year;
    }

    public String getYear()
    {
        return year;
    }
    public void setMonth(String month)
    {
        this.month = month;
    }

    public String getMonth()
    {
        return month;
    }
    public void setDay(String day)
    {
        this.day = day;
    }

    public String getDay()
    {
        return day;
    }
    public void setDateType(String dateType)
    {
        this.dateType = dateType;
    }

    public String getDateType()
    {
        return dateType;
    }
    public void setHolDay(Date holDay)
    {
        this.holDay = holDay;
    }

    public Date getHolDay()
    {
        return holDay;
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
            .append("year", getYear())
            .append("month", getMonth())
            .append("day", getDay())
            .append("dateType", getDateType())
            .append("holDay", getHolDay())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
