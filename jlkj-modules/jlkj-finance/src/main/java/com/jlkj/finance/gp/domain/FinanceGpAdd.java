package com.jlkj.finance.gp.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 厂商增户申请对象 finance_gp_add
 *
 * @author 265799
 * @date 2023-04-10
 */
public class FinanceGpAdd extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 申请单号 */
    @Excel(name = "申请单号")
    private String applyId;

    /** 公司别 */
    @Excel(name = "公司别")
    private String compId;

    /** 厂商名称 */
    private String manufacturerName;

    /** 法定代表人 */
    @Excel(name = "法定代表人")
    private String owner;

    /** 联络人 */
    @Excel(name = "联络人")
    private String contact;

    /** 电话 */
    @Excel(name = "电话")
    private String tel;

    /** 传真 */
    private String fax;

    /** 财务维护人员 */
    private String auditor;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 发票地址 */
    private String billAddress;

    /** 邮政区号 */
    private String billPtcode;

    /** 送货地址 */
    private String sendAddress;

    /** 电子邮件 */
    private String email;

    /** 送货地址邮编 */
    private String sendPtcode;

    /** 集中采购模式 */
    private String purchaseType;

    /** 修改日期 */
    private String updateDate;

    /** 修改人 */
    private String updateUser;

    /** 创建人 */
    private String createUser;

    /** 修改人姓名 */
    private String updateUserName;

    /** 创建人姓名 */
    private String createUserName;
    /** 起始时间 */
    private  String startDate;
    /** 结束时间 */
    private  String endDate;
    /** 说明 */
    private  String  remarkAdd;

    public void setApplyId(String applyId)
    {
        this.applyId = applyId;
    }

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

    public void setRemarkAdd(String remarkAdd)
    {
        this.remarkAdd = remarkAdd;
    }

    public String getRemarkAdd()
    {
        return remarkAdd;
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
    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getOwner()
    {
        return owner;
    }
    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String getContact()
    {
        return contact;
    }
    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getTel()
    {
        return tel;
    }
    public void setFax(String fax)
    {
        this.fax = fax;
    }

    public String getFax()
    {
        return fax;
    }
    public void setAuditor(String auditor)
    {
        this.auditor = auditor;
    }

    public String getAuditor()
    {
        return auditor;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setBillAddress(String billAddress)
    {
        this.billAddress = billAddress;
    }

    public String getBillAddress()
    {
        return billAddress;
    }
    public void setBillPtcode(String billPtcode)
    {
        this.billPtcode = billPtcode;
    }

    public String getBillPtcode()
    {
        return billPtcode;
    }
    public void setSendAddress(String sendAddress)
    {
        this.sendAddress = sendAddress;
    }

    public String getSendAddress()
    {
        return sendAddress;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }
    public void setSendPtcode(String sendPtcode)
    {
        this.sendPtcode = sendPtcode;
    }

    public String getSendPtcode()
    {
        return sendPtcode;
    }
    public void setPurchaseType(String purchaseType)
    {
        this.purchaseType = purchaseType;
    }

    public String getPurchaseType()
    {
        return purchaseType;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("applyId", getApplyId())
            .append("compId", getCompId())
                .append("remarkAdd", getRemarkAdd())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("manufacturerName", getManufacturerName())
            .append("owner", getOwner())
            .append("contact", getContact())
            .append("tel", getTel())
            .append("fax", getFax())
            .append("auditor", getAuditor())
            .append("status", getStatus())
            .append("billAddress", getBillAddress())
            .append("billPtcode", getBillPtcode())
            .append("sendAddress", getSendAddress())
            .append("email", getEmail())
            .append("sendPtcode", getSendPtcode())
            .append("purchaseType", getPurchaseType())
            .append("updateDate", getUpdateDate())
            .append("updateUser", getUpdateUser())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("updateUserName", getUpdateUserName())
            .append("createUserName", getCreateUserName())
            .toString();
    }
}
