package com.jlkj.human.hs.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 薪资计算结果对象 human_hs_salary_result
 *
 * @author jlkj
 * @date 2023-06-05
 */
public class HumanHsSalaryResult extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司ID */
    @Excel(name = "公司ID")
    private String compId;

    /** 员工ID */
    @Excel(name = "员工ID")
    private String empId;

    /** 机构ID */
    @Excel(name = "机构ID")
    private String orgId;

    /** 各公司薪资表项目ID */
    @Excel(name = "各公司薪资表项目ID")
    private String payTableId;

    /** 薪资表项目编码 */
    @Excel(name = "薪资表项目编码")
    private String payTableCode;

    /** 薪资表项目名称 */
    @Excel(name = "薪资表项目名称")
    private String payTableName;

    /** 计算结果 */
    @Excel(name = "计算结果")
    private BigDecimal result;

    /** 月 */
    @Excel(name = "月")
    private Long month;

    /** 年 */
    @Excel(name = "年")
    private Long year;

    /** 薪资类别ID */
    @Excel(name = "薪资类别ID")
    private String payTypeId;

    /** 岗位全称 */
    @Excel(name = "岗位全称")
    private String postFullName;

    /** 职位等级 */
    @Excel(name = "职位等级")
    private String postLevel;

    /** 工资条显示位置 */
    @Excel(name = "工资条显示位置")
    private String salarySheetLocation;

    /** 预留属性1 */
    @Excel(name = "预留属性1")
    private String resvAttr1;

    /** 预留属性2 */
    @Excel(name = "预留属性2")
    private String resvAttr2;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    @Excel(name = "输入人ID")
    private String creatorId;

    /** 输入人工号 */
    @Excel(name = "输入人工号")
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
    public void setOrgId(String orgId)
    {
        this.orgId = orgId;
    }

    public String getOrgId()
    {
        return orgId;
    }
    public void setPayTableId(String payTableId)
    {
        this.payTableId = payTableId;
    }

    public String getPayTableId()
    {
        return payTableId;
    }
    public void setPayTableCode(String payTableCode)
    {
        this.payTableCode = payTableCode;
    }

    public String getPayTableCode()
    {
        return payTableCode;
    }
    public void setPayTableName(String payTableName)
    {
        this.payTableName = payTableName;
    }

    public String getPayTableName()
    {
        return payTableName;
    }
    public void setResult(BigDecimal result)
    {
        this.result = result;
    }

    public BigDecimal getResult()
    {
        return result;
    }
    public void setMonth(Long month)
    {
        this.month = month;
    }

    public Long getMonth()
    {
        return month;
    }
    public void setYear(Long year)
    {
        this.year = year;
    }

    public Long getYear()
    {
        return year;
    }
    public void setPayTypeId(String payTypeId)
    {
        this.payTypeId = payTypeId;
    }

    public String getPayTypeId()
    {
        return payTypeId;
    }
    public void setPostFullName(String postFullName)
    {
        this.postFullName = postFullName;
    }

    public String getPostFullName()
    {
        return postFullName;
    }
    public void setPostLevel(String postLevel)
    {
        this.postLevel = postLevel;
    }

    public String getPostLevel()
    {
        return postLevel;
    }
    public void setSalarySheetLocation(String salarySheetLocation)
    {
        this.salarySheetLocation = salarySheetLocation;
    }

    public String getSalarySheetLocation()
    {
        return salarySheetLocation;
    }
    public void setResvAttr1(String resvAttr1)
    {
        this.resvAttr1 = resvAttr1;
    }

    public String getResvAttr1()
    {
        return resvAttr1;
    }
    public void setResvAttr2(String resvAttr2)
    {
        this.resvAttr2 = resvAttr2;
    }

    public String getResvAttr2()
    {
        return resvAttr2;
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
            .append("orgId", getOrgId())
            .append("payTableId", getPayTableId())
            .append("payTableCode", getPayTableCode())
            .append("payTableName", getPayTableName())
            .append("result", getResult())
            .append("month", getMonth())
            .append("year", getYear())
            .append("payTypeId", getPayTypeId())
            .append("postFullName", getPostFullName())
            .append("postLevel", getPostLevel())
            .append("salarySheetLocation", getSalarySheetLocation())
            .append("resvAttr1", getResvAttr1())
            .append("resvAttr2", getResvAttr2())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("creatorNo", getCreatorNo())
            .append("createDate", getCreateDate())
            .toString();
    }
}
