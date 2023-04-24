package com.jlkj.human.hd.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 人员卡钟明细对象 human_hd_person_colock_detail
 *
 * @author 266861
 * @date 2023-03-29
 */
public class PersonColockDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 主表ID */
    @Excel(name = "主表ID")
    private String personColockId;

    /** 卡钟ID */
    @Excel(name = "卡钟ID")
    private String macId;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    @Excel(name = "输入人ID")
    private String creatorId;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setPersonColockId(String personColockId)
    {
        this.personColockId = personColockId;
    }

    public String getPersonColockId()
    {
        return personColockId;
    }
    public void setMacId(String macId)
    {
        this.macId = macId;
    }

    public String getMacId()
    {
        return macId;
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
                .append("personColockId", getPersonColockId())
                .append("macId", getMacId())
                .append("creator", getCreator())
                .append("creatorId", getCreatorId())
                .append("createDate", getCreateDate())
                .toString();
    }
}
