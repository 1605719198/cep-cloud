package com.jlkj.finance.ao.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 报支管理-报支类别集团设定主档对象 finance_ao_item
 *
 * @author 116514
 * @date 2023-05-20
 */
public class FinanceAoItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 报支类别编码 */
    @Excel(name = "报支类别编码")
    private String itemNo;

    /** 报支类别名称 */
    @Excel(name = "报支类别名称")
    private String itemName;

    /** 贷方会计科目 */
    private String crAcctCode;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 其他类别 */
    private String otherType;

    /** 备注 */
    private String otherDesc;

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
    public void setItemNo(String itemNo)
    {
        this.itemNo = itemNo;
    }

    public String getItemNo()
    {
        return itemNo;
    }
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String getItemName()
    {
        return itemName;
    }
    public void setCrAcctCode(String crAcctCode)
    {
        this.crAcctCode = crAcctCode;
    }

    public String getCrAcctCode()
    {
        return crAcctCode;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setOtherType(String otherType)
    {
        this.otherType = otherType;
    }

    public String getOtherType()
    {
        return otherType;
    }
    public void setOtherDesc(String otherDesc)
    {
        this.otherDesc = otherDesc;
    }

    public String getOtherDesc()
    {
        return otherDesc;
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
            .append("itemNo", getItemNo())
            .append("itemName", getItemName())
            .append("crAcctCode", getCrAcctCode())
            .append("status", getStatus())
            .append("otherType", getOtherType())
            .append("otherDesc", getOtherDesc())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
