package com.jlkj.finance.gp.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 销售管理-厂商基本资料对象 finance_gp_manufacturer_base
 *
 * @author 265799
 * @date 2023-04-08
 */
public class FinanceGpManufacturerBase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 公司别 */
    private String compId;

    /** 厂商编码 */
    @Excel(name = "厂商编码")
    private String manufacturerId;

    /** 国别 */
    private String countryId;

    /** 国别名称 */
    private String countryName;

    /** 厂商类别 */
    private String catalog;

    /** 厂商类别名称 */
    private String catalogName;

    /** 地区码 */
    private String areaId;

    /** 地区名称 */
    private String areaName;

    /** 中文名称 */
    @Excel(name = "中文名称")
    private String manufacturerChineseName;

    /** 英文名称 */
    @Excel(name = "英文名称")
    private String manufacturerEnglishName;

    /** 厂商简称 */
    @Excel(name = "厂商简称")
    private String manufacturerShortName;

    /** 税号 */
    private String taxNo;

    /** 负责人 */
    private String owner;

    /** 负责人职称 */
    private String ownerTitle;

    /** 资本额 */
    private BigDecimal capital;

    /** 资本额币别码 */
    private String capitalCurrencyCode;

    /** 营业额 */
    private BigDecimal turnover;

    /** 营业额币别 */
    private String turnoverCurrencyCode;

    /** 建立人员 */
    private String createUser;

    /** 修改人员 */
    private String updateUser;

    /** 旧厂商编号 */
    private String originaManufacturerId;

    /** 发票地址 */
    private String billAddress;

    /** 邮递区号（发票地址） */
    private String billZipCode;

    /** 邮递区号中文描述 */
    private String billZipDesc;

    /** 网址 */
    private String web;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 相关性 */
    private String corelation;

    /** 专家 */
    private String professional;

    /** 地区码 */
    private String mainAreaId;

    /** 地区码说明 */
    private String mainAreaDesc;

    /** 详细地区码 */
    private String detailAreaId;

    /** 详细地区码说明 */
    private String detailAreaDesc;

    /** 集团归属 */
    private String groupClass;

    /** 关联方 */
    private String relatedParty;

    /** 关联方细项 */
    private String relatedPartyItem;

    /** 部门代码 */
    private String departmentCode;

    /** 清洗状态 */
    private String clearStatus;

    /** 是否集团集中采购 */
    private String isCentralizedPurchase;

    /** 原中文名称 */
    private String originalManufacturerChineseName;

    /** 上层节点id */
    private String parentId;
    /** 起始时间 */
    private  String startDate;
    /** 结束时间 */
    private  String endDate;
    /** 逻辑删除标识 */
    private Long delFlag;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getStartDate()
    {
        return startDate;
    }
    public void setEndDate(String startDate)
    {
        this.endDate = endDate;
    }

    public String getEndDate()
    {
        return endDate;
    }
    public void setCompId(String compId)
    {
        this.compId = compId;
    }

    public String getCompId()
    {
        return compId;
    }
    public void setManufacturerId(String manufacturerId)
    {
        this.manufacturerId = manufacturerId;
    }

    public String getManufacturerId()
    {
        return manufacturerId;
    }
    public void setCountryId(String countryId)
    {
        this.countryId = countryId;
    }

    public String getCountryId()
    {
        return countryId;
    }
    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    public String getCountryName()
    {
        return countryName;
    }
    public void setCatalog(String catalog)
    {
        this.catalog = catalog;
    }

    public String getCatalog()
    {
        return catalog;
    }
    public void setCatalogName(String catalogName)
    {
        this.catalogName = catalogName;
    }

    public String getCatalogName()
    {
        return catalogName;
    }
    public void setAreaId(String areaId)
    {
        this.areaId = areaId;
    }

    public String getAreaId()
    {
        return areaId;
    }
    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }

    public String getAreaName()
    {
        return areaName;
    }
    public void setManufacturerChineseName(String manufacturerChineseName)
    {
        this.manufacturerChineseName = manufacturerChineseName;
    }

    public String getManufacturerChineseName()
    {
        return manufacturerChineseName;
    }
    public void setManufacturerEnglishName(String manufacturerEnglishName)
    {
        this.manufacturerEnglishName = manufacturerEnglishName;
    }

    public String getManufacturerEnglishName()
    {
        return manufacturerEnglishName;
    }
    public void setManufacturerShortName(String manufacturerShortName)
    {
        this.manufacturerShortName = manufacturerShortName;
    }

    public String getManufacturerShortName()
    {
        return manufacturerShortName;
    }
    public void setTaxNo(String taxNo)
    {
        this.taxNo = taxNo;
    }

    public String getTaxNo()
    {
        return taxNo;
    }
    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getOwner()
    {
        return owner;
    }
    public void setOwnerTitle(String ownerTitle)
    {
        this.ownerTitle = ownerTitle;
    }

    public String getOwnerTitle()
    {
        return ownerTitle;
    }
    public void setCapital(BigDecimal capital)
    {
        this.capital = capital;
    }

    public BigDecimal getCapital()
    {
        return capital;
    }
    public void setCapitalCurrencyCode(String capitalCurrencyCode)
    {
        this.capitalCurrencyCode = capitalCurrencyCode;
    }

    public String getCapitalCurrencyCode()
    {
        return capitalCurrencyCode;
    }
    public void setTurnover(BigDecimal turnover)
    {
        this.turnover = turnover;
    }

    public BigDecimal getTurnover()
    {
        return turnover;
    }
    public void setTurnoverCurrencyCode(String turnoverCurrencyCode)
    {
        this.turnoverCurrencyCode = turnoverCurrencyCode;
    }

    public String getTurnoverCurrencyCode()
    {
        return turnoverCurrencyCode;
    }
    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    public String getCreateUser()
    {
        return createUser;
    }
    public void setUpdateUser(String updateUser)
    {
        this.updateUser = updateUser;
    }

    public String getUpdateUser()
    {
        return updateUser;
    }
    public void setOriginaManufacturerId(String originaManufacturerId)
    {
        this.originaManufacturerId = originaManufacturerId;
    }

    public String getOriginaManufacturerId()
    {
        return originaManufacturerId;
    }
    public void setBillAddress(String billAddress)
    {
        this.billAddress = billAddress;
    }

    public String getBillAddress()
    {
        return billAddress;
    }
    public void setBillZipCode(String billZipCode)
    {
        this.billZipCode = billZipCode;
    }

    public String getBillZipCode()
    {
        return billZipCode;
    }
    public void setBillZipDesc(String billZipDesc)
    {
        this.billZipDesc = billZipDesc;
    }

    public String getBillZipDesc()
    {
        return billZipDesc;
    }
    public void setWeb(String web)
    {
        this.web = web;
    }

    public String getWeb()
    {
        return web;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setCorelation(String corelation)
    {
        this.corelation = corelation;
    }

    public String getCorelation()
    {
        return corelation;
    }
    public void setProfessional(String professional)
    {
        this.professional = professional;
    }

    public String getProfessional()
    {
        return professional;
    }
    public void setMainAreaId(String mainAreaId)
    {
        this.mainAreaId = mainAreaId;
    }

    public String getMainAreaId()
    {
        return mainAreaId;
    }
    public void setMainAreaDesc(String mainAreaDesc)
    {
        this.mainAreaDesc = mainAreaDesc;
    }

    public String getMainAreaDesc()
    {
        return mainAreaDesc;
    }
    public void setDetailAreaId(String detailAreaId)
    {
        this.detailAreaId = detailAreaId;
    }

    public String getDetailAreaId()
    {
        return detailAreaId;
    }
    public void setDetailAreaDesc(String detailAreaDesc)
    {
        this.detailAreaDesc = detailAreaDesc;
    }

    public String getDetailAreaDesc()
    {
        return detailAreaDesc;
    }
    public void setGroupClass(String groupClass)
    {
        this.groupClass = groupClass;
    }

    public String getGroupClass()
    {
        return groupClass;
    }
    public void setRelatedParty(String relatedParty)
    {
        this.relatedParty = relatedParty;
    }

    public String getRelatedParty()
    {
        return relatedParty;
    }
    public void setRelatedPartyItem(String relatedPartyItem)
    {
        this.relatedPartyItem = relatedPartyItem;
    }

    public String getRelatedPartyItem()
    {
        return relatedPartyItem;
    }
    public void setDepartmentCode(String departmentCode)
    {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentCode()
    {
        return departmentCode;
    }
    public void setClearStatus(String clearStatus)
    {
        this.clearStatus = clearStatus;
    }

    public String getClearStatus()
    {
        return clearStatus;
    }
    public void setIsCentralizedPurchase(String isCentralizedPurchase)
    {
        this.isCentralizedPurchase = isCentralizedPurchase;
    }

    public String getIsCentralizedPurchase()
    {
        return isCentralizedPurchase;
    }
    public void setOriginalManufacturerChineseName(String originalManufacturerChineseName)
    {
        this.originalManufacturerChineseName = originalManufacturerChineseName;
    }

    public String getOriginalManufacturerChineseName()
    {
        return originalManufacturerChineseName;
    }
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getParentId()
    {
        return parentId;
    }
    public void setDelFlag(Long delFlag)
    {
        this.delFlag = delFlag;
    }

    public Long getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("compId", getCompId())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("manufacturerId", getManufacturerId())
            .append("countryId", getCountryId())
            .append("countryName", getCountryName())
            .append("catalog", getCatalog())
            .append("catalogName", getCatalogName())
            .append("areaId", getAreaId())
            .append("areaName", getAreaName())
            .append("manufacturerChineseName", getManufacturerChineseName())
            .append("manufacturerEnglishName", getManufacturerEnglishName())
            .append("manufacturerShortName", getManufacturerShortName())
            .append("taxNo", getTaxNo())
            .append("owner", getOwner())
            .append("ownerTitle", getOwnerTitle())
            .append("capital", getCapital())
            .append("capitalCurrencyCode", getCapitalCurrencyCode())
            .append("turnover", getTurnover())
            .append("turnoverCurrencyCode", getTurnoverCurrencyCode())
            .append("createUser", getCreateUser())
            .append("updateUser", getUpdateUser())
            .append("originaManufacturerId", getOriginaManufacturerId())
            .append("billAddress", getBillAddress())
            .append("billZipCode", getBillZipCode())
            .append("billZipDesc", getBillZipDesc())
            .append("web", getWeb())
            .append("status", getStatus())
            .append("corelation", getCorelation())
            .append("professional", getProfessional())
            .append("mainAreaId", getMainAreaId())
            .append("mainAreaDesc", getMainAreaDesc())
            .append("detailAreaId", getDetailAreaId())
            .append("detailAreaDesc", getDetailAreaDesc())
            .append("groupClass", getGroupClass())
            .append("relatedParty", getRelatedParty())
            .append("relatedPartyItem", getRelatedPartyItem())
            .append("departmentCode", getDepartmentCode())
            .append("clearStatus", getClearStatus())
            .append("isCentralizedPurchase", getIsCentralizedPurchase())
            .append("originalManufacturerChineseName", getOriginalManufacturerChineseName())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("parentId", getParentId())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
