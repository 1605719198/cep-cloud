package com.jlkj.human.hs.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 各公司薪给输入对象 human_hs_pay_sheet_input
 *
 * @author jiangbingchen
 * @date 2023-05-21
 */
public class PaySheetInput extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 公司别 */
    private String compId;

    /** 薪等 */
    @Excel(name = "薪等")
    private String payTier;

    /** 最低值 */
    @Excel(name = "最低值")
    private Long minMon;

    /** 最高值 */
    @Excel(name = "最高值")
    private Long maxMon;

    /** 中值 */
    @Excel(name = "中值")
    private Long midMon;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 预留字段1 */
    private String resvAttr1;

    /** 预留字段2 */
    private String resvAttr2;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
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
    public void setPayTier(String payTier)
    {
        this.payTier = payTier;
    }

    public String getPayTier()
    {
        return payTier;
    }
    public void setMinMon(Long minMon)
    {
        this.minMon = minMon;
    }

    public Long getMinMon()
    {
        return minMon;
    }
    public void setMaxMon(Long maxMon)
    {
        this.maxMon = maxMon;
    }

    public Long getMaxMon()
    {
        return maxMon;
    }
    public void setMidMon(Long midMon)
    {
        this.midMon = midMon;
    }

    public Long getMidMon()
    {
        return midMon;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
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
            .append("uuid", getUuid())
            .append("compId", getCompId())
            .append("payTier", getPayTier())
            .append("minMon", getMinMon())
            .append("maxMon", getMaxMon())
            .append("midMon", getMidMon())
            .append("status", getStatus())
            .append("resvAttr1", getResvAttr1())
            .append("resvAttr2", getResvAttr2())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
