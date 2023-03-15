package com.jlkj.human.hp.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门资料维护对象 sys_dept
 *
 * @author jlkj
 * @date 2023-03-08
 */
public class SysDept extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 机构id */
    private Long deptId;

    /** 机构编码 */
    @Excel(name = "机构编码")
    private String deptCode;

    /** 机构名称 */
    @Excel(name = "机构名称")
    private String deptName;

    /** 板块ID */
    private String boardId;

    /** 上级机构id */
    private Long parentId;

    /** 上级机构名称 */
    private String parentName;

    /** 上级机构全称 */
    private String parentFullName;

    /** 祖级列表 */
    private String ancestors;

    /** 机构层级 */
    private String orgTierId;

    /** 排序序号 */
    private Long orderNum;

    /** 领导 */
    private String leader;

    /** 邮箱 */
    private String email;

    /** 删除标志 */
    private String delFlag;

    /** 成本中心 */
    private String costCenterId;

    /** 传真 */
    private String fax;

    /** 电话 */
    private String phone;

    /** 变更原因 */
    private String changeReason;

    /** 生效日期 */
    private Long effectDate;

    /** 版本号 */
    private String versionNo;

    /** 是否公司 */
    private String ifCompany;

    /** 公司编码 */
    private String compId;

    /** 是否显示 */
    private String ifDisplay;

    /** 状态 */
    private String status;

    /** 是否是新的 */
    private String isNew;

    /** 公司简称 */
    private String companyName;
    /** 子部门 */
    private List<SysDept> children = new ArrayList<SysDept>();

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getDeptId()
    {
        return deptId;
    }
    public void setDeptCode(String deptCode)
    {
        this.deptCode = deptCode;
    }

    public String getDeptCode()
    {
        return deptCode;
    }
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getDeptName()
    {
        return deptName;
    }
    public void setBoardId(String boardId)
    {
        this.boardId = boardId;
    }

    public String getBoardId()
    {
        return boardId;
    }
    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getParentId()
    {
        return parentId;
    }
    public void setParentName(String parentName)
    {
        this.parentName = parentName;
    }

    public String getParentName()
    {
        return parentName;
    }
    public void setParentFullName(String parentFullName)
    {
        this.parentFullName = parentFullName;
    }

    public String getParentFullName()
    {
        return parentFullName;
    }
    public void setAncestors(String ancestors)
    {
        this.ancestors = ancestors;
    }

    public String getAncestors()
    {
        return ancestors;
    }
    public void setOrgTierId(String orgTierId)
    {
        this.orgTierId = orgTierId;
    }

    public String getOrgTierId()
    {
        return orgTierId;
    }
    public void setOrderNum(Long orderNum)
    {
        this.orderNum = orderNum;
    }

    public Long getOrderNum()
    {
        return orderNum;
    }
    public void setLeader(String leader)
    {
        this.leader = leader;
    }

    public String getLeader()
    {
        return leader;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }
    public void setCostCenterId(String costCenterId)
    {
        this.costCenterId = costCenterId;
    }

    public String getCostCenterId()
    {
        return costCenterId;
    }
    public void setFax(String fax)
    {
        this.fax = fax;
    }

    public String getFax()
    {
        return fax;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setChangeReason(String changeReason)
    {
        this.changeReason = changeReason;
    }

    public String getChangeReason()
    {
        return changeReason;
    }
    public void setEffectDate(Long effectDate)
    {
        this.effectDate = effectDate;
    }

    public Long getEffectDate()
    {
        return effectDate;
    }
    public void setVersionNo(String versionNo)
    {
        this.versionNo = versionNo;
    }

    public String getVersionNo()
    {
        return versionNo;
    }
    public void setIfCompany(String ifCompany)
    {
        this.ifCompany = ifCompany;
    }

    public String getIfCompany()
    {
        return ifCompany;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setIfDisplay(String ifDisplay)
    {
        this.ifDisplay = ifDisplay;
    }

    public String getIfDisplay()
    {
        return ifDisplay;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setIsNew(String isNew)
    {
        this.isNew = isNew;
    }

    public String getIsNew()
    {
        return isNew;
    }
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public String getCompanyName()
    {
        return companyName;
    }

    public List<SysDept> getChildren()
    {
        return children;
    }

    public void setChildren(List<SysDept> children)
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("deptId", getDeptId())
            .append("deptCode", getDeptCode())
            .append("deptName", getDeptName())
            .append("boardId", getBoardId())
            .append("parentId", getParentId())
            .append("parentName", getParentName())
            .append("parentFullName", getParentFullName())
            .append("ancestors", getAncestors())
            .append("orgTierId", getOrgTierId())
            .append("orderNum", getOrderNum())
            .append("leader", getLeader())
            .append("email", getEmail())
            .append("delFlag", getDelFlag())
            .append("costCenterId", getCostCenterId())
            .append("fax", getFax())
            .append("phone", getPhone())
            .append("changeReason", getChangeReason())
            .append("effectDate", getEffectDate())
            .append("versionNo", getVersionNo())
            .append("ifCompany", getIfCompany())
            .append("compId", getCompId())
            .append("ifDisplay", getIfDisplay())
            .append("createBy", getCreateBy())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("isNew", getIsNew())
            .append("companyName", getCompanyName())
            .toString();
    }
}
