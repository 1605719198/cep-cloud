package com.jlkj.finance.ft.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 资产大类基本资料对象 finance_ft_group
 *
 * @author SunXuTong
 * @date 2023-06-21
 */
public class FinanceFtGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String companyId;

    /** 资产大类编码 */
    @Excel(name = "资产大类编码")
    private String assetGroupNo;

    /** 资产大类名称 */
    @Excel(name = "资产大类名称")
    private String assetGroupName;

    /** 折旧方法编码 */
    private String deprMethodNo;

    /** 折旧方法名称 */
    @Excel(name = "折旧方法名称")
    private String deprMethodName;

    /** 数量单位 */
    @Excel(name = "数量单位")
    private String unit;

    /** 耐用期限 */
    @Excel(name = "耐用期限")
    private Long usableMonth;

    /** 残值率 */
    @Excel(name = "残值率")
    private BigDecimal scrapRate;

    /** 是否计提折旧 */
    private String deprType;

    /** 固定资产科目 */
    private String assetAccount;

    /** 累计折旧科目 */
    private String deprAccount;

    /** 资产清理科目 */
    private String clearAccount;

    /** 折旧费用科目 */
    private String expenseAccount;

    /** 减值准备科目 */
    private String reserveAccount;

    /** 状态 */
    private String status;

    /** 新增人名称 */
    private String createName;

    /** 异动名称 */
    private String updateName;

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
    public void setAssetGroupNo(String assetGroupNo)
    {
        this.assetGroupNo = assetGroupNo;
    }

    public String getAssetGroupNo()
    {
        return assetGroupNo;
    }
    public void setAssetGroupName(String assetGroupName)
    {
        this.assetGroupName = assetGroupName;
    }

    public String getAssetGroupName()
    {
        return assetGroupName;
    }
    public void setDeprMethodNo(String deprMethodNo)
    {
        this.deprMethodNo = deprMethodNo;
    }

    public String getDeprMethodNo()
    {
        return deprMethodNo;
    }
    public void setDeprMethodName(String deprMethodName)
    {
        this.deprMethodName = deprMethodName;
    }

    public String getDeprMethodName()
    {
        return deprMethodName;
    }
    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return unit;
    }
    public void setUsableMonth(Long usableMonth)
    {
        this.usableMonth = usableMonth;
    }

    public Long getUsableMonth()
    {
        return usableMonth;
    }
    public void setScrapRate(BigDecimal scrapRate)
    {
        this.scrapRate = scrapRate;
    }

    public BigDecimal getScrapRate()
    {
        return scrapRate;
    }
    public void setDeprType(String deprType)
    {
        this.deprType = deprType;
    }

    public String getDeprType()
    {
        return deprType;
    }
    public void setAssetAccount(String assetAccount)
    {
        this.assetAccount = assetAccount;
    }

    public String getAssetAccount()
    {
        return assetAccount;
    }
    public void setDeprAccount(String deprAccount)
    {
        this.deprAccount = deprAccount;
    }

    public String getDeprAccount()
    {
        return deprAccount;
    }
    public void setClearAccount(String clearAccount)
    {
        this.clearAccount = clearAccount;
    }

    public String getClearAccount()
    {
        return clearAccount;
    }
    public void setExpenseAccount(String expenseAccount)
    {
        this.expenseAccount = expenseAccount;
    }

    public String getExpenseAccount()
    {
        return expenseAccount;
    }
    public void setReserveAccount(String reserveAccount)
    {
        this.reserveAccount = reserveAccount;
    }

    public String getReserveAccount()
    {
        return reserveAccount;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("assetGroupNo", getAssetGroupNo())
            .append("assetGroupName", getAssetGroupName())
            .append("deprMethodNo", getDeprMethodNo())
            .append("deprMethodName", getDeprMethodName())
            .append("unit", getUnit())
            .append("usableMonth", getUsableMonth())
            .append("scrapRate", getScrapRate())
            .append("remark", getRemark())
            .append("deprType", getDeprType())
            .append("assetAccount", getAssetAccount())
            .append("deprAccount", getDeprAccount())
            .append("clearAccount", getClearAccount())
            .append("expenseAccount", getExpenseAccount())
            .append("reserveAccount", getReserveAccount())
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
