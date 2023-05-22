package com.jlkj.finance.aa.domain;

import com.jlkj.finance.aa.dto.FinanceAaCashflowCodeDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

import java.util.List;

/**
 * 集团管理-纵向印项目公式对象 finance_aa_project_formula
 *
 * @author 116514
 * @date 2023-05-10
 */
public class FinanceAaProjectFormula extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 会计公司id */
    private String companyId;

    /** 报表id */
    private String reportId;

    /** 报表代号 */
    private String reportNo;

    /** 印表项目 */
    @Excel(name = "印表项目")
    private String itemCode;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String itemName;

    /** 行次 */
    @Excel(name = "行次")
    private Long itemRow;

    /** 类别 */
    @Excel(name = "类别")
    private String itemType;

    /** 控制 */
    @Excel(name = "控制")
    private String sumControl;

    /** 空格 */
    @Excel(name = "空格")
    private Long skipSpace;

    /** 正负 */
    @Excel(name = "正负")
    private String sign;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 新增人名称 */
    private String createName;

    /** 异动名称 */
    private String updateName;

    private List<FinanceAaProjectFormula> dataList;

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
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String getItemName()
    {
        return itemName;
    }
    public void setItemRow(Long itemRow)
    {
        this.itemRow = itemRow;
    }

    public Long getItemRow()
    {
        return itemRow;
    }
    public void setItemType(String itemType)
    {
        this.itemType = itemType;
    }

    public String getItemType()
    {
        return itemType;
    }
    public void setSumControl(String sumControl)
    {
        this.sumControl = sumControl;
    }

    public String getSumControl()
    {
        return sumControl;
    }
    public void setSkipSpace(Long skipSpace)
    {
        this.skipSpace = skipSpace;
    }

    public Long getSkipSpace()
    {
        return skipSpace;
    }
    public void setSign(String sign)
    {
        this.sign = sign;
    }

    public String getSign()
    {
        return sign;
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

    public List<FinanceAaProjectFormula> getDataList() { return dataList; }

    public void setDataList(List<FinanceAaProjectFormula> dataList) { this.dataList = dataList; }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("reportId", getReportId())
            .append("reportNo", getReportNo())
            .append("itemCode", getItemCode())
            .append("itemName", getItemName())
            .append("itemRow", getItemRow())
            .append("itemType", getItemType())
            .append("sumControl", getSumControl())
            .append("skipSpace", getSkipSpace())
            .append("sign", getSign())
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
