package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 凭证类别维护对象 finance_aa_voucher_type
 *
 * @author 116514
 * @date 2023-04-13
 */
public class FinanceVoucherType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private String id;

    /** 凭证类别 */
    @Excel(name = "凭证类别")
    private String voucherType;

    /** 凭主类别名称 */
    @Excel(name = "凭主类别名称")
    private String voucherTypeName;

    /** 新增人 */
    private String createName;

    /** 异动人 */
    private String updateName;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setVoucherType(String voucherType)
    {
        this.voucherType = voucherType;
    }

    public String getVoucherType()
    {
        return voucherType;
    }
    public void setVoucherTypeName(String voucherTypeName)
    {
        this.voucherTypeName = voucherTypeName;
    }

    public String getVoucherTypeName()
    {
        return voucherTypeName;
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
            .append("voucherType", getVoucherType())
            .append("voucherTypeName", getVoucherTypeName())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
