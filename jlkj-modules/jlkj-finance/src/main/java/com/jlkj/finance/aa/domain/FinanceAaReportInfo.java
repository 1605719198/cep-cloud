package com.jlkj.finance.aa.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 印项目计算记录对象 finance_aa_report_info
 *
 * @author jlkj
 * @date 2023-06-28
 */
public class FinanceAaReportInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键
 */
    private String id;

    /** 公司代码
 */
    private String companyId;

    /** 报表id
 */
    @Excel(name = "报表id")
    private String reportId;



    /** 报表no
     */
    @Excel(name = "报表代号")
    private String reportNo;
    /** 报表name
     */
    @Excel(name = "报表名称")
    private String reportName;
    /** 会计周期 */

    @Excel(name = "会计周期", width = 30)
    @JsonFormat(pattern ="yyyy-MM",timezone="GMT+8")
    private String acctPeriod;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 异常信息
 */
    @Excel(name = "异常信息")
    private String info;

    /** 新增人名称 */
    @Excel(name = "新增人名称")
    private String createName;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
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
    public void setAcctPeriod(String acctPeriod)
    {
        this.acctPeriod = acctPeriod;
    }

    public String getAcctPeriod()
    {
        return acctPeriod;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setInfo(String info)
    {
        this.info = info;
    }

    public String getInfo()
    {
        return info;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getCreateName()
    {
        return createName;
    }
    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("reportId", getReportId())
            .append("reportNo", getReportNo())
            .append("reportName", getReportName())
            .append("acctPeriod", getAcctPeriod())
            .append("status", getStatus())
            .append("info", getInfo())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .toString();
    }
}
