package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 年收入维护对象 human_hs_year_amt
 *
 * @author 266861
 * @date 2023-05-17
 */
public class YearAmt extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 员工id */
    @Excel(name = "员工id")
    private String empId;

    /** 员工编码 */
    @Excel(name = "员工编码")
    private String empNo;

    /** 员工姓名 */
    @Excel(name = "员工姓名")
    private String empName;

    /** 年份 */
    @Excel(name = "年份")
    private String year;

    /** 是否全年缴税 */
    @Excel(name = "是否全年缴税")
    private String ifFullTax;

    /** 全年累计是否大于6万 */
    @Excel(name = "全年累计是否大于6万")
    private String ifIncomeGreaterSix;

    /** 累计收入 */
    @Excel(name = "累计收入")
    private BigDecimal sumIncome;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入人工号 */
    private String creatorNo;

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
    public void setYear(String year)
    {
        this.year = year;
    }

    public String getYear()
    {
        return year;
    }
    public void setIfFullTax(String ifFullTax)
    {
        this.ifFullTax = ifFullTax;
    }

    public String getIfFullTax()
    {
        return ifFullTax;
    }
    public void setIfIncomeGreaterSix(String ifIncomeGreaterSix)
    {
        this.ifIncomeGreaterSix = ifIncomeGreaterSix;
    }

    public String getIfIncomeGreaterSix()
    {
        return ifIncomeGreaterSix;
    }
    public void setSumIncome(BigDecimal sumIncome)
    {
        this.sumIncome = sumIncome;
    }

    public BigDecimal getSumIncome()
    {
        return sumIncome;
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
    public void setCreatorNo(String creatorNo)
    {
        this.creatorNo = creatorNo;
    }

    public String getCreatorNo()
    {
        return creatorNo;
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
            .append("empId", getEmpId())
            .append("empNo", getEmpNo())
            .append("empName", getEmpName())
            .append("year", getYear())
            .append("ifFullTax", getIfFullTax())
            .append("ifIncomeGreaterSix", getIfIncomeGreaterSix())
            .append("sumIncome", getSumIncome())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("creatorNo", getCreatorNo())
            .append("createDate", getCreateDate())
            .toString();
    }
}
