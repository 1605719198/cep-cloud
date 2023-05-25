package com.jlkj.finance.aa.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

import java.util.Date;

/**
 * 关账执行记录对象 finance_aa_close_record
 *
 * @author 265799
 * @date 2023-05-23
 */
@Data
public class FinanceAaCloseRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 会计公司id */
    @Excel(name = "会计公司id")
    private String companyId;

    /** 会计周期 */
    @Excel(name = "会计周期")
    private String acctPeriod;

    /** 执行状态 */
    @Excel(name = "执行状态")
    private String status;

    /** 执行信息 */
    @Excel(name = "执行信息")
    private String descclose;

    /** 执行人名称 */
    private String createName;

    /** 执行时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

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
    public void setDesc(String descclose)
    {
        this.descclose = descclose;
    }

    public String getDesc()
    {
        return descclose;
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
            .append("acctPeriod", getAcctPeriod())
            .append("status", getStatus())
            .append("descclose", getDesc())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .toString();
    }
}
