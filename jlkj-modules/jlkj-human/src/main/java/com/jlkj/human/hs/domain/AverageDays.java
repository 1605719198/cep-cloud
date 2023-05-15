package com.jlkj.human.hs.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 各公司平均天数设定对象 human_hs_average_days
 *
 * @author 266861
 * @date 2023-05-12
 */
public class AverageDays extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectDate;

    /** 月平均天数 */
    @Excel(name = "月平均天数")
    private BigDecimal avgDayNum;

    /** 版本号 */
    @Excel(name = "版本号")
    private Long versionNo;

    /** 备注 */
    private String comm;

    /** 预留字段1 */
    private String resvAttr1;

    /** 预留字段2 */
    private String resvAttr2;

    /** 输入人ID */
    private String creatorId;

    /** 输入人工号 */
    private String creatorNo;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUuid()
    {
        return uuid;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setEffectDate(Date effectDate)
    {
        this.effectDate = effectDate;
    }

    public Date getEffectDate()
    {
        return effectDate;
    }
    public void setAvgDayNum(BigDecimal avgDayNum)
    {
        this.avgDayNum = avgDayNum;
    }

    public BigDecimal getAvgDayNum()
    {
        return avgDayNum;
    }
    public void setVersionNo(Long versionNo)
    {
        this.versionNo = versionNo;
    }

    public Long getVersionNo()
    {
        return versionNo;
    }
    public void setComm(String comm)
    {
        this.comm = comm;
    }

    public String getComm()
    {
        return comm;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("uuid", getUuid())
            .append("compId", getCompId())
            .append("effectDate", getEffectDate())
            .append("avgDayNum", getAvgDayNum())
            .append("versionNo", getVersionNo())
            .append("comm", getComm())
            .append("resvAttr1", getResvAttr1())
            .append("resvAttr2", getResvAttr2())
            .append("creatorId", getCreatorId())
            .append("creatorNo", getCreatorNo())
            .append("creator", getCreator())
            .append("createDate", getCreateDate())
            .toString();
    }
}
