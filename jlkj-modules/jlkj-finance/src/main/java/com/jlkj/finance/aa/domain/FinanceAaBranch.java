package com.jlkj.finance.aa.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 金融机构管理分行对象 finance_aa_branch
 *
 * @author SunXuTong
 * @date 2023-04-14
 */
public class FinanceAaBranch extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 父主键 */
    private String mainId;

    /** 总行代码 */
    private String bankNo;

    /** 分行代码 */
    @Excel(name = "分行代码")
    private String branchNo;

    /** 分行中文全名 */
    @Excel(name = "分行中文全名")
    private String branchchnname;

    /** 分行英文全名 */
    private String branchengname;

    /** 分行中文简称 */
    @Excel(name = "分行中文简称")
    private String branchchnabbr;

    /** 分行英文简称 */
    private String branchengabbr;

    /** 状态 */
    @Excel(name = "状态")
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
    public void setMainId(String mainId)
    {
        this.mainId = mainId;
    }

    public String getMainId()
    {
        return mainId;
    }
    public void setBankNo(String bankNo)
    {
        this.bankNo = bankNo;
    }

    public String getBankNo()
    {
        return bankNo;
    }
    public void setBranchNo(String branchNo)
    {
        this.branchNo = branchNo;
    }

    public String getBranchNo()
    {
        return branchNo;
    }
    public void setBranchchnname(String branchchnname)
    {
        this.branchchnname = branchchnname;
    }

    public String getBranchchnname()
    {
        return branchchnname;
    }
    public void setBranchengname(String branchengname)
    {
        this.branchengname = branchengname;
    }

    public String getBranchengname()
    {
        return branchengname;
    }
    public void setBranchchnabbr(String branchchnabbr)
    {
        this.branchchnabbr = branchchnabbr;
    }

    public String getBranchchnabbr()
    {
        return branchchnabbr;
    }
    public void setBranchengabbr(String branchengabbr)
    {
        this.branchengabbr = branchengabbr;
    }

    public String getBranchengabbr()
    {
        return branchengabbr;
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
            .append("mainId", getMainId())
            .append("bankNo", getBankNo())
            .append("branchNo", getBranchNo())
            .append("branchchnname", getBranchchnname())
            .append("branchengname", getBranchengname())
            .append("branchchnabbr", getBranchchnabbr())
            .append("branchengabbr", getBranchengabbr())
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
