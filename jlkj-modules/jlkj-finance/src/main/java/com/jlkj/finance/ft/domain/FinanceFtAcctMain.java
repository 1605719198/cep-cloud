package com.jlkj.finance.ft.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 账务代码对象 finance_ft_acct_main
 *
 * @author SunXuTong
 * @date 2023-06-27
 */
public class FinanceFtAcctMain extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId
    private String uuid;

    /** 公司 */
    private String companyId;

    /** 账务分类代码 */
    private String acctType;

    /** 账务代码 */
    @Excel(name = "账务代码")
    private String acctLogic;

    /** 账务代码明细 */
    private String acctDetail;

    /** 账务代码名称 */
    @Excel(name = "账务代码名称")
    private String logicName;

    /** 是否抛AA */
    @Excel(name = "是否抛AA")
    private String isAa;

    /** 备注 */
    @Excel(name = "备注")
    private String logicRemark;

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
    public void setAcctType(String acctType)
    {
        this.acctType = acctType;
    }

    public String getAcctType()
    {
        return acctType;
    }
    public void setAcctLogic(String acctLogic)
    {
        this.acctLogic = acctLogic;
    }

    public String getAcctLogic()
    {
        return acctLogic;
    }
    public void setAcctDetail(String acctDetail)
    {
        this.acctDetail = acctDetail;
    }

    public String getAcctDetail()
    {
        return acctDetail;
    }
    public void setLogicName(String logicName)
    {
        this.logicName = logicName;
    }

    public String getLogicName()
    {
        return logicName;
    }
    public void setIsAa(String isAa)
    {
        this.isAa = isAa;
    }

    public String getIsAa()
    {
        return isAa;
    }
    public void setLogicRemark(String logicRemark)
    {
        this.logicRemark = logicRemark;
    }

    public String getLogicRemark()
    {
        return logicRemark;
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
            .append("acctType", getAcctType())
            .append("acctLogic", getAcctLogic())
            .append("acctDetail", getAcctDetail())
            .append("logicName", getLogicName())
            .append("isAa", getIsAa())
            .append("logicRemark", getLogicRemark())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
