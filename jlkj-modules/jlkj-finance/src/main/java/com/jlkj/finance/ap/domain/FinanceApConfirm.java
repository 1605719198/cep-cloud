package com.jlkj.finance.ap.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 会计确认主档对象 finance_ap_confirm
 *
 * @author 265799
 * @date 2023-07-10
 */
public class FinanceApConfirm extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    @Excel(name = "公司别")
    private String companyId;

    /** 应付凭单号 */
    @Excel(name = "应付凭单号")
    private String billNo;

    /** 厂商编码id */
    @Excel(name = "厂商编码id")
    private String manufacturerId;

    /** 厂商编码 */
    @Excel(name = "厂商编码")
    private String manufacturerNo;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String contractNo;

    /** 供应商 */
    @Excel(name = "供应商")
    private String suppId;

    /** 供货商编码 */
    @Excel(name = "供货商编码")
    private String suppNo;

    /** 期望付款方式 */
    @Excel(name = "期望付款方式")
    private String payType;

    /** 来源系统代码 */
    @Excel(name = "来源系统代码")
    private String fromApid;

    /** 来源程式代码 */
    @Excel(name = "来源程式代码")
    private String fromPgrmid;

    /** 会计确认日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "会计确认日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date accountDate;

    /** 币别 */
    @Excel(name = "币别")
    private String crcyUnit;

    /** 转账凭证号 */
    @Excel(name = "转账凭证号")
    private String mvoucherNo;

    /** 付款日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "付款日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payVoucherDate;

    /** 付款凭证 */
    @Excel(name = "付款凭证")
    private String payVoucherNo;

    /** 摘要 */
    @Excel(name = "摘要")
    private String billDesc;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 业务类别 */
    @Excel(name = "业务类别")
    private String businessType;

    /** 材料类别 */
    @Excel(name = "材料类别")
    private String materialType;

    /** 新增（经办）人名称 */
    @Excel(name = "新增", readConverterExp = "经=办")
    private String createName;

    /** 新增人部门 */
    @Excel(name = "新增人部门")
    private String createDept;

    /** 新增人部门名称 */
    @Excel(name = "新增人部门名称")
    private String createDeptName;

    /** 异动名称 */
    @Excel(name = "异动名称")
    private String updateName;

    /** 到期日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueDate;

    /** 已付款记账金额 */
    @Excel(name = "已付款记账金额")
    private BigDecimal paidAmt;

    /** 已调度记账金额 */
    @Excel(name = "已调度记账金额")
    private BigDecimal dispatchAmt;

    /** 会计确认明细信息 */
    private List<FinanceApConfirmDetail> financeApConfirmDetailList;

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
    public void setManufacturerId(String manufacturerId)
    {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerId()
    {
        return manufacturerId;
    }
    public void setManufacturerNo(String manufacturerNo)
    {
        this.manufacturerNo = manufacturerNo;
    }

    public String getManufacturerNo()
    {
        return manufacturerNo;
    }
    public void setContractNo(String contractNo)
    {
        this.contractNo = contractNo;
    }

    public String getContractNo()
    {
        return contractNo;
    }
    public void setSuppId(String suppId)
    {
        this.suppId = suppId;
    }

    public String getSuppId()
    {
        return suppId;
    }
    public void setSuppNo(String suppNo)
    {
        this.suppNo = suppNo;
    }

    public String getSuppNo()
    {
        return suppNo;
    }
    public void setPayType(String payType)
    {
        this.payType = payType;
    }

    public String getPayType()
    {
        return payType;
    }
    public void setFromApid(String fromApid)
    {
        this.fromApid = fromApid;
    }

    public String getFromApid()
    {
        return fromApid;
    }
    public void setFromPgrmid(String fromPgrmid)
    {
        this.fromPgrmid = fromPgrmid;
    }

    public String getFromPgrmid()
    {
        return fromPgrmid;
    }
    public void setAccountDate(Date accountDate)
    {
        this.accountDate = accountDate;
    }

    public Date getAccountDate()
    {
        return accountDate;
    }
    public void setCrcyUnit(String crcyUnit)
    {
        this.crcyUnit = crcyUnit;
    }

    public String getCrcyUnit()
    {
        return crcyUnit;
    }
    public void setMvoucherNo(String mvoucherNo)
    {
        this.mvoucherNo = mvoucherNo;
    }

    public String getMvoucherNo()
    {
        return mvoucherNo;
    }
    public void setPayVoucherDate(Date payVoucherDate)
    {
        this.payVoucherDate = payVoucherDate;
    }

    public Date getPayVoucherDate()
    {
        return payVoucherDate;
    }
    public void setPayVoucherNo(String payVoucherNo)
    {
        this.payVoucherNo = payVoucherNo;
    }

    public String getPayVoucherNo()
    {
        return payVoucherNo;
    }
    public void setBillDesc(String billDesc)
    {
        this.billDesc = billDesc;
    }

    public String getBillDesc()
    {
        return billDesc;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setBusinessType(String businessType)
    {
        this.businessType = businessType;
    }

    public String getBusinessType()
    {
        return businessType;
    }
    public void setMaterialType(String materialType)
    {
        this.materialType = materialType;
    }

    public String getMaterialType()
    {
        return materialType;
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
    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    public Date getDueDate()
    {
        return dueDate;
    }
    public void setPaidAmt(BigDecimal paidAmt)
    {
        this.paidAmt = paidAmt;
    }

    public BigDecimal getPaidAmt()
    {
        return paidAmt;
    }
    public void setDispatchAmt(BigDecimal dispatchAmt)
    {
        this.dispatchAmt = dispatchAmt;
    }

    public BigDecimal getDispatchAmt()
    {
        return dispatchAmt;
    }

    public List<FinanceApConfirmDetail> getFinanceApConfirmDetailList()
    {
        return financeApConfirmDetailList;
    }

    public void setFinanceApConfirmDetailList(List<FinanceApConfirmDetail> financeApConfirmDetailList)
    {
        this.financeApConfirmDetailList = financeApConfirmDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("companyId", getCompanyId())
            .append("billNo", getBillNo())
            .append("manufacturerId", getManufacturerId())
            .append("manufacturerNo", getManufacturerNo())
            .append("contractNo", getContractNo())
            .append("suppId", getSuppId())
            .append("suppNo", getSuppNo())
            .append("payType", getPayType())
            .append("fromApid", getFromApid())
            .append("fromPgrmid", getFromPgrmid())
            .append("accountDate", getAccountDate())
            .append("crcyUnit", getCrcyUnit())
            .append("mvoucherNo", getMvoucherNo())
            .append("payVoucherDate", getPayVoucherDate())
            .append("payVoucherNo", getPayVoucherNo())
            .append("billDesc", getBillDesc())
            .append("status", getStatus())
            .append("businessType", getBusinessType())
            .append("materialType", getMaterialType())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("createDept", getCreateDept())
            .append("createDeptName", getCreateDeptName())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .append("dueDate", getDueDate())
            .append("paidAmt", getPaidAmt())
            .append("dispatchAmt", getDispatchAmt())
            .append("financeApConfirmDetailList", getFinanceApConfirmDetailList())
            .toString();
    }
}
