package com.jlkj.finance.ao.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 会计确认主档对象 finance_ao_account
 *
 * @author 265799
 * @date 2023-06-25
 */
@Data
public class FinanceAoAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String companyId;

    /** 业务单号 */
    @Excel(name = "业务单号")
    private String billNo;

    /** 业务id */
    @Excel(name = "业务id")
    private String billId;

    /** 签核会计 */
    @Excel(name = "签核会计")
    private String accountBy;

    /** 签核会计姓名 */
    @Excel(name = "签核会计姓名")
    private String accountName;

    /** 期望付款方式 */
    @Excel(name = "期望付款方式")
    private String payType;

    /** 抛帐日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "抛帐日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date postDate;

    /** 确认时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "确认时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date approvalDate;

    /** 批示意见 */
    @Excel(name = "批示意见")
    private String approvalComments;

    /** 批示结论 */
    @Excel(name = "批示结论")
    private String approvalStatus;
    /** 处理状态 */

    private String  handleStatus;
    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 新增人姓名 */
    private String createName;

    /** 异动人姓名 */
    private String updateName;
    /** 记帐币金额 */
    @Excel(name = "记帐币金额")
    private BigDecimal amt;
    /** 编号类型 */
    private String  billType;
    /**
     * 开始日期
     */
    private String startDate;

    /**
     * 结束日期
     */
    private String endDate;

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
    public void setBillNo(String billNo)
    {
        this.billNo = billNo;
    }

    public String getBillNo()
    {
        return billNo;
    }
    public void setBillId(String billId)
    {
        this.billId = billId;
    }

    public String getBillId()
    {
        return billId;
    }
    public void setAccountBy(String accountBy)
    {
        this.accountBy = accountBy;
    }

    public String getAccountBy()
    {
        return accountBy;
    }
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getAccountName()
    {
        return accountName;
    }
    public void setPayType(String payType)
    {
        this.payType = payType;
    }

    public String getPayType()
    {
        return payType;
    }
    public void setPostDate(Date postDate)
    {
        this.postDate = postDate;
    }

    public Date getPostDate()
    {
        return postDate;
    }
    public void setApprovalDate(Date approvalDate)
    {
        this.approvalDate = approvalDate;
    }

    public Date getApprovalDate()
    {
        return approvalDate;
    }
    public void setApprovalComments(String approvalComments)
    {
        this.approvalComments = approvalComments;
    }

    public String getApprovalComments()
    {
        return approvalComments;
    }
    public void setApprovalStatus(String approvalStatus)
    {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalStatus()
    {
        return approvalStatus;
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
            .append("companyId", getCompanyId())
            .append("billNo", getBillNo())
            .append("billId", getBillId())
            .append("accountBy", getAccountBy())
            .append("accountName", getAccountName())
            .append("payType", getPayType())
            .append("postDate", getPostDate())
            .append("approvalDate", getApprovalDate())
            .append("approvalComments", getApprovalComments())
            .append("approvalStatus", getApprovalStatus())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("updateName", getUpdateName())
            .toString();
    }
}
