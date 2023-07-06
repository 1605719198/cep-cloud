package com.jlkj.human.st.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 夜班统计对象 human_hd_night_duty
 *
 * @author 267383
 * @date 2023-07-05
 */
public class NightDutyStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 序号 */
    @Excel(name = "序号")
    private String num;

    /** 公司别 */
    private String compId;

    /** 员工Id */
    private String empId;

    /** 工号 */
    @Excel(name = "工号")
    private String empNo;

    /** 姓名 */
    @Excel(name = "姓名")
    private String empName;

    /** 岗位全称 */
    @Excel(name = "岗位全称")
    private String postFullname;

    /** 岗位ID */
    private String postId;

    /** 班次ID */
    private String shiftId;

    /** 排班日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "排班日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date shiftDate;

    /** 大夜数 */
    @Excel(name = "大夜数")
    private BigDecimal bigNight;

    /** 小夜数 */
    @Excel(name = "小夜数")
    private BigDecimal smallNight;

    /** 输入人 */
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    private Date createDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date1;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date2;

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
    public void setEmpId(String empId)
    {
        this.empId = empId;
    }

    public String getEmpId()
    {
        return empId;
    }
    public void setEmpNo(String empNo)
    {
        this.empNo = empNo;
    }

    public String getEmpNo()
    {
        return empNo;
    }
    public void setEmpName(String empName)
    {
        this.empName = empName;
    }

    public String getEmpName()
    {
        return empName;
    }
    public void setPostFullname(String postFullname)
    {
        this.postFullname = postFullname;
    }

    public String getPostFullname()
    {
        return postFullname;
    }
    public void setPostId(String postId)
    {
        this.postId = postId;
    }

    public String getPostId()
    {
        return postId;
    }
    public void setShiftId(String shiftId)
    {
        this.shiftId = shiftId;
    }

    public String getShiftId()
    {
        return shiftId;
    }
    public void setShiftDate(Date shiftDate)
    {
        this.shiftDate = shiftDate;
    }

    public Date getShiftDate()
    {
        return shiftDate;
    }
    public void setBigNight(BigDecimal bigNight)
    {
        this.bigNight = bigNight;
    }

    public BigDecimal getBigNight()
    {
        return bigNight;
    }
    public void setSmallNight(BigDecimal smallNight)
    {
        this.smallNight = smallNight;
    }

    public BigDecimal getSmallNight()
    {
        return smallNight;
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
    public void setNum(String num)
    {
        this.num = num;
    }

    public String getNum()
    {
        return num;
    }
    public void setDate1(Date date1)
    {
        this.date1 = date1;
    }
    public Date getDate1()
    {
        return date1;
    }

    public void setDate2(Date date2)
    {
        this.date2 = date2;
    }
    public Date getDate2()
    {
        return date2;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
                .append("num", getNum())
                .append("date1", getDate1())
                .append("date2", getDate2())
            .append("compId", getCompId())
            .append("empId", getEmpId())
            .append("empNo", getEmpNo())
            .append("empName", getEmpName())
            .append("postFullname", getPostFullname())
            .append("postId", getPostId())
            .append("shiftId", getShiftId())
            .append("shiftDate", getShiftDate())
            .append("bigNight", getBigNight())
            .append("smallNight", getSmallNight())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
