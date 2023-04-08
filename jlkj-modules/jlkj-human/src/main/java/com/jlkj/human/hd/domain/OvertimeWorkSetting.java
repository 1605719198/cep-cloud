package com.jlkj.human.hd.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 加班补休参数设定对象 human_hd_overtimeworksetting
 *
 * @author 266861
 * @date 2023-03-29
 */
public class OvertimeWorkSetting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 存班小时是否累计到下一月度 */
    @Excel(name = "存班小时是否累计到下一月度")
    private String isTranMon;

    /** 存班小时是否累计到下一季度 */
    @Excel(name = "存班小时是否累计到下一季度")
    private String isTranQua;

    /** 存班小时是否累计到下一年度 */
    @Excel(name = "存班小时是否累计到下一年度")
    private String isTranYear;

    /** 补休最小单位（单位：小时） */
    private BigDecimal holUnit;

    /** 加班最小单位（单位：小时） */
    private BigDecimal oveUnit;

    /** 月最大加班小时数（单位：小时） */
    private BigDecimal monthOve;

    /** 月最大剩余存班小时数（单位：小时） */
    private BigDecimal resvAttr3;

    /** 月最大可送出异常次数 */
    private Long resvAttr1;

    /** 补休的最小班次数 */
    private Long resvAttr2;

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
    public void setIsTranMon(String isTranMon)
    {
        this.isTranMon = isTranMon;
    }

    public String getIsTranMon()
    {
        return isTranMon;
    }
    public void setIsTranQua(String isTranQua)
    {
        this.isTranQua = isTranQua;
    }

    public String getIsTranQua()
    {
        return isTranQua;
    }
    public void setIsTranYear(String isTranYear)
    {
        this.isTranYear = isTranYear;
    }

    public String getIsTranYear()
    {
        return isTranYear;
    }
    public void setHolUnit(BigDecimal holUnit)
    {
        this.holUnit = holUnit;
    }

    public BigDecimal getHolUnit()
    {
        return holUnit;
    }
    public void setOveUnit(BigDecimal oveUnit)
    {
        this.oveUnit = oveUnit;
    }

    public BigDecimal getOveUnit()
    {
        return oveUnit;
    }
    public void setMonthOve(BigDecimal monthOve)
    {
        this.monthOve = monthOve;
    }

    public BigDecimal getMonthOve()
    {
        return monthOve;
    }
    public void setResvAttr3(BigDecimal resvAttr3)
    {
        this.resvAttr3 = resvAttr3;
    }

    public BigDecimal getResvAttr3()
    {
        return resvAttr3;
    }
    public void setResvAttr1(Long resvAttr1)
    {
        this.resvAttr1 = resvAttr1;
    }

    public Long getResvAttr1()
    {
        return resvAttr1;
    }
    public void setResvAttr2(Long resvAttr2)
    {
        this.resvAttr2 = resvAttr2;
    }

    public Long getResvAttr2()
    {
        return resvAttr2;
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
                .append("isTranMon", getIsTranMon())
                .append("isTranQua", getIsTranQua())
                .append("isTranYear", getIsTranYear())
                .append("holUnit", getHolUnit())
                .append("oveUnit", getOveUnit())
                .append("monthOve", getMonthOve())
                .append("resvAttr3", getResvAttr3())
                .append("resvAttr1", getResvAttr1())
                .append("resvAttr2", getResvAttr2())
                .append("creator", getCreator())
                .append("creatorId", getCreatorId())
                .append("createDate", getCreateDate())
                .toString();
    }
}
