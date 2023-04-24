package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 集团管理-会计公司维护对象 finance_aa_company
 *
 * @author 116514
 * @date 2023-04-23
 */
public class FinanceAaCompanyGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;

    /** 会计公司代码 */
    @Excel(name = "会计公司代码")
    private String companyNo;

    /** 会计公司简称 */
    @Excel(name = "会计公司简称")
    private String companySname;

    /** 会计公司全称 */
    @Excel(name = "会计公司全称")
    private String companyName;

    /** 默认币别id */
    private String crcyId;

    /** 默认币别编码 */
    @Excel(name = "默认币别编码")
    private String crcyCode;

    /** 对应人事公司 */
    @Excel(name = "对应人事公司")
    private String deptCode;

    /** 对应厂商编码ID */
    private String manufacturerId;

    /** 对应厂商编码NO */
    @Excel(name = "对应厂商编码NO")
    private String manufacturerNo;

    /** 新增人名称 */
    private String createName;

    /** 异动人名称 */
    private String updateName;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setCompanyNo(String companyNo)
    {
        this.companyNo = companyNo;
    }

    public String getCompanyNo()
    {
        return companyNo;
    }
    public void setCompanySname(String companySname)
    {
        this.companySname = companySname;
    }

    public String getCompanySname()
    {
        return companySname;
    }
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getCompanyName()
    {
        return companyName;
    }
    public void setCrcyId(String crcyId)
    {
        this.crcyId = crcyId;
    }

    public String getCrcyId()
    {
        return crcyId;
    }
    public void setCrcyCode(String crcyCode)
    {
        this.crcyCode = crcyCode;
    }

    public String getCrcyCode()
    {
        return crcyCode;
    }
    public void setDeptCode(String deptCode)
    {
        this.deptCode = deptCode;
    }

    public String getDeptCode()
    {
        return deptCode;
    }
    public void setManufacturerId(String manufacturerId)
    {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerId()
    {
        return manufacturerId;
    }
    public void setManufacturerNo(String manufacturerNo)
    {
        this.manufacturerNo = manufacturerNo;
    }

    public String getManufacturerNo()
    {
        return manufacturerNo;
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
            .append("companyNo", getCompanyNo())
            .append("companySname", getCompanySname())
            .append("companyName", getCompanyName())
            .append("crcyId", getCrcyId())
            .append("crcyCode", getCrcyCode())
            .append("deptCode", getDeptCode())
            .append("manufacturerId", getManufacturerId())
            .append("manufacturerNo", getManufacturerNo())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
