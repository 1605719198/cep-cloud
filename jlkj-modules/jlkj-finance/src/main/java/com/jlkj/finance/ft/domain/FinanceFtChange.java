package com.jlkj.finance.ft.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 资产变动单主档对象 finance_ft_change
 *
 * @author jlkj
 * @date 2023-07-04
 */
public class FinanceFtChange extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 公司别 */
    private String companyId;

    /** 单据编号 */
    @Excel(name = "单据编号")
    private String changeNo;

    /** 单据名称 */
    @Excel(name = "单据名称")
    private String changeName;

    /** 单据类型 */
    private String changeType;

    /** 变动方式 */
    private String changeWay;

    /** 变动单据性质 */
    private String billNature;

    /** 变动日期 */
    private Date changeDate;

    /** 变动说明 */
    private String changeDesc;

    /** 资产类别 */
    private String assetType;

    /** 部门 */
    @Excel(name = "部门")
    private String applyDept;

    /** 新保管人 */
    private String newAssetUser;

    /** 申请人 */
    private String applyUser;

    /** 申请日期 */
    private Date applyDate;

    /** 单据状态 */
    private String status;

    /** 变动期间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "变动期间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date period;

    /** 实物审核人 */
    private String confirmUser;

    /** 实物审核日期 */
    private Date confirmDate;

    /** 审核人 */
    private String checkUser;

    /** 审核人日期 */
    private Date checkDate;

    /** 新增人姓名 */
    private String createName;

    /** 修改人姓名 */
    private String updateName;

    /** 资产变动单主档信息 */
    private List<FinanceFtChangeDetail> financeFtChangeDetailList;

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getUuid()
    {
        return uuid;
    }
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setChangeNo(String changeNo)
    {
        this.changeNo = changeNo;
    }

    public String getChangeNo()
    {
        return changeNo;
    }
    public void setChangeName(String changeName)
    {
        this.changeName = changeName;
    }

    public String getChangeName()
    {
        return changeName;
    }
    public void setChangeType(String changeType)
    {
        this.changeType = changeType;
    }

    public String getChangeType()
    {
        return changeType;
    }
    public void setChangeWay(String changeWay)
    {
        this.changeWay = changeWay;
    }

    public String getChangeWay()
    {
        return changeWay;
    }
    public void setBillNature(String billNature)
    {
        this.billNature = billNature;
    }

    public String getBillNature()
    {
        return billNature;
    }
    public void setChangeDate(Date changeDate)
    {
        this.changeDate = changeDate;
    }

    public Date getChangeDate()
    {
        return changeDate;
    }
    public void setChangeDesc(String changeDesc)
    {
        this.changeDesc = changeDesc;
    }

    public String getChangeDesc()
    {
        return changeDesc;
    }
    public void setAssetType(String assetType)
    {
        this.assetType = assetType;
    }

    public String getAssetType()
    {
        return assetType;
    }
    public void setApplyDept(String applyDept)
    {
        this.applyDept = applyDept;
    }

    public String getApplyDept()
    {
        return applyDept;
    }
    public void setNewAssetUser(String newAssetUser)
    {
        this.newAssetUser = newAssetUser;
    }

    public String getNewAssetUser()
    {
        return newAssetUser;
    }
    public void setApplyUser(String applyUser)
    {
        this.applyUser = applyUser;
    }

    public String getApplyUser()
    {
        return applyUser;
    }
    public void setApplyDate(Date applyDate)
    {
        this.applyDate = applyDate;
    }

    public Date getApplyDate()
    {
        return applyDate;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setPeriod(Date period)
    {
        this.period = period;
    }

    public Date getPeriod()
    {
        return period;
    }
    public void setConfirmUser(String confirmUser)
    {
        this.confirmUser = confirmUser;
    }

    public String getConfirmUser()
    {
        return confirmUser;
    }
    public void setConfirmDate(Date confirmDate)
    {
        this.confirmDate = confirmDate;
    }

    public Date getConfirmDate()
    {
        return confirmDate;
    }
    public void setCheckUser(String checkUser)
    {
        this.checkUser = checkUser;
    }

    public String getCheckUser()
    {
        return checkUser;
    }
    public void setCheckDate(Date checkDate)
    {
        this.checkDate = checkDate;
    }

    public Date getCheckDate()
    {
        return checkDate;
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

    public List<FinanceFtChangeDetail> getFinanceFtChangeDetailList()
    {
        return financeFtChangeDetailList;
    }

    public void setFinanceFtChangeDetailList(List<FinanceFtChangeDetail> financeFtChangeDetailList)
    {
        this.financeFtChangeDetailList = financeFtChangeDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("uuid", getUuid())
            .append("companyId", getCompanyId())
            .append("changeNo", getChangeNo())
            .append("changeName", getChangeName())
            .append("changeType", getChangeType())
            .append("changeWay", getChangeWay())
            .append("billNature", getBillNature())
            .append("changeDate", getChangeDate())
            .append("changeDesc", getChangeDesc())
            .append("assetType", getAssetType())
            .append("applyDept", getApplyDept())
            .append("newAssetUser", getNewAssetUser())
            .append("applyUser", getApplyUser())
            .append("applyDate", getApplyDate())
            .append("status", getStatus())
            .append("period", getPeriod())
            .append("confirmUser", getConfirmUser())
            .append("confirmDate", getConfirmDate())
            .append("checkUser", getCheckUser())
            .append("checkDate", getCheckDate())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .append("financeFtChangeDetailList", getFinanceFtChangeDetailList())
            .toString();
    }
}
