package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 集团管理-报定义对象 finance_aa_report_defin
 *
 * @author 116514
 * @date 2023-05-06
 */
public class FinanceAaReportDefin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String reportId;

    /** 会计公司id */
    @Excel(name = "会计公司id")
    private String companyId;

    /** 报表代号 */
    @Excel(name = "报表代号")
    private String reportNo;

    /** 报表名称 */
    @Excel(name = "报表名称")
    private String reportName;

    /** 报表表尾 */
    @Excel(name = "报表表尾")
    private String reportTail;

    /** ureport名称 */
    @Excel(name = "ureport名称")
    private String ureportName;

    /** 报表形式 */
    @Excel(name = "报表形式")
    private String reportType;

    /** 新增人名称 */
    @Excel(name = "新增人名称")
    private String createName;

    /** 异动名称 */
    @Excel(name = "异动名称")
    private String updateName;

    public void setReportId(String reportId)
    {
        this.reportId = reportId;
    }

    public String getReportId()
    {
        return reportId;
    }
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setReportNo(String reportNo)
    {
        this.reportNo = reportNo;
    }

    public String getReportNo()
    {
        return reportNo;
    }
    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }

    public String getReportName()
    {
        return reportName;
    }
    public void setReportTail(String reportTail)
    {
        this.reportTail = reportTail;
    }

    public String getReportTail()
    {
        return reportTail;
    }
    public void setUreportName(String ureportName)
    {
        this.ureportName = ureportName;
    }

    public String getUreportName()
    {
        return ureportName;
    }
    public void setReportType(String reportType)
    {
        this.reportType = reportType;
    }

    public String getReportType()
    {
        return reportType;
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
                .append("reportId", getReportId())
                .append("companyId", getCompanyId())
                .append("reportNo", getReportNo())
                .append("reportName", getReportName())
                .append("reportTail", getReportTail())
                .append("ureportName", getUreportName())
                .append("reportType", getReportType())
                .append("createBy", getCreateBy())
                .append("createName", getCreateName())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateName", getUpdateName())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
