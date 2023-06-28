package com.jlkj.finance.aa.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 印项目计算结果对象 finance_aa_project_result
 *
 * @author jlkj
 * @date 2023-06-28
 */
public class FinanceAaProjectResult extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 公司代码 */
    @Excel(name = "公司代码")
    private String companyId;

    /** 报表id */
    @Excel(name = "报表id")
    private String reportId;

    /** 报表代号 */
    @Excel(name = "报表代号")
    private String reportNo;

    /** 印表项目 */
    @Excel(name = "印表项目")
    private String itemCode;

    /** 会计周期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "会计周期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date acctPeriod;

    /** 金额实际数 */
    @Excel(name = "金额实际数")
    private BigDecimal amt;

    /** 数量实际数 */
    @Excel(name = "数量实际数")
    private BigDecimal qty;

    /** 新增人名称 */
    @Excel(name = "新增人名称")
    private String createName;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setReportId(String reportId)
    {
        this.reportId = reportId;
    }

    public String getReportId()
    {
        return reportId;
    }
    public void setReportNo(String reportNo)
    {
        this.reportNo = reportNo;
    }

    public String getReportNo()
    {
        return reportNo;
    }
    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    public String getItemCode()
    {
        return itemCode;
    }
    public void setAcctPeriod(Date acctPeriod)
    {
        this.acctPeriod = acctPeriod;
    }

    public Date getAcctPeriod()
    {
        return acctPeriod;
    }
    public void setAmt(BigDecimal amt)
    {
        this.amt = amt;
    }

    public BigDecimal getAmt()
    {
        return amt;
    }
    public void setQty(BigDecimal qty)
    {
        this.qty = qty;
    }

    public BigDecimal getQty()
    {
        return qty;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getCreateName()
    {
        return createName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("reportId", getReportId())
            .append("reportNo", getReportNo())
            .append("itemCode", getItemCode())
            .append("acctPeriod", getAcctPeriod())
            .append("amt", getAmt())
            .append("qty", getQty())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .toString();
    }
}
