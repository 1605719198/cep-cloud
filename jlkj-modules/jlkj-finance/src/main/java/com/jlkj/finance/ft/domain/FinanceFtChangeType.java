package com.jlkj.finance.ft.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 固定资产变动方式对象 finance_ft_change_type
 *
 * @author SunXuTong
 * @date 2023-06-26
 */
public class FinanceFtChangeType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 公司别 */
    private String companyId;

    /** 变动方式代码 */
    @Excel(name = "变动方式代码")
    private String changeWay;

    /** 变动方式名称 */
    @Excel(name = "变动方式名称")
    private String changeName;

    /** 单据性质 */
    @Excel(name = "单据性质")
    private String billNature;

    /** 账务规则代码 */
    @Excel(name = "账务规则代码")
    private String acctLogic;

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
    public void setChangeWay(String changeWay)
    {
        this.changeWay = changeWay;
    }

    public String getChangeWay()
    {
        return changeWay;
    }
    public void setChangeName(String changeName)
    {
        this.changeName = changeName;
    }

    public String getChangeName()
    {
        return changeName;
    }
    public void setBillNature(String billNature)
    {
        this.billNature = billNature;
    }

    public String getBillNature()
    {
        return billNature;
    }
    public void setAcctLogic(String acctLogic)
    {
        this.acctLogic = acctLogic;
    }

    public String getAcctLogic()
    {
        return acctLogic;
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
            .append("changeWay", getChangeWay())
            .append("changeName", getChangeName())
            .append("billNature", getBillNature())
            .append("acctLogic", getAcctLogic())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
