package com.jlkj.human.pa.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 绩效系数设定对象 human_pa_performance_level
 *
 * @author 116519
 * @date 2023-05-15
 */
public class PerformanceLevel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 公司 */
    private String compId;

    /** 绩效等级编码 */
    @Excel(name = "绩效等级编码")
    private String perfTierCode;

    /** 绩效等级名称 */
    @Excel(name = "绩效等级名称")
    private String perfIterName;

    /** 考核周期ID */
    @Excel(name = "考核周期ID")
    private String perfTypeId;

    /** 绩效系数 */
    @Excel(name = "绩效系数")
    private BigDecimal perfCoefficient;

    /** 新增人工号 */
    @Excel(name = "新增人工号")
    private String creator;

    /** 新增人ID */
    @Excel(name = "新增人ID")
    private String creatorId;

    /** 新增人姓名 */
    @Excel(name = "新增人姓名")
    private String creatorName;

    /** 新增时间 */
    @Excel(name = "新增时间")
    private String creatdate;

    private List<PerformanceLevel> levelList = new ArrayList<PerformanceLevel>();
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
    public void setPerfTierCode(String perfTierCode)
    {
        this.perfTierCode = perfTierCode;
    }

    public String getPerfTierCode()
    {
        return perfTierCode;
    }
    public void setPerfIterName(String perfIterName)
    {
        this.perfIterName = perfIterName;
    }

    public String getPerfIterName()
    {
        return perfIterName;
    }
    public void setPerfTypeId(String perfTypeId)
    {
        this.perfTypeId = perfTypeId;
    }

    public String getPerfTypeId()
    {
        return perfTypeId;
    }
    public void setPerfCoefficient(BigDecimal perfCoefficient)
    {
        this.perfCoefficient = perfCoefficient;
    }

    public BigDecimal getPerfCoefficient()
    {
        return perfCoefficient;
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
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }

    public String getCreatorName()
    {
        return creatorName;
    }
    public void setCreatdate(String creatdate)
    {
        this.creatdate = creatdate;
    }

    public String getCreatdate()
    {
        return creatdate;
    }

    public List<PerformanceLevel> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<PerformanceLevel> levelList) {
        this.levelList = levelList;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("uuid", getUuid())
            .append("compId", getCompId())
            .append("perfTierCode", getPerfTierCode())
            .append("perfIterName", getPerfIterName())
            .append("perfTypeId", getPerfTypeId())
            .append("perfCoefficient", getPerfCoefficient())
            .append("remark", getRemark())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("creatorName", getCreatorName())
            .append("creatdate", getCreatdate())
            .toString();
    }
}
