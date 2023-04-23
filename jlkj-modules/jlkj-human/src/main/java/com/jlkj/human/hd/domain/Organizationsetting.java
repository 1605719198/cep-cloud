package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 机构参数对象 human_hd_organizationsetting
 *
 * @author 266861
 * @date 2023-03-28
 */
public class Organizationsetting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 上班打卡有效时间 */
    @Excel(name = "上班打卡有效时间")
    private Long onDutyTime;

    /** 下班打卡有效时间 */
    @Excel(name = "下班打卡有效时间")
    private Long offDutyTime;

    /** 迟到时间 */
    @Excel(name = "迟到时间")
    private Long lateTime;

    /** 早退时间 */
    @Excel(name = "早退时间")
    private Long leaTime;

    /** 上午下班打卡结束时间 */
    @Excel(name = "上午下班打卡结束时间")
    private Long offDutyAm;

    /** 下午上班打卡开始时间 */
    @Excel(name = "下午上班打卡开始时间")
    private Long onDutyPm;

    /** 打卡间隔时间 */
    @Excel(name = "打卡间隔时间")
    private Long spaceTime;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectDate;

    /** 缓冲时间 */
    @Excel(name = "缓冲时间")
    private Long delayTime;

    /** 往前审核天数 */
    @Excel(name = "往前审核天数")
    private Long beforeCheckDays;

    /** 备注 */
    @Excel(name = "备注")
    private String description;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 公司名 */
    @Excel(name = "公司名")
    private String compName;

    /** 组织机构 */
    @Excel(name = "组织机构")
    private String organizationId;

    /** 组织机构名 */
    @Excel(name = "组织机构名")
    private String organizationName;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人id */
    @Excel(name = "输入人id")
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
    public void setOnDutyTime(Long onDutyTime)
    {
        this.onDutyTime = onDutyTime;
    }

    public Long getOnDutyTime()
    {
        return onDutyTime;
    }
    public void setOffDutyTime(Long offDutyTime)
    {
        this.offDutyTime = offDutyTime;
    }

    public Long getOffDutyTime()
    {
        return offDutyTime;
    }
    public void setLateTime(Long lateTime)
    {
        this.lateTime = lateTime;
    }

    public Long getLateTime()
    {
        return lateTime;
    }
    public void setLeaTime(Long leaTime)
    {
        this.leaTime = leaTime;
    }

    public Long getLeaTime()
    {
        return leaTime;
    }
    public void setOffDutyAm(Long offDutyAm)
    {
        this.offDutyAm = offDutyAm;
    }

    public Long getOffDutyAm()
    {
        return offDutyAm;
    }
    public void setOnDutyPm(Long onDutyPm)
    {
        this.onDutyPm = onDutyPm;
    }

    public Long getOnDutyPm()
    {
        return onDutyPm;
    }
    public void setSpaceTime(Long spaceTime)
    {
        this.spaceTime = spaceTime;
    }

    public Long getSpaceTime()
    {
        return spaceTime;
    }
    public void setEffectDate(Date effectDate)
    {
        this.effectDate = effectDate;
    }

    public Date getEffectDate()
    {
        return effectDate;
    }
    public void setDelayTime(Long delayTime)
    {
        this.delayTime = delayTime;
    }

    public Long getDelayTime()
    {
        return delayTime;
    }
    public void setBeforeCheckDays(Long beforeCheckDays)
    {
        this.beforeCheckDays = beforeCheckDays;
    }

    public Long getBeforeCheckDays()
    {
        return beforeCheckDays;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setCompName(String compName)
    {
        this.compName = compName;
    }

    public String getCompName()
    {
        return compName;
    }
    public void setOrganizationId(String organizationId)
    {
        this.organizationId = organizationId;
    }

    public String getOrganizationId()
    {
        return organizationId;
    }
    public void setOrganizationName(String organizationName)
    {
        this.organizationName = organizationName;
    }

    public String getOrganizationName()
    {
        return organizationName;
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
            .append("onDutyTime", getOnDutyTime())
            .append("offDutyTime", getOffDutyTime())
            .append("lateTime", getLateTime())
            .append("leaTime", getLeaTime())
            .append("offDutyAm", getOffDutyAm())
            .append("onDutyPm", getOnDutyPm())
            .append("spaceTime", getSpaceTime())
            .append("effectDate", getEffectDate())
            .append("delayTime", getDelayTime())
            .append("beforeCheckDays", getBeforeCheckDays())
            .append("description", getDescription())
            .append("compId", getCompId())
            .append("compName", getCompName())
            .append("organizationId", getOrganizationId())
            .append("organizationName", getOrganizationName())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
