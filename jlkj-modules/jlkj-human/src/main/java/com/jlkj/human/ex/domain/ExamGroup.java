package com.jlkj.human.ex.domain;

import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 人员分组对象 sys_group
 * 
 * @author qnsdt
 * @date 2023-01-28
 */
public class ExamGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 分组代码 */
    @Excel(name = "分组代码")
    private String groupCode;

    /** 分组名称 */
    @Excel(name = "分组名称")
    private String groupName;

    private int personNumber;
    
    public int getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(int personNumber) {
		this.personNumber = personNumber;
	}

	/** 分组描述 */
    @Excel(name = "分组描述")
    private String groupDescribe;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupCode() 
    {
        return groupCode;
    }
    public void setGroupName(String groupName) 
    {
        this.groupName = groupName;
    }

    public String getGroupName() 
    {
        return groupName;
    }
    public void setGroupDescribe(String groupDescribe) 
    {
        this.groupDescribe = groupDescribe;
    }

    public String getGroupDescribe() 
    {
        return groupDescribe;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("groupCode", getGroupCode())
            .append("groupName", getGroupName())
            .append("personNumber",getPersonNumber())
            .append("groupDescribe", getGroupDescribe())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
