package com.jlkj.human.ex.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

/**
 * 考试分类对象 human_ex_type
 * 
 * @author qnsdt
 * @date 2022-12-14
 */
@Data
@TableName(value ="human_ex_type")
public class ExamType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 类别id */
    @TableId
    private Long typeId;

    /** parentid */
    private Long parentId;
    
    public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/** 类别代码 */
    @Excel(name = "类别代码")
    private String typeCode;

    /** 类别名称 */
    @Excel(name = "类别名称")
    private String typeName;

    /** 顺序Id */
    @Excel(name = "顺序Id")
    private Long orderId;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 子类别 */
    private List<ExamType> children = new ArrayList<ExamType>();
    
	public void setTypeId(Long typeId) 
    {
        this.typeId = typeId;
    }

	public Long getTypeId() 
    {
        return typeId;
    }
    public void setTypeCode(String typeCode) 
    {
        this.typeCode = typeCode;
    }

    public String getTypeCode() 
    {
        return typeCode;
    }
    public void setTypeName(String typeName) 
    {
        this.typeName = typeName;
    }

    public String getTypeName() 
    {
        return typeName;
    }
    public void setOrderId(Long orderId) 
    {
        this.orderId = orderId;
    }

    public Long getOrderId() 
    {
        return orderId;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    public List<ExamType> getChildren() {
		return children;
	}

	public void setChildren(List<ExamType> children) {
		this.children = children;
	}
	
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("typeId", getTypeId())
            .append("parentId", getParentId())
            .append("typeCode", getTypeCode())
            .append("typeName", getTypeName())
            .append("orderId", getOrderId())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
