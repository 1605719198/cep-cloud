package com.jlkj.finance.aa.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 利率汇率管理对象 finance_aa_rate
 *
 * @author SunXuTong
 * @date 2023-04-17
 */
@Data
public class FinanceAaRate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date ratedate;

    /** 汇率类别 */
    @Excel(name = "汇率类别")
    private String ratecate;

    /** 交易市场代码 */
    @Excel(name = "交易市场代码")
    private String ratespot;

    /** 币别代码 */
    @Excel(name = "币别代码")
    private String ratecrcy;

    /** 汇率 */
    @Excel(name = "汇率")
    private BigDecimal ratevalue;

    /** 资料来源 */
    @Excel(name = "资料来源")
    private String ratesrc;

    /** 新增人名称 */
    private String createName;

    /** 异动名称 */
    private String updateName;
    /** 起始日期 */
    private String startDate;
    /** 截止日期 */
    private String endDate;
    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setRatedate(Date ratedate)
    {
        this.ratedate = ratedate;
    }

    public Date getRatedate()
    {
        return ratedate;
    }
    public void setRatecate(String ratecate)
    {
        this.ratecate = ratecate;
    }

    public String getRatecate()
    {
        return ratecate;
    }
    public void setRatespot(String ratespot)
    {
        this.ratespot = ratespot;
    }

    public String getRatespot()
    {
        return ratespot;
    }
    public void setRatecrcy(String ratecrcy)
    {
        this.ratecrcy = ratecrcy;
    }

    public String getRatecrcy()
    {
        return ratecrcy;
    }
    public void setRatevalue(BigDecimal ratevalue)
    {
        this.ratevalue = ratevalue;
    }

    public BigDecimal getRatevalue()
    {
        return ratevalue;
    }
    public void setRatesrc(String ratesrc)
    {
        this.ratesrc = ratesrc;
    }

    public String getRatesrc()
    {
        return ratesrc;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getCreateName()
    {
        return createName;
    }
    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }

    public String getUpdateName()
    {
        return updateName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("ratedate", getRatedate())
            .append("ratecate", getRatecate())
            .append("ratespot", getRatespot())
            .append("ratecrcy", getRatecrcy())
            .append("ratevalue", getRatevalue())
            .append("ratesrc", getRatesrc())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
