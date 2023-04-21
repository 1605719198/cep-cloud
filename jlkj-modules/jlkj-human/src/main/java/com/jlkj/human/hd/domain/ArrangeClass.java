package com.jlkj.human.hd.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 排班明细对象 human_hd_arrange_class
 *
 * @author 266861
 * @date 2023-04-10
 */
public class ArrangeClass extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 排班主档ID */
    @Excel(name = "排班主档ID")
    private String arrangeClassMasterId;

    /** 轮班方式ID */
    @Excel(name = "轮班方式ID")
    private String shiftmodeId;

    /** 班别ID */
    @Excel(name = "班别ID")
    private String classId;

    /** 班次ID */
    @Excel(name = "班次ID")
    private String shiftId;

    /** 班次编码 */
    @Excel(name = "班次编码")
    private String shiftCode;

    /** 排班日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "排班日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date arrShiDate;

    /** 排班说明 */
    @Excel(name = "排班说明")
    private String shiftDesc;

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
    public void setArrangeClassMasterId(String arrangeClassMasterId)
    {
        this.arrangeClassMasterId = arrangeClassMasterId;
    }

    public String getArrangeClassMasterId()
    {
        return arrangeClassMasterId;
    }
    public void setShiftmodeId(String shiftmodeId)
    {
        this.shiftmodeId = shiftmodeId;
    }

    public String getShiftmodeId()
    {
        return shiftmodeId;
    }
    public void setClassId(String classId)
    {
        this.classId = classId;
    }

    public String getClassId()
    {
        return classId;
    }
    public void setShiftId(String shiftId)
    {
        this.shiftId = shiftId;
    }

    public String getShiftId()
    {
        return shiftId;
    }
    public void setShiftCode(String shiftCode)
    {
        this.shiftCode = shiftCode;
    }

    public String getShiftCode()
    {
        return shiftCode;
    }
    public void setArrShiDate(Date arrShiDate)
    {
        this.arrShiDate = arrShiDate;
    }

    public Date getArrShiDate()
    {
        return arrShiDate;
    }
    public void setShiftDesc(String shiftDesc)
    {
        this.shiftDesc = shiftDesc;
    }

    public String getShiftDesc()
    {
        return shiftDesc;
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
            .append("arrangeClassMasterId", getArrangeClassMasterId())
            .append("shiftmodeId", getShiftmodeId())
            .append("classId", getClassId())
            .append("shiftId", getShiftId())
            .append("shiftCode", getShiftCode())
            .append("arrShiDate", getArrShiDate())
            .append("shiftDesc", getShiftDesc())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
