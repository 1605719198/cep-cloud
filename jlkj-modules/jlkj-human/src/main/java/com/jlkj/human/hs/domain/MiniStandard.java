package com.jlkj.human.hs.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 各公司最低工资标准及生活保障标准设定对象 human_hs_mini_standard
 *
 * @author jiangbingchen
 * @date 2023-05-24
 */
public class MiniStandard extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 主键 */
    private String uuid;

    /** 公司别 */
    private String compId;

    /** 版本号 */
    private Long version;

    /** 工资标准类型 */
    @Excel(name = "工资标准类型")
    private String salaryStandard;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal money;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectDate;

    /** 预留字段1 */
    private String resvAttr1;

    /** 预留字段2 */
    private String resvAttr2;

    /** 输入人 */
    @Excel(name = "输入人")
    private String creator;

    /** 输入人ID */
    private String creatorId;

    /** 输入日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "输入日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createDate;

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUuid()
    {
        return uuid;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setVersion(Long version)
    {
        this.version = version;
    }

    public Long getVersion()
    {
        return version;
    }
    public void setSalaryStandard(String salaryStandard)
    {
        this.salaryStandard = salaryStandard;
    }

    public String getSalaryStandard()
    {
        return salaryStandard;
    }
    public void setMoney(BigDecimal money)
    {
        this.money = money;
    }

    public BigDecimal getMoney()
    {
        return money;
    }
    public void setEffectDate(Date effectDate)
    {
        this.effectDate = effectDate;
    }

    public Date getEffectDate()
    {
        return effectDate;
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
            .append("uuid", getUuid())
            .append("compId", getCompId())
            .append("version", getVersion())
            .append("salaryStandard", getSalaryStandard())
            .append("money", getMoney())
            .append("effectDate", getEffectDate())
            .append("resvAttr1", getResvAttr1())
            .append("resvAttr2", getResvAttr2())
            .append("creator", getCreator())
            .append("creatorId", getCreatorId())
            .append("createDate", getCreateDate())
            .toString();
    }
}
