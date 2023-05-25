package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 个人所得税比例维护对象 human_hs_personal_income_tax
 *
 * @author jiangbingchen
 * @date 2023-05-18
 */
public class PersonalIncomeTax extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 所得税级距 */
    @Excel(name = "所得税级距")
    private BigDecimal taxSpace;

    /** 税率（%） */
    @Excel(name = "税率", readConverterExp = "%=")
    private BigDecimal taxRate;

    /** 速算扣除数 */
    @Excel(name = "速算扣除数")
    private BigDecimal deductNum;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    /** 版本号 */
    @Excel(name = "版本号")
    private Long versionNo;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectDate;

    /** 类型 */
    private String type;

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
    public void setTaxSpace(BigDecimal taxSpace)
    {
        this.taxSpace = taxSpace;
    }

    public BigDecimal getTaxSpace()
    {
        return taxSpace;
    }
    public void setTaxRate(BigDecimal taxRate)
    {
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxRate()
    {
        return taxRate;
    }
    public void setDeductNum(BigDecimal deductNum)
    {
        this.deductNum = deductNum;
    }

    public BigDecimal getDeductNum()
    {
        return deductNum;
    }
    public void setVersionNo(Long versionNo)
    {
        this.versionNo = versionNo;
    }

    public Long getVersionNo()
    {
        return versionNo;
    }
    public void setEffectDate(Date effectDate)
    {
        this.effectDate = effectDate;
    }

    public Date getEffectDate()
    {
        return effectDate;
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
    public void setDate(Date date)
    {
        this.date = date;
    }

    public Date getDate()
    {
        return date;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taxSpace", getTaxSpace())
            .append("taxRate", getTaxRate())
            .append("deductNum", getDeductNum())
            .append("versionNo", getVersionNo())
            .append("effectDate", getEffectDate())
            .append("type", getType())
            .append("creator", getCreator())
                .append("creatorId", getCreatorId())
                .append("date", getDate())
            .append("createDate", getCreateDate())
            .toString();
    }
}
