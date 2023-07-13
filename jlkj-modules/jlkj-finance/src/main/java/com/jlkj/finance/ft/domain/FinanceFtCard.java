package com.jlkj.finance.ft.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 固定资产卡片主档对象 finance_ft_card
 *
 * @author 116524
 * @date 2023-07-10
 */
public class FinanceFtCard extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 资产id */
    @Excel(name = "资产id")
    private String assetId;

    /** 公司别 */
    @Excel(name = "公司别")
    private String companyId;

    /** 资产编码 */
    @Excel(name = "资产编码")
    private String assetNo;

    /** 资产分类 */
    @Excel(name = "资产分类")
    private String assetGroup;

    /** 财务资产分类 */
    @Excel(name = "财务资产分类")
    private String finAssetGroup;

    /** 单据类型 */
    @Excel(name = "单据类型")
    private BigDecimal changePrice;

    /** 资产科目 */
    @Excel(name = "资产科目")
    private String accountCode;

    /** 资产名称 */
    @Excel(name = "资产名称")
    private String assetName;

    /** 原企业号码 */
    @Excel(name = "原企业号码")
    private String deptAssetNo;

    /** 资产备码 */
    @Excel(name = "资产备码")
    private String assetBakNo;

    /** 产权单位 */
    @Excel(name = "产权单位")
    private String deptNo;

    /** 资产保管人 */
    @Excel(name = "资产保管人")
    private String assetUser;

    /** 资产保管人部门 */
    @Excel(name = "资产保管人部门")
    private String userDept;

    /** 成本中心 */
    @Excel(name = "成本中心")
    private String costCenter;

    /** 使用期限 */
    @Excel(name = "使用期限")
    private Long usableMonth;

    /** 入帐前已使用期间数 */
    @Excel(name = "入帐前已使用期间数")
    private Long usedMonth;

    /** 开始使用日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始使用日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 存放地点 */
    @Excel(name = "存放地点")
    private String location;

    /** 数量 */
    @Excel(name = "数量")
    private Long qty;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private String unit;

    /** 资金来源 */
    @Excel(name = "资金来源")
    private String source;

    /** 资产性质 */
    @Excel(name = "资产性质")
    private String assetCharacter;

    /** 资产规格 */
    @Excel(name = "资产规格")
    private String assetSpec;

    /** 资产原值 */
    @Excel(name = "资产原值")
    private BigDecimal orgValue;

    /** 资产取得价值 */
    @Excel(name = "资产取得价值")
    private BigDecimal getValue;

    /** 入帐前累计已提折旧 */
    @Excel(name = "入帐前累计已提折旧")
    private BigDecimal beforeAccuDepr;

    /** 月折旧率 */
    @Excel(name = "月折旧率")
    private BigDecimal monthDeprRate;

    /** 月折旧额 */
    @Excel(name = "月折旧额")
    private BigDecimal monthDeprAmt;

    /** 累计折旧 */
    @Excel(name = "累计折旧")
    private BigDecimal accuDepr;

    /** 减值准备 */
    @Excel(name = "减值准备")
    private BigDecimal minusPrepare;

    /** 残值率 */
    @Excel(name = "残值率")
    private BigDecimal scrapRate;

    /** 预计净残值 */
    @Excel(name = "预计净残值")
    private BigDecimal scrapAmt;

    /** 是否被清理 */
    @Excel(name = "是否被清理")
    private String isCleared;

    /** 增加方式代码 */
    @Excel(name = "增加方式代码")
    private String addCauseCode;

    /** 增加期间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "增加期间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date addPeriod;

    /** 增加日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "增加日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date addDate;

    /** 减少方式代码 */
    @Excel(name = "减少方式代码")
    private String clearCauseCode;

    /** 减少期间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "减少期间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date clearPeriod;

    /** 减少日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "减少日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date clearDate;

    /** 减少人 */
    @Excel(name = "减少人")
    private String clearUser;

    /** 合同号 */
    @Excel(name = "合同号")
    private String contractNo;

    /** 预算编号 */
    @Excel(name = "预算编号")
    private String budgetNo;

    /** 供应商 */
    @Excel(name = "供应商")
    private String supplyerNo;

    /** 制造商 */
    @Excel(name = "制造商")
    private String manufacturer;

    /** 重量 */
    @Excel(name = "重量")
    private BigDecimal weight;

    /** 容量 */
    @Excel(name = "容量")
    private BigDecimal capacity;

    /** 长度 */
    @Excel(name = "长度")
    private BigDecimal length;

    /** 建筑面积(m2) */
    @Excel(name = "建筑面积(m2)")
    private BigDecimal buildingArea;

    /** 占地面积(m2) */
    @Excel(name = "占地面积(m2)")
    private BigDecimal floorArea;

    /** 厂房层数 */
    @Excel(name = "厂房层数")
    private Long layerNum;

    /** 经济用途 */
    @Excel(name = "经济用途")
    private String economyPurpose;

    /** 装备水平 */
    @Excel(name = "装备水平")
    private String levels;

    /** 资产说明 */
    @Excel(name = "资产说明")
    private String assetDesc;

    /** 产品编码 */
    @Excel(name = "产品编码")
    private String product;

    /** 卡片模式 */
    @Excel(name = "卡片模式")
    private String cardModel;

    /** 来源程式 */
    @Excel(name = "来源程式")
    private String pgrmId;

    /** 新增人姓名 */
    @Excel(name = "新增人姓名")
    private String createName;

    /** 修改人姓名 */
    @Excel(name = "修改人姓名")
    private String updateName;

    public void setAssetId(String assetId)
    {
        this.assetId = assetId;
    }

    public String getAssetId()
    {
        return assetId;
    }
    public void setCompanyId(String companyId)
    {
        this.companyId = companyId;
    }

    public String getCompanyId()
    {
        return companyId;
    }
    public void setAssetNo(String assetNo)
    {
        this.assetNo = assetNo;
    }

    public String getAssetNo()
    {
        return assetNo;
    }
    public void setAssetGroup(String assetGroup)
    {
        this.assetGroup = assetGroup;
    }

    public String getAssetGroup()
    {
        return assetGroup;
    }
    public void setFinAssetGroup(String finAssetGroup)
    {
        this.finAssetGroup = finAssetGroup;
    }

    public String getFinAssetGroup()
    {
        return finAssetGroup;
    }
    public void setChangePrice(BigDecimal changePrice)
    {
        this.changePrice = changePrice;
    }

    public BigDecimal getChangePrice()
    {
        return changePrice;
    }
    public void setAccountCode(String accountCode)
    {
        this.accountCode = accountCode;
    }

    public String getAccountCode()
    {
        return accountCode;
    }
    public void setAssetName(String assetName)
    {
        this.assetName = assetName;
    }

    public String getAssetName()
    {
        return assetName;
    }
    public void setDeptAssetNo(String deptAssetNo)
    {
        this.deptAssetNo = deptAssetNo;
    }

    public String getDeptAssetNo()
    {
        return deptAssetNo;
    }
    public void setAssetBakNo(String assetBakNo)
    {
        this.assetBakNo = assetBakNo;
    }

    public String getAssetBakNo()
    {
        return assetBakNo;
    }
    public void setDeptNo(String deptNo)
    {
        this.deptNo = deptNo;
    }

    public String getDeptNo()
    {
        return deptNo;
    }
    public void setAssetUser(String assetUser)
    {
        this.assetUser = assetUser;
    }

    public String getAssetUser()
    {
        return assetUser;
    }
    public void setUserDept(String userDept)
    {
        this.userDept = userDept;
    }

    public String getUserDept()
    {
        return userDept;
    }
    public void setCostCenter(String costCenter)
    {
        this.costCenter = costCenter;
    }

    public String getCostCenter()
    {
        return costCenter;
    }
    public void setUsableMonth(Long usableMonth)
    {
        this.usableMonth = usableMonth;
    }

    public Long getUsableMonth()
    {
        return usableMonth;
    }
    public void setUsedMonth(Long usedMonth)
    {
        this.usedMonth = usedMonth;
    }

    public Long getUsedMonth()
    {
        return usedMonth;
    }
    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getStartDate()
    {
        return startDate;
    }
    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getLocation()
    {
        return location;
    }
    public void setQty(Long qty)
    {
        this.qty = qty;
    }

    public Long getQty()
    {
        return qty;
    }
    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return unit;
    }
    public void setSource(String source)
    {
        this.source = source;
    }

    public String getSource()
    {
        return source;
    }
    public void setAssetCharacter(String assetCharacter)
    {
        this.assetCharacter = assetCharacter;
    }

    public String getAssetCharacter()
    {
        return assetCharacter;
    }
    public void setAssetSpec(String assetSpec)
    {
        this.assetSpec = assetSpec;
    }

    public String getAssetSpec()
    {
        return assetSpec;
    }
    public void setOrgValue(BigDecimal orgValue)
    {
        this.orgValue = orgValue;
    }

    public BigDecimal getOrgValue()
    {
        return orgValue;
    }
    public void setGetValue(BigDecimal getValue)
    {
        this.getValue = getValue;
    }

    public BigDecimal getGetValue()
    {
        return getValue;
    }
    public void setBeforeAccuDepr(BigDecimal beforeAccuDepr)
    {
        this.beforeAccuDepr = beforeAccuDepr;
    }

    public BigDecimal getBeforeAccuDepr()
    {
        return beforeAccuDepr;
    }
    public void setMonthDeprRate(BigDecimal monthDeprRate)
    {
        this.monthDeprRate = monthDeprRate;
    }

    public BigDecimal getMonthDeprRate()
    {
        return monthDeprRate;
    }
    public void setMonthDeprAmt(BigDecimal monthDeprAmt)
    {
        this.monthDeprAmt = monthDeprAmt;
    }

    public BigDecimal getMonthDeprAmt()
    {
        return monthDeprAmt;
    }
    public void setAccuDepr(BigDecimal accuDepr)
    {
        this.accuDepr = accuDepr;
    }

    public BigDecimal getAccuDepr()
    {
        return accuDepr;
    }
    public void setMinusPrepare(BigDecimal minusPrepare)
    {
        this.minusPrepare = minusPrepare;
    }

    public BigDecimal getMinusPrepare()
    {
        return minusPrepare;
    }
    public void setScrapRate(BigDecimal scrapRate)
    {
        this.scrapRate = scrapRate;
    }

    public BigDecimal getScrapRate()
    {
        return scrapRate;
    }
    public void setScrapAmt(BigDecimal scrapAmt)
    {
        this.scrapAmt = scrapAmt;
    }

    public BigDecimal getScrapAmt()
    {
        return scrapAmt;
    }
    public void setIsCleared(String isCleared)
    {
        this.isCleared = isCleared;
    }

    public String getIsCleared()
    {
        return isCleared;
    }
    public void setAddCauseCode(String addCauseCode)
    {
        this.addCauseCode = addCauseCode;
    }

    public String getAddCauseCode()
    {
        return addCauseCode;
    }
    public void setAddPeriod(Date addPeriod)
    {
        this.addPeriod = addPeriod;
    }

    public Date getAddPeriod()
    {
        return addPeriod;
    }
    public void setAddDate(Date addDate)
    {
        this.addDate = addDate;
    }

    public Date getAddDate()
    {
        return addDate;
    }
    public void setClearCauseCode(String clearCauseCode)
    {
        this.clearCauseCode = clearCauseCode;
    }

    public String getClearCauseCode()
    {
        return clearCauseCode;
    }
    public void setClearPeriod(Date clearPeriod)
    {
        this.clearPeriod = clearPeriod;
    }

    public Date getClearPeriod()
    {
        return clearPeriod;
    }
    public void setClearDate(Date clearDate)
    {
        this.clearDate = clearDate;
    }

    public Date getClearDate()
    {
        return clearDate;
    }
    public void setClearUser(String clearUser)
    {
        this.clearUser = clearUser;
    }

    public String getClearUser()
    {
        return clearUser;
    }
    public void setContractNo(String contractNo)
    {
        this.contractNo = contractNo;
    }

    public String getContractNo()
    {
        return contractNo;
    }
    public void setBudgetNo(String budgetNo)
    {
        this.budgetNo = budgetNo;
    }

    public String getBudgetNo()
    {
        return budgetNo;
    }
    public void setSupplyerNo(String supplyerNo)
    {
        this.supplyerNo = supplyerNo;
    }

    public String getSupplyerNo()
    {
        return supplyerNo;
    }
    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }
    public void setWeight(BigDecimal weight)
    {
        this.weight = weight;
    }

    public BigDecimal getWeight()
    {
        return weight;
    }
    public void setCapacity(BigDecimal capacity)
    {
        this.capacity = capacity;
    }

    public BigDecimal getCapacity()
    {
        return capacity;
    }
    public void setLength(BigDecimal length)
    {
        this.length = length;
    }

    public BigDecimal getLength()
    {
        return length;
    }
    public void setBuildingArea(BigDecimal buildingArea)
    {
        this.buildingArea = buildingArea;
    }

    public BigDecimal getBuildingArea()
    {
        return buildingArea;
    }
    public void setFloorArea(BigDecimal floorArea)
    {
        this.floorArea = floorArea;
    }

    public BigDecimal getFloorArea()
    {
        return floorArea;
    }
    public void setLayerNum(Long layerNum)
    {
        this.layerNum = layerNum;
    }

    public Long getLayerNum()
    {
        return layerNum;
    }
    public void setEconomyPurpose(String economyPurpose)
    {
        this.economyPurpose = economyPurpose;
    }

    public String getEconomyPurpose()
    {
        return economyPurpose;
    }
    public void setLevels(String levels)
    {
        this.levels = levels;
    }

    public String getLevels()
    {
        return levels;
    }
    public void setAssetDesc(String assetDesc)
    {
        this.assetDesc = assetDesc;
    }

    public String getAssetDesc()
    {
        return assetDesc;
    }
    public void setProduct(String product)
    {
        this.product = product;
    }

    public String getProduct()
    {
        return product;
    }
    public void setCardModel(String cardModel)
    {
        this.cardModel = cardModel;
    }

    public String getCardModel()
    {
        return cardModel;
    }
    public void setPgrmId(String pgrmId)
    {
        this.pgrmId = pgrmId;
    }

    public String getPgrmId()
    {
        return pgrmId;
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
            .append("assetId", getAssetId())
            .append("companyId", getCompanyId())
            .append("assetNo", getAssetNo())
            .append("assetGroup", getAssetGroup())
            .append("finAssetGroup", getFinAssetGroup())
            .append("changePrice", getChangePrice())
            .append("accountCode", getAccountCode())
            .append("assetName", getAssetName())
            .append("deptAssetNo", getDeptAssetNo())
            .append("assetBakNo", getAssetBakNo())
            .append("deptNo", getDeptNo())
            .append("assetUser", getAssetUser())
            .append("userDept", getUserDept())
            .append("costCenter", getCostCenter())
            .append("usableMonth", getUsableMonth())
            .append("usedMonth", getUsedMonth())
            .append("startDate", getStartDate())
            .append("location", getLocation())
            .append("qty", getQty())
            .append("unit", getUnit())
            .append("source", getSource())
            .append("character", getAssetCharacter())
            .append("assetSpec", getAssetSpec())
            .append("orgValue", getOrgValue())
            .append("getValue", getGetValue())
            .append("beforeAccuDepr", getBeforeAccuDepr())
            .append("monthDeprRate", getMonthDeprRate())
            .append("monthDeprAmt", getMonthDeprAmt())
            .append("accuDepr", getAccuDepr())
            .append("minusPrepare", getMinusPrepare())
            .append("scrapRate", getScrapRate())
            .append("scrapAmt", getScrapAmt())
            .append("isCleared", getIsCleared())
            .append("addCauseCode", getAddCauseCode())
            .append("addPeriod", getAddPeriod())
            .append("addDate", getAddDate())
            .append("clearCauseCode", getClearCauseCode())
            .append("clearPeriod", getClearPeriod())
            .append("clearDate", getClearDate())
            .append("clearUser", getClearUser())
            .append("contractNo", getContractNo())
            .append("budgetNo", getBudgetNo())
            .append("supplyerNo", getSupplyerNo())
            .append("manufacturer", getManufacturer())
            .append("weight", getWeight())
            .append("capacity", getCapacity())
            .append("length", getLength())
            .append("buildingArea", getBuildingArea())
            .append("floorArea", getFloorArea())
            .append("layerNum", getLayerNum())
            .append("economyPurpose", getEconomyPurpose())
            .append("levels", getLevels())
            .append("assetDesc", getAssetDesc())
            .append("product", getProduct())
            .append("cardModel", getCardModel())
            .append("pgrmId", getPgrmId())
            .append("createBy", getCreateBy())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateName", getUpdateName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
