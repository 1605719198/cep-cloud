package com.jlkj.finance.ap.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 预付申请主档对象 finance_ap_loan
 *
 * @author 265799
 * @date 2023-07-10
 */
@Data
public class FinanceApLoan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /** 公司别 */
    @NotNull(message = "companyId为必须项")
    @Excel(name = "公司别")
    private String companyId;

    /** 申请单号 */
    @NotNull(message = "billNo为必须项")
    @Excel(name = "申请单号")
    private String billNo;

    /** 厂商编码 */
    @NotNull(message = "manufacturer为必须项")
    @Excel(name = "厂商编码")
    private String manufacturer;

    /** 支出类别 */
    @NotNull(message = "itemNo为必须项")
    private String itemNo;

    /** 期望付款方式 */
    @Excel(name = "期望付款方式")
    private String payType;

    /** 币别 */
    @Excel(name = "币别")
    private String crcyunit;

    /** 签核会计 */
    @Excel(name = "签核会计")
    private String accountBy;

    /** 签核会计姓名 */
    @Excel(name = "签核会计姓名")
    private String accountName;

    /** 摘要 */
    @Excel(name = "摘要")
    @NotNull(message = "billDesc为必须项")
    private String billDesc;

    /** 到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 业务员 */
    private String salesman;

    /** 业务员名称 */
    @Excel(name = "业务员名称")
    private String salesmanName;

    /** 经办人名称 */
    @Excel(name = "经办人名称")
    private String createName;

    /** 新增人部门 */
    private String createDept;

    /** 新增人部门名称 */
    private String createDeptName;

    /** 异动名称 */
    private String updateName;
    /** 起始日期 */
    private String startDate;
    /** 截止日期 */
    private String endDate;
    /** 预付金额 */
    private BigDecimal totalAmt;
    /** 付款金额 */
    private BigDecimal paidAmt;
    /** 会计确认日期 */
    private Date accountDate;
    /** 付款凭证号 */
    private String payVoucherNo;
    /** 付款日期 */
    private Date payVoucherDate;

    /** 汇率 */

    private BigDecimal exchangeRate;
    /** 预付申请明细档信息 */
    private List<FinanceApLoanDetail> financeApLoanDetailList;

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
    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }
    public void setItemNo(String itemNo)
    {
        this.itemNo = itemNo;
    }

    public String getItemNo()
    {
        return itemNo;
    }
    public void setPayType(String payType)
    {
        this.payType = payType;
    }

    public String getPayType()
    {
        return payType;
    }
    public void setCrcyunit(String crcyunit)
    {
        this.crcyunit = crcyunit;
    }

    public String getCrcyunit()
    {
        return crcyunit;
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
    public void setBillDesc(String billDesc)
    {
        this.billDesc = billDesc;
    }

    public String getBillDesc()
    {
        return billDesc;
    }
    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    public Date getDueDate()
    {
        return dueDate;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setSalesman(String salesman)
    {
        this.salesman = salesman;
    }

    public String getSalesman()
    {
        return salesman;
    }
    public void setSalesmanName(String salesmanName)
    {
        this.salesmanName = salesmanName;
    }

    public String getSalesmanName()
    {
        return salesmanName;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getCreateName()
    {
        return createName;
    }
    public void setCreateDept(String createDept)
    {
        this.createDept = createDept;
    }

    public String getCreateDept()
    {
        return createDept;
    }
    public void setCreateDeptName(String createDeptName)
    {
        this.createDeptName = createDeptName;
    }

    public String getCreateDeptName()
    {
        return createDeptName;
    }
    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }

    public String getUpdateName()
    {
        return updateName;
    }

    public List<FinanceApLoanDetail> getFinanceApLoanDetailList()
    {
        return financeApLoanDetailList;
    }

    public void setFinanceApLoanDetailList(List<FinanceApLoanDetail> financeApLoanDetailList)
    {
        this.financeApLoanDetailList = financeApLoanDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("billNo", getBillNo())
            .append("manufacturer", getManufacturer())
            .append("itemNo", getItemNo())
            .append("payType", getPayType())
            .append("crcyunit", getCrcyunit())
            .append("accountBy", getAccountBy())
            .append("accountName", getAccountName())
            .append("billDesc", getBillDesc())
            .append("dueDate", getDueDate())
            .append("status", getStatus())
            .append("salesman", getSalesman())
            .append("salesmanName", getSalesmanName())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("createDept", getCreateDept())
            .append("createDeptName", getCreateDeptName())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .append("financeApLoanDetailList", getFinanceApLoanDetailList())
            .toString();
    }
}
