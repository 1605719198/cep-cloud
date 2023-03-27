package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 假别参数设定对象 human_hd_holidaysetting
 *
 * @author 266861
 * @date 2023-03-21
 */
public class Holidaysetting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 假别名称 */
    @Excel(name = "假别名称")
    private String holidayTypeCode;

    /** 公司别 */
    private String compId;

    /** 是否含假日 */
    @Excel(name = "是否含假日")
    private String isIncHol;

    /** 常白班请假最小单位（班次） */
    @Excel(name = "常白班请假最小单位", readConverterExp = "班=次")
    private BigDecimal isNeedCheck;

    /** 常白班请假最小单位（小时） */
    @Excel(name = "常白班请假最小单位", readConverterExp = "小=时")
    private BigDecimal minUnitDay;

    /** 倒班请假最小单位（班次） */
    @Excel(name = "倒班请假最小单位", readConverterExp = "班=次")
    private BigDecimal minUnitTurns;

    /** 说明 */
    @Excel(name = "说明")
    private String description;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人id */
    private String creatorId;

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
    public void setholidayTypeCode(String holidayTypeCode)
    {
        this.holidayTypeCode = holidayTypeCode;
    }

    public String getholidayTypeCode()
    {
        return holidayTypeCode;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setIsIncHol(String isIncHol)
    {
        this.isIncHol = isIncHol;
    }

    public String getIsIncHol()
    {
        return isIncHol;
    }
    public void setIsNeedCheck(BigDecimal isNeedCheck)
    {
        this.isNeedCheck = isNeedCheck;
    }

    public BigDecimal getIsNeedCheck()
    {
        return isNeedCheck;
    }
    public void setMinUnitDay(BigDecimal minUnitDay)
    {
        this.minUnitDay = minUnitDay;
    }

    public BigDecimal getMinUnitDay()
    {
        return minUnitDay;
    }
    public void setMinUnitTurns(BigDecimal minUnitTurns)
    {
        this.minUnitTurns = minUnitTurns;
    }

    public BigDecimal getMinUnitTurns()
    {
        return minUnitTurns;
    }
    public void setdescription(String description)
    {
        this.description = description;
    }

    public String getdescription()
    {
        return description;
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
            .append("holidayTypeCode", getholidayTypeCode())
            .append("compId", getCompId())
            .append("isIncHol", getIsIncHol())
            .append("isNeedCheck", getIsNeedCheck())
            .append("minUnitDay", getMinUnitDay())
            .append("minUnitTurns", getMinUnitTurns())
            .append("description", getdescription())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
