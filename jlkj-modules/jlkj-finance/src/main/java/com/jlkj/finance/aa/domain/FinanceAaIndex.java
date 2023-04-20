package com.jlkj.finance.aa.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 系统选单-索引设定对象 finance_aa_index
 *
 * @author 265799
 * @date 2023-04-19
 */
@TableName(value ="finance_aa_index")
@Data
public class FinanceAaIndex extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    @TableField(exist = false)
    private String remark;

    /** id */
    private String id;

    /** 会计公司id */
    private String companyId;

    /** 上层节点编码 */
    private String parentNo;

    /** 上层节点ID */
    private String parentId;

    /** 节点序号 */
    @Excel(name = "节点序号")
    private String nodeNo;

    /** 节点类别 */
    @Excel(name = "节点类别")
    private String nodeType;

    /** 节点名称 */
    @Excel(name = "节点名称")
    private String nodeName;

    /** 用途说明 */
    @Excel(name = "用途说明")
    private String useDesc;

    /** 排列指示 */
    @Excel(name = "排列指示")
    private Long dataSeq;

    /** 节点资料操作授权群组 */
    @Excel(name = "节点资料操作授权群组")
    private String nodeAuthgrp;

    /** 明细资料操作授权群组 */
    @Excel(name = "明细资料操作授权群组")
    private String dataAuthgrp;

    /** 新增日期 */
    private String createDate;

    /** 异动日期 */
    private String updateDate;

    /** 新增人姓名 */
    private String createName;

    /** 异动人姓名 */
    private String updateName;

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
    public void setParentNo(String parentNo)
    {
        this.parentNo = parentNo;
    }

    public String getParentNo()
    {
        return parentNo;
    }
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getParentId()
    {
        return parentId;
    }
    public void setNodeNo(String nodeNo)
    {
        this.nodeNo = nodeNo;
    }

    public String getNodeNo()
    {
        return nodeNo;
    }
    public void setNodeType(String nodeType)
    {
        this.nodeType = nodeType;
    }

    public String getNodeType()
    {
        return nodeType;
    }
    public void setNodeName(String nodeName)
    {
        this.nodeName = nodeName;
    }

    public String getNodeName()
    {
        return nodeName;
    }
    public void setUseDesc(String useDesc)
    {
        this.useDesc = useDesc;
    }

    public String getUseDesc()
    {
        return useDesc;
    }
    public void setDataSeq(Long dataSeq)
    {
        this.dataSeq = dataSeq;
    }

    public Long getDataSeq()
    {
        return dataSeq;
    }
    public void setNodeAuthgrp(String nodeAuthgrp)
    {
        this.nodeAuthgrp = nodeAuthgrp;
    }

    public String getNodeAuthgrp()
    {
        return nodeAuthgrp;
    }
    public void setDataAuthgrp(String dataAuthgrp)
    {
        this.dataAuthgrp = dataAuthgrp;
    }

    public String getDataAuthgrp()
    {
        return dataAuthgrp;
    }
    public void setCreateDate(String createDate)
    {
        this.createDate = createDate;
    }

    public String getCreateDate()
    {
        return createDate;
    }
    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }

    public String getUpdateDate()
    {
        return updateDate;
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
            .append("companyId", getCompanyId())
            .append("parentNo", getParentNo())
            .append("parentId", getParentId())
            .append("nodeNo", getNodeNo())
            .append("nodeType", getNodeType())
            .append("nodeName", getNodeName())
            .append("useDesc", getUseDesc())
            .append("dataSeq", getDataSeq())
            .append("nodeAuthgrp", getNodeAuthgrp())
            .append("dataAuthgrp", getDataAuthgrp())
            .append("createBy", getCreateBy())
            .append("createDate", getCreateDate())
            .append("updateBy", getUpdateBy())
            .append("updateDate", getUpdateDate())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createName", getCreateName())
            .append("updateName", getUpdateName())
            .toString();
    }
}
