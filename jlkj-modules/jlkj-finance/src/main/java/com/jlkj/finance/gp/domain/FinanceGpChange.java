package com.jlkj.finance.gp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 厂商异动申请对象 finance_gp_change
 *
 * @author 265799
 * @date 2023-04-12
 */
public class FinanceGpChange extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 申请单号 */
    @Excel(name = "申请单号")
    private String applyId;

    /** 公司别 */
    private String compId;

    /** 厂商名称 */
    @Excel(name = "厂商名称")
    private String manufacturerName;

    /** 财务维护人员 */
    @Excel(name = "财务维护人员")
    private String auditor;

    /** 修改日期 */
    @Excel(name = "修改日期")
    private String updateDate;

    /** 修改人 */
    private String updateUser;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createUser;

    /** 修改人姓名 */
    private String updateUserName;

    /** 创建人姓名 */
    private String createUserName;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 厂商编码 */
    private String manufacturerId;

    /** 说明 */
    private String remarkAdd;
    /** 起始时间 */
    private  String startDate;
    /** 结束时间 */
    private  String endDate;
    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }
    public String getEndDate()
    {
        return endDate;
    }

    public void setApplyId(String applyId)
    {
        this.applyId = applyId;
    }

    public String getApplyId()
    {
        return applyId;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setManufacturerName(String manufacturerName)
    {
        this.manufacturerName = manufacturerName;
    }

    public String getManufacturerName()
    {
        return manufacturerName;
    }
    public void setAuditor(String auditor)
    {
        this.auditor = auditor;
    }

    public String getAuditor()
    {
        return auditor;
    }
    public void setUpdateDate(String updateDate)
    {
        this.updateDate = updateDate;
    }

    public String getUpdateDate()
    {
        return updateDate;
    }
    public void setUpdateUser(String updateUser)
    {
        this.updateUser = updateUser;
    }

    public String getUpdateUser()
    {
        return updateUser;
    }
    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    public String getCreateUser()
    {
        return createUser;
    }
    public void setUpdateUserName(String updateUserName)
    {
        this.updateUserName = updateUserName;
    }

    public String getUpdateUserName()
    {
        return updateUserName;
    }
    public void setCreateUserName(String createUserName)
    {
        this.createUserName = createUserName;
    }

    public String getCreateUserName()
    {
        return createUserName;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setManufacturerId(String manufacturerId)
    {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerId()
    {
        return manufacturerId;
    }
    public void setRemarkAdd(String remarkAdd)
    {
        this.remarkAdd = remarkAdd;
    }

    public String getRemarkAdd()
    {
        return remarkAdd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("applyId", getApplyId())
            .append("compId", getCompId())
           .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("manufacturerName", getManufacturerName())
            .append("auditor", getAuditor())
            .append("updateDate", getUpdateDate())
            .append("updateUser", getUpdateUser())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("updateUserName", getUpdateUserName())
            .append("createUserName", getCreateUserName())
            .append("status", getStatus())
            .append("manufacturerId", getManufacturerId())
            .append("remarkAdd", getRemarkAdd())
            .toString();
    }
}
