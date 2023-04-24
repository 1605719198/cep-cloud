package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 排班记录对象 human_hd_arrange_class_master
 *
 * @author 266861
 * @date 2023-04-10
 */
public class ArrangeClassMaster extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 轮班方式ID */
    @Excel(name = "轮班方式ID")
    private String shiftmodeId;

    /** 轮班方式名称 */
    @Excel(name = "轮班方式名称")
    private String turnTypeName;

    /** 班别编码 */
    @Excel(name = "班别编码")
    private String classId;

    /** 班别名称 */
    @Excel(name = "班别名称")
    private String className;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 所有班次 */
    @Excel(name = "所有班次")
    private String rule;

    /** 版本 */
    @Excel(name = "版本")
    private Long versionNo;

    /** 是否最新 */
    @Excel(name = "是否最新")
    private String isNew;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    @Excel(name = "输入人ID")
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    /** 明细数据列表 */
    private List<ArrangeClass> detailList = new ArrayList<ArrangeClass>();

    public void setDetailList(List<ArrangeClass> detailList)
    {
        this.detailList = detailList;
    }

    public List<ArrangeClass> getDetailList()
    {
        return detailList;
    }

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
    public void setShiftmodeId(String shiftmodeId)
    {
        this.shiftmodeId = shiftmodeId;
    }

    public String getShiftmodeId()
    {
        return shiftmodeId;
    }
    public void setTurnTypeName(String turnTypeName)
    {
        this.turnTypeName = turnTypeName;
    }

    public String getTurnTypeName()
    {
        return turnTypeName;
    }
    public void setClassId(String classId)
    {
        this.classId = classId;
    }

    public String getClassId()
    {
        return classId;
    }
    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getClassName()
    {
        return className;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }
    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }
    public void setRule(String rule)
    {
        this.rule = rule;
    }

    public String getRule()
    {
        return rule;
    }
    public void setVersionNo(Long versionNo)
    {
        this.versionNo = versionNo;
    }

    public Long getVersionNo()
    {
        return versionNo;
    }
    public void setIsNew(String isNew)
    {
        this.isNew = isNew;
    }

    public String getIsNew()
    {
        return isNew;
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
            .append("shiftmodeId", getShiftmodeId())
            .append("turnTypeName", getTurnTypeName())
            .append("classId", getClassId())
            .append("className", getClassName())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("rule", getRule())
            .append("versionNo", getVersionNo())
            .append("isNew", getIsNew())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .append("detailList", getDetailList())
            .toString();
    }
}
