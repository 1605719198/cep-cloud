package com.jlkj.finance.ft.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.jlkj.common.core.annotation.Excel;
import com.jlkj.common.core.web.domain.BaseEntity;

/**
 * 资产变动单主档对象 finance_ft_change_detail
 *
 * @author jlkj
 * @date 2023-07-04
 */
public class FinanceFtChangeDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String uuid;

    /** 公司别 */
    @Excel(name = "公司别")
    private String companyId;

    /** 变动单id */
    @Excel(name = "变动单id")
    private String changeId;

    /** 单据编号 */
    @Excel(name = "单据编号")
    private String changeNo;

    /** 变动期间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "变动期间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date period;

    /** 单据类型 */
    @Excel(name = "单据类型")
    private BigDecimal changePrice;

    /** 资产id */
    @Excel(name = "资产id")
    private String assetId;

    /** 资产编码 */
    @Excel(name = "资产编码")
    private String assetNo;

    /** 资产分类 */
    @Excel(name = "资产分类")
    private String assetGroup;

    /** 新资产分类 */
    @Excel(name = "新资产分类")
    private String newAssetGroup;

    /** 资产名称 */
    @Excel(name = "资产名称")
    private String assetName;

    /** 新资产名称 */
    @Excel(name = "新资产名称")
    private String newAssetName;

    /** 资产说明 */
    @Excel(name = "资产说明")
    private String assetDesc;

    /** 资产类别 */
    @Excel(name = "资产类别")
    private String assetType;

    /** 资产原值 */
    @Excel(name = "资产原值")
    private BigDecimal orgValue;

    /** 资产取得价值 */
    @Excel(name = "资产取得价值")
    private BigDecimal getValue;

    /** 累计折旧 */
    @Excel(name = "累计折旧")
    private BigDecimal accuDepr;

    /** 折旧年限 */
    @Excel(name = "折旧年限")
    private Long deprCount;

    /** 新折旧年限 */
    @Excel(name = "新折旧年限")
    private Long newDeprCount;

    /** 归属部门 */
    @Excel(name = "归属部门")
    private String deptNo;

    /** 新归属部门 */
    @Excel(name = "新归属部门")
    private String newDeptNo;

    /** 资产保管人 */
    @Excel(name = "资产保管人")
    private String assetUser;

    /** 新资产保管人 */
    @Excel(name = "新资产保管人")
    private String newAssetUser;

    /** 资产保管人部门 */
    @Excel(name = "资产保管人部门")
    private String userDept;

    /** 新资产保管人部门 */
    @Excel(name = "新资产保管人部门")
    private String newUserDept;

    /** 数量 */
    @Excel(name = "数量")
    private Long qty;

    /** 新数量 */
    @Excel(name = "新数量")
    private Long newQty;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private String unit;

    /** 新计量单位 */
    @Excel(name = "新计量单位")
    private String newUnit;

    /** 使用状态 */
    @Excel(name = "使用状态")
    private String assetStatus;

    /** 新使用状态 */
    @Excel(name = "新使用状态")
    private String newAssetStatus;

    /** 存放地点 */
    @Excel(name = "存放地点")
    private String location;

    /** 新存放地点 */
    @Excel(name = "新存放地点")
    private String newLocation;

    /** 资产规格 */
    @Excel(name = "资产规格")
    private String assetSpec;

    /** 新资产规格 */
    @Excel(name = "新资产规格")
    private String newAssetSpec;

    /** 合同号 */
    @Excel(name = "合同号")
    private String contractNo;

    /** 预算编号 */
    @Excel(name = "预算编号")
    private String budgetNo;

    /** 供应商 */
    @Excel(name = "供应商")
    private String supplyerNo;

    /** 新供应商 */
    @Excel(name = "新供应商")
    private String newSupplyerNo;

    /** 制造商 */
    @Excel(name = "制造商")
    private String manufacturer;

    /** 新制造商 */
    @Excel(name = "新制造商")
    private String newManufacturer;

    /** 重量 */
    @Excel(name = "重量")
    private BigDecimal weight;

    /** 新重量 */
    @Excel(name = "新重量")
    private BigDecimal newWeight;

    /** 容量 */
    @Excel(name = "容量")
    private BigDecimal capacity;

    /** 新容量 */
    @Excel(name = "新容量")
    private BigDecimal newCapacity;

    /** 长度 */
    @Excel(name = "长度")
    private BigDecimal length;

    /** 新长度 */
    @Excel(name = "新长度")
    private BigDecimal newLength;

    /** 建筑面积(m2) */
    @Excel(name = "建筑面积(m2)")
    private BigDecimal buildingArea;

    /** 新建筑面积(m2) */
    @Excel(name = "新建筑面积(m2)")
    private BigDecimal newBuildingArea;

    /** 占地面积(m2) */
    @Excel(name = "占地面积(m2)")
    private BigDecimal floorArea;

    /** 新占地面积(m2) */
    @Excel(name = "新占地面积(m2)")
    private BigDecimal newFloorArea;

    /** 厂房层数 */
    @Excel(name = "厂房层数")
    private Long layerNum;

    /** 新厂房层数 */
    @Excel(name = "新厂房层数")
    private Long newLayerNum;

    /** 经济用途 */
    @Excel(name = "经济用途")
    private String economyPurpose;

    /** 资金来源 */
    @Excel(name = "资金来源")
    private String source;

    /** 新资金来源 */
    @Excel(name = "新资金来源")
    private String newSource;

    /** 资产性质 */
    @Excel(name = "资产性质")
    private String assetCharacter;

    /** 新资产性质 */
    @Excel(name = "新资产性质")
    private String newAssetCharacter;

    /** 装备水平 */
    @Excel(name = "装备水平")
    private String levels;

    /** 新装备水平 */
    @Excel(name = "新装备水平")
    private String newLevels;

    /** 料号 */
    @Excel(name = "料号")
    private String materialNo;

    /** 储位 */
    @Excel(name = "储位")
    private String locationNo;

    /** 交易单号 */
    @Excel(name = "交易单号")
    private String tradeNo;

    /** 增加方式 */
    @Excel(name = "增加方式")
    private String cardModel;

    /** 新增加方式 */
    @Excel(name = "新增加方式")
    private String newCardModel;

    /** 拆分方式 */
    @Excel(name = "拆分方式")
    private String splitType;

    /** 拆分数量 */
    @Excel(name = "拆分数量")
    private Long splitCount;

    /** 来源系统 */
    @Excel(name = "来源系统")
    private String sourceId;

    /** 程式编码 */
    @Excel(name = "程式编码")
    private String pgrmId;

    /** 新增人姓名 */
    @Excel(name = "新增人姓名")
    private String createName;

    /** 修改人姓名 */
    @Excel(name = "修改人姓名")
    private String updateName;

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
    public void setChangeId(String changeId)
    {
        this.changeId = changeId;
    }

    public String getChangeId()
    {
        return changeId;
    }
    public void setChangeNo(String changeNo)
    {
        this.changeNo = changeNo;
    }

    public String getChangeNo()
    {
        return changeNo;
    }
    public void setPeriod(Date period)
    {
        this.period = period;
    }

    public Date getPeriod()
    {
        return period;
    }
    public void setChangePrice(BigDecimal changePrice)
    {
        this.changePrice = changePrice;
    }

    public BigDecimal getChangePrice()
    {
        return changePrice;
    }
    public void setAssetId(String assetId)
    {
        this.assetId = assetId;
    }

    public String getAssetId()
    {
        return assetId;
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
    public void setNewAssetGroup(String newAssetGroup)
    {
        this.newAssetGroup = newAssetGroup;
    }

    public String getNewAssetGroup()
    {
        return newAssetGroup;
    }
    public void setAssetName(String assetName)
    {
        this.assetName = assetName;
    }

    public String getAssetName()
    {
        return assetName;
    }
    public void setNewAssetName(String newAssetName)
    {
        this.newAssetName = newAssetName;
    }

    public String getNewAssetName()
    {
        return newAssetName;
    }
    public void setAssetDesc(String assetDesc)
    {
        this.assetDesc = assetDesc;
    }

    public String getAssetDesc()
    {
        return assetDesc;
    }
    public void setAssetType(String assetType)
    {
        this.assetType = assetType;
    }

    public String getAssetType()
    {
        return assetType;
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
    public void setAccuDepr(BigDecimal accuDepr)
    {
        this.accuDepr = accuDepr;
    }

    public BigDecimal getAccuDepr()
    {
        return accuDepr;
    }
    public void setDeprCount(Long deprCount)
    {
        this.deprCount = deprCount;
    }

    public Long getDeprCount()
    {
        return deprCount;
    }
    public void setNewDeprCount(Long newDeprCount)
    {
        this.newDeprCount = newDeprCount;
    }

    public Long getNewDeprCount()
    {
        return newDeprCount;
    }
    public void setDeptNo(String deptNo)
    {
        this.deptNo = deptNo;
    }

    public String getDeptNo()
    {
        return deptNo;
    }
    public void setNewDeptNo(String newDeptNo)
    {
        this.newDeptNo = newDeptNo;
    }

    public String getNewDeptNo()
    {
        return newDeptNo;
    }
    public void setAssetUser(String assetUser)
    {
        this.assetUser = assetUser;
    }

    public String getAssetUser()
    {
        return assetUser;
    }
    public void setNewAssetUser(String newAssetUser)
    {
        this.newAssetUser = newAssetUser;
    }

    public String getNewAssetUser()
    {
        return newAssetUser;
    }
    public void setUserDept(String userDept)
    {
        this.userDept = userDept;
    }

    public String getUserDept()
    {
        return userDept;
    }
    public void setNewUserDept(String newUserDept)
    {
        this.newUserDept = newUserDept;
    }

    public String getNewUserDept()
    {
        return newUserDept;
    }
    public void setQty(Long qty)
    {
        this.qty = qty;
    }

    public Long getQty()
    {
        return qty;
    }
    public void setNewQty(Long newQty)
    {
        this.newQty = newQty;
    }

    public Long getNewQty()
    {
        return newQty;
    }
    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    public String getUnit()
    {
        return unit;
    }
    public void setNewUnit(String newUnit)
    {
        this.newUnit = newUnit;
    }

    public String getNewUnit()
    {
        return newUnit;
    }
    public void setAssetStatus(String assetStatus)
    {
        this.assetStatus = assetStatus;
    }

    public String getAssetStatus()
    {
        return assetStatus;
    }
    public void setNewAssetStatus(String newAssetStatus)
    {
        this.newAssetStatus = newAssetStatus;
    }

    public String getNewAssetStatus()
    {
        return newAssetStatus;
    }
    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getLocation()
    {
        return location;
    }
    public void setNewLocation(String newLocation)
    {
        this.newLocation = newLocation;
    }

    public String getNewLocation()
    {
        return newLocation;
    }
    public void setAssetSpec(String assetSpec)
    {
        this.assetSpec = assetSpec;
    }

    public String getAssetSpec()
    {
        return assetSpec;
    }
    public void setNewAssetSpec(String newAssetSpec)
    {
        this.newAssetSpec = newAssetSpec;
    }

    public String getNewAssetSpec()
    {
        return newAssetSpec;
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
    public void setNewSupplyerNo(String newSupplyerNo)
    {
        this.newSupplyerNo = newSupplyerNo;
    }

    public String getNewSupplyerNo()
    {
        return newSupplyerNo;
    }
    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer()
    {
        return manufacturer;
    }
    public void setNewManufacturer(String newManufacturer)
    {
        this.newManufacturer = newManufacturer;
    }

    public String getNewManufacturer()
    {
        return newManufacturer;
    }
    public void setWeight(BigDecimal weight)
    {
        this.weight = weight;
    }

    public BigDecimal getWeight()
    {
        return weight;
    }
    public void setNewWeight(BigDecimal newWeight)
    {
        this.newWeight = newWeight;
    }

    public BigDecimal getNewWeight()
    {
        return newWeight;
    }
    public void setCapacity(BigDecimal capacity)
    {
        this.capacity = capacity;
    }

    public BigDecimal getCapacity()
    {
        return capacity;
    }
    public void setNewCapacity(BigDecimal newCapacity)
    {
        this.newCapacity = newCapacity;
    }

    public BigDecimal getNewCapacity()
    {
        return newCapacity;
    }
    public void setLength(BigDecimal length)
    {
        this.length = length;
    }

    public BigDecimal getLength()
    {
        return length;
    }
    public void setNewLength(BigDecimal newLength)
    {
        this.newLength = newLength;
    }

    public BigDecimal getNewLength()
    {
        return newLength;
    }
    public void setBuildingArea(BigDecimal buildingArea)
    {
        this.buildingArea = buildingArea;
    }

    public BigDecimal getBuildingArea()
    {
        return buildingArea;
    }
    public void setNewBuildingArea(BigDecimal newBuildingArea)
    {
        this.newBuildingArea = newBuildingArea;
    }

    public BigDecimal getNewBuildingArea()
    {
        return newBuildingArea;
    }
    public void setFloorArea(BigDecimal floorArea)
    {
        this.floorArea = floorArea;
    }

    public BigDecimal getFloorArea()
    {
        return floorArea;
    }
    public void setNewFloorArea(BigDecimal newFloorArea)
    {
        this.newFloorArea = newFloorArea;
    }

    public BigDecimal getNewFloorArea()
    {
        return newFloorArea;
    }
    public void setLayerNum(Long layerNum)
    {
        this.layerNum = layerNum;
    }

    public Long getLayerNum()
    {
        return layerNum;
    }
    public void setNewLayerNum(Long newLayerNum)
    {
        this.newLayerNum = newLayerNum;
    }

    public Long getNewLayerNum()
    {
        return newLayerNum;
    }
    public void setEconomyPurpose(String economyPurpose)
    {
        this.economyPurpose = economyPurpose;
    }

    public String getEconomyPurpose()
    {
        return economyPurpose;
    }
    public void setSource(String source)
    {
        this.source = source;
    }

    public String getSource()
    {
        return source;
    }
    public void setNewSource(String newSource)
    {
        this.newSource = newSource;
    }

    public String getNewSource()
    {
        return newSource;
    }
    public void setAssetCharacter(String assetCharacter)
    {
        this.assetCharacter = assetCharacter;
    }

    public String getAssetCharacter()
    {
        return assetCharacter;
    }
    public void setNewAssetCharacter(String newAssetCharacter)
    {
        this.newAssetCharacter = newAssetCharacter;
    }

    public String getNewAssetCharacter()
    {
        return newAssetCharacter;
    }
    public void setLevels(String levels)
    {
        this.levels = levels;
    }

    public String getLevels()
    {
        return levels;
    }
    public void setNewLevels(String newLevels)
    {
        this.newLevels = newLevels;
    }

    public String getNewLevels()
    {
        return newLevels;
    }
    public void setMaterialNo(String materialNo)
    {
        this.materialNo = materialNo;
    }

    public String getMaterialNo()
    {
        return materialNo;
    }
    public void setLocationNo(String locationNo)
    {
        this.locationNo = locationNo;
    }

    public String getLocationNo()
    {
        return locationNo;
    }
    public void setTradeNo(String tradeNo)
    {
        this.tradeNo = tradeNo;
    }

    public String getTradeNo()
    {
        return tradeNo;
    }
    public void setCardModel(String cardModel)
    {
        this.cardModel = cardModel;
    }

    public String getCardModel()
    {
        return cardModel;
    }
    public void setNewCardModel(String newCardModel)
    {
        this.newCardModel = newCardModel;
    }

    public String getNewCardModel()
    {
        return newCardModel;
    }
    public void setSplitType(String splitType)
    {
        this.splitType = splitType;
    }

    public String getSplitType()
    {
        return splitType;
    }
    public void setSplitCount(Long splitCount)
    {
        this.splitCount = splitCount;
    }

    public Long getSplitCount()
    {
        return splitCount;
    }
    public void setSourceId(String sourceId)
    {
        this.sourceId = sourceId;
    }

    public String getSourceId()
    {
        return sourceId;
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
            .append("uuid", getUuid())
            .append("companyId", getCompanyId())
            .append("changeId", getChangeId())
            .append("changeNo", getChangeNo())
            .append("period", getPeriod())
            .append("changePrice", getChangePrice())
            .append("assetId", getAssetId())
            .append("assetNo", getAssetNo())
            .append("assetGroup", getAssetGroup())
            .append("newAssetGroup", getNewAssetGroup())
            .append("assetName", getAssetName())
            .append("newAssetName", getNewAssetName())
            .append("assetDesc", getAssetDesc())
            .append("assetType", getAssetType())
            .append("orgValue", getOrgValue())
            .append("getValue", getGetValue())
            .append("accuDepr", getAccuDepr())
            .append("deprCount", getDeprCount())
            .append("newDeprCount", getNewDeprCount())
            .append("deptNo", getDeptNo())
            .append("newDeptNo", getNewDeptNo())
            .append("assetUser", getAssetUser())
            .append("newAssetUser", getNewAssetUser())
            .append("userDept", getUserDept())
            .append("newUserDept", getNewUserDept())
            .append("qty", getQty())
            .append("newQty", getNewQty())
            .append("unit", getUnit())
            .append("newUnit", getNewUnit())
            .append("assetStatus", getAssetStatus())
            .append("newAssetStatus", getNewAssetStatus())
            .append("location", getLocation())
            .append("newLocation", getNewLocation())
            .append("assetSpec", getAssetSpec())
            .append("newAssetSpec", getNewAssetSpec())
            .append("contractNo", getContractNo())
            .append("budgetNo", getBudgetNo())
            .append("supplyerNo", getSupplyerNo())
            .append("newSupplyerNo", getNewSupplyerNo())
            .append("manufacturer", getManufacturer())
            .append("newManufacturer", getNewManufacturer())
            .append("weight", getWeight())
            .append("newWeight", getNewWeight())
            .append("capacity", getCapacity())
            .append("newCapacity", getNewCapacity())
            .append("length", getLength())
            .append("newLength", getNewLength())
            .append("buildingArea", getBuildingArea())
            .append("newBuildingArea", getNewBuildingArea())
            .append("floorArea", getFloorArea())
            .append("newFloorArea", getNewFloorArea())
            .append("layerNum", getLayerNum())
            .append("newLayerNum", getNewLayerNum())
            .append("economyPurpose", getEconomyPurpose())
            .append("source", getSource())
            .append("newSource", getNewSource())
            .append("assetCharacter", getAssetCharacter())
            .append("newAssetCharacter", getNewAssetCharacter())
            .append("levels", getLevels())
            .append("newLevels", getNewLevels())
            .append("materialNo", getMaterialNo())
            .append("locationNo", getLocationNo())
            .append("tradeNo", getTradeNo())
            .append("cardModel", getCardModel())
            .append("newCardModel", getNewCardModel())
            .append("splitType", getSplitType())
            .append("splitCount", getSplitCount())
            .append("sourceId", getSourceId())
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
