package com.jlkj.finance.ft.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 资产使用状态明细设定对象 finance_ft_status_detail
 *
 * @author SunXuTong
 * @date 2023-07-06
 */
public class FinanceFtStatusDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId
    private String uuid;

    /** 会计公司id */
    @Excel(name = "会计公司id")
    private String companyId;

    /** 状态代码 */
    @Excel(name = "状态代码")
    private String statusCode;

    /** 资产用途 */
    @Excel(name = "资产用途")
    private String assetPurpose;

    /** 财务用途 */
    @Excel(name = "财务用途")
    private String financePurpose;

    /** 是否提折旧 */
    @Excel(name = "是否提折旧")
    private String isDepr;

    /** 费用性质 */
    @Excel(name = "费用性质")
    private String expenseType;

    /** 备注 */
    @Excel(name = "备注")
    private String detailRemark;

    /** 新增人名称 */
    private String createName;

    /** 异动名称 */
    private String updateName;

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUuid()
    {
        return uuid;
    }
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setStatusCode(String statusCode)
    {
        this.statusCode = statusCode;
    }

    public String getStatusCode()
    {
        return statusCode;
    }
    public void setAssetPurpose(String assetPurpose)
    {
        this.assetPurpose = assetPurpose;
    }

    public String getAssetPurpose()
    {
        return assetPurpose;
    }
    public void setFinancePurpose(String financePurpose)
    {
        this.financePurpose = financePurpose;
    }

    public String getFinancePurpose()
    {
        return financePurpose;
    }
    public void setIsDepr(String isDepr)
    {
        this.isDepr = isDepr;
    }

    public String getIsDepr()
    {
        return isDepr;
    }
    public void setExpenseType(String expenseType)
    {
        this.expenseType = expenseType;
    }

    public String getExpenseType()
    {
        return expenseType;
    }
    public void setDetailRemark(String detailRemark)
    {
        this.detailRemark = detailRemark;
    }

    public String getDetailRemark()
    {
        return detailRemark;
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
            .append("uuid", getUuid())
            .append("companyId", getCompanyId())
            .append("statusCode", getStatusCode())
            .append("assetPurpose", getAssetPurpose())
            .append("financePurpose", getFinancePurpose())
            .append("isDepr", getIsDepr())
            .append("expenseType", getExpenseType())
            .append("detailRemark", getDetailRemark())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
