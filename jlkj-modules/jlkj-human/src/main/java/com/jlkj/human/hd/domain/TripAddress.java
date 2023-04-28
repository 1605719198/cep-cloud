package com.jlkj.human.hd.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 出差地点记录对象 human_hd_trip_address
 *
 * @author 266861
 * @date 2023-04-24
 */
public class TripAddress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String compId;

    /** 地点 */
    @Excel(name = "地点")
    private String address;

    /** 拼音缩写 */
    @Excel(name = "拼音缩写")
    private String simpl;

    /** 地点属性 */
    @Excel(name = "地点属性")
    private String type;

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
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setSimpl(String simpl)
    {
        this.simpl = simpl;
    }

    public String getSimpl()
    {
        return simpl;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
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
            .append("address", getAddress())
            .append("simpl", getSimpl())
            .append("type", getType())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
