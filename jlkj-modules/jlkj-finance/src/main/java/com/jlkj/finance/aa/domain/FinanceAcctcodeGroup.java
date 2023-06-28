package com.jlkj.finance.aa.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 会计科目-集团级对象 finance_aa_group_acctcode
 *
 * @author 116514
 * @date 2023-04-17
 */
public class FinanceAcctcodeGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 会计科目主键 */
    @NotBlank(message = "会计科目不能为空")
    private String groupAcctId;

    /** 会计科目编号 */
    @Excel(name = "会计科目编号")
    @NotBlank(message = "会计科目编号不能为空")
    private String groupAcctCode;

    /** 会计科目中文名称 */
    @Excel(name = "会计科目中文名称")
    @NotBlank(message = "会计科目中文名称不能为空")
    private String groupAcctName;

    /** 上层会计科目ID */
    @NotBlank(message = "上层会计科目ID不能为空")
    private String parentId;

    /** 上层会计科目NO */
    @NotBlank(message = "上层会计科目NO不能为空")
    private String parentAcctCode;

    /** 传票性科目 */
    @Excel(name = "传票性科目")
    @NotBlank(message = "传票性科目不能为空")
    private String isVoucher;

    /** 余额方向 */
    @Excel(name = "余额方向")
    @NotBlank(message = "余额方向不能为空")
    private String drOrCr;

    /** 现金科目 */
    @Excel(name = "现金科目")
    @NotBlank(message = "现金科目不能为空")
    private String isCash;

    /** 级别 */
    private int level;

    /** 禁用标识 */
    @Excel(name = "禁用标识")
    private String disabledCode;

    /** 禁用日期 */
    private Date disabledDate;

    /** 新增人名称 */
    private String createName;

    /** 异动人名称 */
    private String updateName;

    /** 祖级列表 */
    private String ancestors;

    /** 子节点 */
    private List<FinanceAcctcodeGroup> children = new ArrayList<FinanceAcctcodeGroup>();


    public void setGroupAcctId(String groupAcctId)
    {
        this.groupAcctId = groupAcctId;
    }

    public String getGroupAcctId()
    {
        return groupAcctId;
    }
    public void setGroupAcctCode(String groupAcctCode)
    {
        this.groupAcctCode = groupAcctCode;
    }

    public String getGroupAcctCode()
    {
        return groupAcctCode;
    }
    public void setGroupAcctName(String groupAcctName)
    {
        this.groupAcctName = groupAcctName;
    }

    public String getGroupAcctName()
    {
        return groupAcctName;
    }
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getParentId()
    {
        return parentId;
    }
    public void setParentAcctCode(String parentAcctCode)
    {
        this.parentAcctCode = parentAcctCode;
    }

    public String getParentAcctCode()
    {
        return parentAcctCode;
    }
    public void setIsVoucher(String isVoucher)
    {
        this.isVoucher = isVoucher;
    }

    public String getIsVoucher()
    {
        return isVoucher;
    }
    public void setDrOrCr(String drOrCr)
    {
        this.drOrCr = drOrCr;
    }

    public String getDrOrCr()
    {
        return drOrCr;
    }
    public void setIsCash(String isCash)
    {
        this.isCash = isCash;
    }

    public String getIsCash()
    {
        return isCash;
    }
    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getLevel()
    {
        return level;
    }
    public void setDisabledCode(String disabledCode)
    {
        this.disabledCode = disabledCode;
    }

    public String getDisabledCode()
    {
        return disabledCode;
    }
    public void setDisabledDate(Date disabledDate)
    {
        this.disabledDate = disabledDate;
    }

    public Date getDisabledDate()
    {
        return disabledDate;
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


    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    public String getAncestors()
    {
        return ancestors;
    }

    public List<FinanceAcctcodeGroup> getChildren()
    {
        return children;
    }

    public void setChildren(List<FinanceAcctcodeGroup> children)
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("groupAcctId", getGroupAcctId())
                .append("groupAcctCode", getGroupAcctCode())
                .append("groupAcctName", getGroupAcctName())
                .append("parentId", getParentId())
                .append("parentAcctCode", getParentAcctCode())
                .append("ancestors", getAncestors())
                .append("isVoucher", getIsVoucher())
                .append("drOrCr", getDrOrCr())
                .append("isCash", getIsCash())
                .append("level", getLevel())
                .append("disabledCode", getDisabledCode())
                .append("disabledDate", getDisabledDate())
                .append("createBy", getCreateBy())
                .append("createName", getCreateName())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateName", getUpdateName())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
