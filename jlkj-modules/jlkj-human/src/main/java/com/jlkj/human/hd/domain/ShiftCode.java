package com.jlkj.human.hd.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 班次数据对象 human_hd_shift_code
 *
 * @author 266861
 * @date 2023-04-06
 */
public class ShiftCode extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String compId;

    /** 轮班方式ID */
    private String shiftmodeId;

    /** 班次编码 */
    @Excel(name = "班次编码")
    private String shiftCode;

    /** 班次开始时间_时 */
    @Excel(name = "班次开始时间_时")
    private String startHour;

    /** 班次开始时间_分 */
    private String startMin;

    /** 班次工作时间_时 */
    @Excel(name = "班次工作时间_时")
    private String conHour;

    /** 班次工作时间_分 */
    private String conMin;

    /** 班次结束时间_时 */
    private String endHour;

    /** 班次结束时间_分 */
    private String endMin;

    /** 是否中午打卡 */
    @Excel(name = "是否中午打卡")
    private String isNoon;

    /** 中间休息次数 */
    @Excel(name = "中间休息次数")
    private Long restCount;

    /** 大小夜 */
    @Excel(name = "大小夜")
    private String bigSmaNight;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 说明 */
    @Excel(name = "说明")
    private String description;

    /** 是否使用 */
    private String isUsed;

    /** 一次休息开始_时 */
    private String restStartHour;

    /** 一次休息开始_分 */
    private String restStartMin;

    /** 一次休息时长 */
    @Excel(name = "一次休息时长")
    private String restConMin;

    /** 一次休息结束_时 */
    private String restEndHour;

    /** 一次休息结束_分 */
    private String restEndMin;

    /** 二次休息开始_时 */
    private String restStartHour2;

    /** 二次休息开始_分 */
    private String restStartMin2;

    /** 二次休息时长 */
    private String restConMin2;

    /** 二次休息结束_时 */
    private String restEndHour2;

    /** 二次休息结束_分 */
    private String restEndMin2;

    /** 三次休息开始_时 */
    private String restStartHour3;

    /** 三次休息开始_分 */
    private String restStartMin3;

    /** 三次休息时长 */
    private String restConMin3;

    /** 三次休息结束_时 */
    private String restEndHour3;

    /** 三次休息结束_分 */
    private String restEndMin3;

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
    public void setShiftmodeId(String shiftmodeId)
    {
        this.shiftmodeId = shiftmodeId;
    }

    public String getShiftmodeId()
    {
        return shiftmodeId;
    }
    public void setShiftCode(String shiftCode)
    {
        this.shiftCode = shiftCode;
    }

    public String getShiftCode()
    {
        return shiftCode;
    }
    public void setStartHour(String startHour)
    {
        this.startHour = startHour;
    }

    public String getStartHour()
    {
        return startHour;
    }
    public void setStartMin(String startMin)
    {
        this.startMin = startMin;
    }

    public String getStartMin()
    {
        return startMin;
    }
    public void setConHour(String conHour)
    {
        this.conHour = conHour;
    }

    public String getConHour()
    {
        return conHour;
    }
    public void setConMin(String conMin)
    {
        this.conMin = conMin;
    }

    public String getConMin()
    {
        return conMin;
    }
    public void setEndHour(String endHour)
    {
        this.endHour = endHour;
    }

    public String getEndHour()
    {
        return endHour;
    }
    public void setEndMin(String endMin)
    {
        this.endMin = endMin;
    }

    public String getEndMin()
    {
        return endMin;
    }
    public void setIsNoon(String isNoon)
    {
        this.isNoon = isNoon;
    }

    public String getIsNoon()
    {
        return isNoon;
    }
    public void setRestCount(Long restCount)
    {
        this.restCount = restCount;
    }

    public Long getRestCount()
    {
        return restCount;
    }
    public void setBigSmaNight(String bigSmaNight)
    {
        this.bigSmaNight = bigSmaNight;
    }

    public String getBigSmaNight()
    {
        return bigSmaNight;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setIsUsed(String isUsed)
    {
        this.isUsed = isUsed;
    }

    public String getIsUsed()
    {
        return isUsed;
    }
    public void setRestStartHour(String restStartHour)
    {
        this.restStartHour = restStartHour;
    }

    public String getRestStartHour()
    {
        return restStartHour;
    }
    public void setRestStartMin(String restStartMin)
    {
        this.restStartMin = restStartMin;
    }

    public String getRestStartMin()
    {
        return restStartMin;
    }
    public void setRestConMin(String restConMin)
    {
        this.restConMin = restConMin;
    }

    public String getRestConMin()
    {
        return restConMin;
    }
    public void setRestEndHour(String restEndHour)
    {
        this.restEndHour = restEndHour;
    }

    public String getRestEndHour()
    {
        return restEndHour;
    }
    public void setRestEndMin(String restEndMin)
    {
        this.restEndMin = restEndMin;
    }

    public String getRestEndMin()
    {
        return restEndMin;
    }
    public void setRestStartHour2(String restStartHour2)
    {
        this.restStartHour2 = restStartHour2;
    }

    public String getRestStartHour2()
    {
        return restStartHour2;
    }
    public void setRestStartMin2(String restStartMin2)
    {
        this.restStartMin2 = restStartMin2;
    }

    public String getRestStartMin2()
    {
        return restStartMin2;
    }
    public void setRestConMin2(String restConMin2)
    {
        this.restConMin2 = restConMin2;
    }

    public String getRestConMin2()
    {
        return restConMin2;
    }
    public void setRestEndHour2(String restEndHour2)
    {
        this.restEndHour2 = restEndHour2;
    }

    public String getRestEndHour2()
    {
        return restEndHour2;
    }
    public void setRestEndMin2(String restEndMin2)
    {
        this.restEndMin2 = restEndMin2;
    }

    public String getRestEndMin2()
    {
        return restEndMin2;
    }
    public void setRestStartHour3(String restStartHour3)
    {
        this.restStartHour3 = restStartHour3;
    }

    public String getRestStartHour3()
    {
        return restStartHour3;
    }
    public void setRestStartMin3(String restStartMin3)
    {
        this.restStartMin3 = restStartMin3;
    }

    public String getRestStartMin3()
    {
        return restStartMin3;
    }
    public void setRestConMin3(String restConMin3)
    {
        this.restConMin3 = restConMin3;
    }

    public String getRestConMin3()
    {
        return restConMin3;
    }
    public void setRestEndHour3(String restEndHour3)
    {
        this.restEndHour3 = restEndHour3;
    }

    public String getRestEndHour3()
    {
        return restEndHour3;
    }
    public void setRestEndMin3(String restEndMin3)
    {
        this.restEndMin3 = restEndMin3;
    }

    public String getRestEndMin3()
    {
        return restEndMin3;
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
            .append("shiftCode", getShiftCode())
            .append("startHour", getStartHour())
            .append("startMin", getStartMin())
            .append("conHour", getConHour())
            .append("conMin", getConMin())
            .append("endHour", getEndHour())
            .append("endMin", getEndMin())
            .append("isNoon", getIsNoon())
            .append("restCount", getRestCount())
            .append("bigSmaNight", getBigSmaNight())
            .append("status", getStatus())
            .append("description", getDescription())
            .append("isUsed", getIsUsed())
            .append("restStartHour", getRestStartHour())
            .append("restStartMin", getRestStartMin())
            .append("restConMin", getRestConMin())
            .append("restEndHour", getRestEndHour())
            .append("restEndMin", getRestEndMin())
            .append("restStartHour2", getRestStartHour2())
            .append("restStartMin2", getRestStartMin2())
            .append("restConMin2", getRestConMin2())
            .append("restEndHour2", getRestEndHour2())
            .append("restEndMin2", getRestEndMin2())
            .append("restStartHour3", getRestStartHour3())
            .append("restStartMin3", getRestStartMin3())
            .append("restConMin3", getRestConMin3())
            .append("restEndHour3", getRestEndHour3())
            .append("restEndMin3", getRestEndMin3())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
