package com.jlkj.human.hd.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 加班汇总（薪酬计算用）对象 human_hd_over_time
 *
 * @author 266861
 * @date 2023-06-28
 */
public class OverTime extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 年 */
    @Excel(name = "年")
    private Long year;

    /** 月 */
    @Excel(name = "月")
    private Long month;

    /** 假别ID/加班类别ID（假别编码） */
    @Excel(name = "假别ID/加班类别ID", readConverterExp = "假=别编码")
    private String holOveType;

    /** 请假类的为1，加班类的为0 */
    @Excel(name = "请假类的为1，加班类的为0")
    private String isHolType;

    /** 已批班次数(导入表格的（请假天数）) */
    @Excel(name = "已批班次数(导入表格的", readConverterExp = "请=假天数")
    private BigDecimal dutyNum;

    /** 已批小时数（请假天数*8转成小时数） */
    @Excel(name = "已批小时数", readConverterExp = "请=假天数*8转成小时数")
    private BigDecimal hourNum;

    /** 含节假日已批班次数（导入时等duty_num） */
    @Excel(name = "含节假日已批班次数", readConverterExp = "导=入时等duty_num")
    private BigDecimal morDutyNum;

    /** 含节假日已批小时数（导入时等hour_num） */
    @Excel(name = "含节假日已批小时数", readConverterExp = "导=入时等hour_num")
    private BigDecimal morHourNum;

    /** 员工ID（human_hd_personnel 人员数据） */
    @Excel(name = "员工ID", readConverterExp = "h=uman_hd_personnel,人=员数据")
    private String empId;

    /** 员工编码（human_hd_personnel 人员数据） */
    @Excel(name = "员工编码", readConverterExp = "h=uman_hd_personnel,人=员数据")
    private String empNo;

    /** 岗位ID（human_hd_personnel 人员数据） */
    @Excel(name = "岗位ID", readConverterExp = "h=uman_hd_personnel,人=员数据")
    private String postId;

    /** 岗位名称（human_hd_personnel 人员数据） */
    @Excel(name = "岗位名称", readConverterExp = "h=uman_hd_personnel,人=员数据")
    private String postName;

    /** 员工姓名（human_hd_personnel 人员数据） */
    @Excel(name = "员工姓名", readConverterExp = "h=uman_hd_personnel,人=员数据")
    private String empName;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    @Excel(name = "输入人ID")
    private String creatorId;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Date createDate;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String resvAttr1a;

    /** 数据来源（1-表示导入，0-系统自动） */
    @Excel(name = "数据来源", readConverterExp = "1=-表示导入，0-系统自动")
    private String dataFrom;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setYear(Long year)
    {
        this.year = year;
    }

    public Long getYear()
    {
        return year;
    }
    public void setMonth(Long month)
    {
        this.month = month;
    }

    public Long getMonth()
    {
        return month;
    }
    public void setHolOveType(String holOveType)
    {
        this.holOveType = holOveType;
    }

    public String getHolOveType()
    {
        return holOveType;
    }
    public void setIsHolType(String isHolType)
    {
        this.isHolType = isHolType;
    }

    public String getIsHolType()
    {
        return isHolType;
    }
    public void setDutyNum(BigDecimal dutyNum)
    {
        this.dutyNum = dutyNum;
    }

    public BigDecimal getDutyNum()
    {
        return dutyNum;
    }
    public void setHourNum(BigDecimal hourNum)
    {
        this.hourNum = hourNum;
    }

    public BigDecimal getHourNum()
    {
        return hourNum;
    }
    public void setMorDutyNum(BigDecimal morDutyNum)
    {
        this.morDutyNum = morDutyNum;
    }

    public BigDecimal getMorDutyNum()
    {
        return morDutyNum;
    }
    public void setMorHourNum(BigDecimal morHourNum)
    {
        this.morHourNum = morHourNum;
    }

    public BigDecimal getMorHourNum()
    {
        return morHourNum;
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
    public void setPostId(String postId)
    {
        this.postId = postId;
    }

    public String getPostId()
    {
        return postId;
    }
    public void setPostName(String postName)
    {
        this.postName = postName;
    }

    public String getPostName()
    {
        return postName;
    }
    public void setEmpName(String empName)
    {
        this.empName = empName;
    }

    public String getEmpName()
    {
        return empName;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
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
    public void setResvAttr1a(String resvAttr1a)
    {
        this.resvAttr1a = resvAttr1a;
    }

    public String getResvAttr1a()
    {
        return resvAttr1a;
    }
    public void setDataFrom(String dataFrom)
    {
        this.dataFrom = dataFrom;
    }

    public String getDataFrom()
    {
        return dataFrom;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("year", getYear())
            .append("month", getMonth())
            .append("holOveType", getHolOveType())
            .append("isHolType", getIsHolType())
            .append("dutyNum", getDutyNum())
            .append("hourNum", getHourNum())
            .append("morDutyNum", getMorDutyNum())
            .append("morHourNum", getMorHourNum())
            .append("empId", getEmpId())
            .append("empNo", getEmpNo())
            .append("postId", getPostId())
            .append("postName", getPostName())
            .append("empName", getEmpName())
            .append("compId", getCompId())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .append("resvAttr1a", getResvAttr1a())
            .append("dataFrom", getDataFrom())
            .toString();
    }
}
