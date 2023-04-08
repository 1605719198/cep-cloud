package com.jlkj.human.hd.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 轮班方式数据对象 human_hd_shift_mode
 *
 * @author 266861
 * @date 2023-04-04
 */
public class ShiftMode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String compId;

    /** 轮班方式编码 */
    @Excel(name = "轮班方式编码")
    private String turnTypeCode;

    /** 轮班方式名称 */
    @Excel(name = "轮班方式名称")
    private String turnTypeName;

    /** 排班内是否计算加班 */
    @Excel(name = "排班内是否计算加班")
    private String resvAttr1a0;

    /** 备注 */
    @Excel(name = "备注")
    private String description;

    /** 是否倒班 */
    @Excel(name = "是否倒班")
    private String isTurn;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 是否使用 */
    private String isUsed;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
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
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setTurnTypeCode(String turnTypeCode)
    {
        this.turnTypeCode = turnTypeCode;
    }

    public String getTurnTypeCode()
    {
        return turnTypeCode;
    }
    public void setTurnTypeName(String turnTypeName)
    {
        this.turnTypeName = turnTypeName;
    }

    public String getTurnTypeName()
    {
        return turnTypeName;
    }
    public void setResvAttr1a0(String resvAttr1a0)
    {
        this.resvAttr1a0 = resvAttr1a0;
    }

    public String getResvAttr1a0()
    {
        return resvAttr1a0;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setIsTurn(String isTurn)
    {
        this.isTurn = isTurn;
    }

    public String getIsTurn()
    {
        return isTurn;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setIsUsed(String isUsed)
    {
        this.isUsed = isUsed;
    }

    public String getIsUsed()
    {
        return isUsed;
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
            .append("turnTypeCode", getTurnTypeCode())
            .append("turnTypeName", getTurnTypeName())
            .append("resvAttr1a0", getResvAttr1a0())
            .append("description", getDescription())
            .append("isTurn", getIsTurn())
            .append("status", getStatus())
            .append("isUsed", getIsUsed())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
