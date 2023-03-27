package com.jlkj.human.hd.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 假别参数设定对象 human_hd_holidaysetting
 *
 * @author 266861
 * @date 2023-03-21
 */
public class CopyHolidaysetting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 来源公司别 */
    private String oldCompId;

    /** 目标公司别 */
    private String newCompId;

    /** 输入人 */
    private String creator;

    /** 输入人id */
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    public void setOldCompId(String oldCompId)
    {
        this.oldCompId = oldCompId;
    }
    public String getOldCompId()
    {
        return oldCompId;
    }

    public void setNewCompId(String newCompId)
    {
        this.newCompId = newCompId;
    }
    public String getNewCompId()
    {
        return newCompId;
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
                .append("oldCompId", getOldCompId())
                .append("newCompId", getNewCompId())
                .append("creator", getCreator())
                .append("creatorId", getCreatorId())
                .append("createDate", getCreateDate())
                .toString();
    }
}
