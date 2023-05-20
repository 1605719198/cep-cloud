package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

import java.util.List;

/**
 * 集团管理-报表内容资料对象 finance_aa_project_content
 *
 * @author 116514
 * @date 2023-05-16
 */
public class FinanceAaProjectContent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 会计公司id */
    @Excel(name = "会计公司id")
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

    /** 加减项 */
    @Excel(name = "加减项")
    private String operator;

    /** 代码类别 */
    @Excel(name = "代码类别")
    private String kind;

    /** 会计科目/印表项目 */
    @Excel(name = "会计科目/印表项目")
    private String code;

    /** 金额取数方式 */
    @Excel(name = "金额取数方式")
    private String drcrkind;

    /** 表达方式 */
    @Excel(name = "表达方式")
    private String amtqty;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 新增人名称 */
    @Excel(name = "新增人名称")
    private String createName;

    /** 异动名称 */
    @Excel(name = "异动名称")
    private String updateName;

    /** 报表名称 查询用 */
    private String reportName;

    private List<FinanceAaProjectContent> dataList;

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
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public String getOperator()
    {
        return operator;
    }
    public void setKind(String kind)
    {
        this.kind = kind;
    }

    public String getKind()
    {
        return kind;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
    public void setDrcrkind(String drcrkind)
    {
        this.drcrkind = drcrkind;
    }

    public String getDrcrkind()
    {
        return drcrkind;
    }
    public void setAmtqty(String amtqty)
    {
        this.amtqty = amtqty;
    }

    public String getAmtqty()
    {
        return amtqty;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
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

    public List<FinanceAaProjectContent> getDataList() { return dataList; }

    public void setDataList(List<FinanceAaProjectContent> dataList) { this.dataList = dataList; }

    public String getReportName() {  return reportName; }

    public void setReportName(String reportName) { this.reportName = reportName; }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("reportId", getReportId())
            .append("reportNo", getReportNo())
            .append("itemCode", getItemCode())
            .append("operator", getOperator())
            .append("kind", getKind())
            .append("code", getCode())
            .append("drcrkind", getDrcrkind())
            .append("amtqty", getAmtqty())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
