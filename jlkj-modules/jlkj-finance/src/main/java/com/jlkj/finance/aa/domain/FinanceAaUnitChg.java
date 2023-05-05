package com.jlkj.finance.aa.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 计量单位转换对象 finance_aa_unit_chg
 *
 * @author SunXuTong
 * @date 2023-05-04
 */
public class FinanceAaUnitChg extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 转换单位 */
    @Excel(name = "转换单位")
    private String unitId;

    /** 转换单位编码 */
    private String unitNo;

    /** 被转换单位 */
    @Excel(name = "被转换单位")
    private String chgunitid;

    /** 被转换单位编码 */
    private String chgunitNo;

    /** 转换率 */
    @Excel(name = "转换率")
    private BigDecimal changerate;

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
    public void setUnitId(String unitId)
    {
        this.unitId = unitId;
    }

    public String getUnitId()
    {
        return unitId;
    }
    public void setUnitNo(String unitNo)
    {
        this.unitNo = unitNo;
    }

    public String getUnitNo()
    {
        return unitNo;
    }
    public void setChgunitid(String chgunitid)
    {
        this.chgunitid = chgunitid;
    }

    public String getChgunitid()
    {
        return chgunitid;
    }
    public void setChgunitNo(String chgunitNo)
    {
        this.chgunitNo = chgunitNo;
    }

    public String getChgunitNo()
    {
        return chgunitNo;
    }
    public void setChangerate(BigDecimal changerate)
    {
        this.changerate = changerate;
    }

    public BigDecimal getChangerate()
    {
        return changerate;
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
            .append("unitId", getUnitId())
            .append("unitNo", getUnitNo())
            .append("chgunitid", getChgunitid())
            .append("chgunitNo", getChgunitNo())
            .append("changerate", getChangerate())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
