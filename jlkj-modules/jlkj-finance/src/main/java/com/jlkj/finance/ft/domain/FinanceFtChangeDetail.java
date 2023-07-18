package com.jlkj.finance.ft.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 资产变动单主档
 * @TableName finance_ft_change_detail
 */
@TableName(value ="finance_ft_change_detail")
@Data
public class FinanceFtChangeDetail implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String uuid;

    /**
     * 公司别
     */
    private String companyId;

    /**
     * 变动单id
     */
    private String changeId;

    /**
     * 单据编号
     */
    private String changeNo;

    /**
     * 变动期间
     */
    private Date period;

    /**
     * 单据类型
     */
    private BigDecimal changePrice;

    /**
     * 资产id
     */
    private String assetId;

    /**
     * 资产编码
     */
    private String assetNo;

    /**
     * 资产分类
     */
    private String assetGroup;

    /**
     * 新资产分类
     */
    private String newAssetGroup;

    /**
     * 资产名称
     */
    private String assetName;

    /**
     * 新资产名称
     */
    private String newAssetName;

    /**
     * 资产说明
     */
    private String assetDesc;

    /**
     * 资产类别
     */
    private String assetType;

    /**
     * 资产原值
     */
    private BigDecimal orgValue;

    /**
     * 资产取得价值
     */
    private BigDecimal getValue;

    /**
     * 累计折旧
     */
    private BigDecimal accuDepr;

    /**
     * 折旧年限
     */
    private Integer deprCount;

    /**
     * 新折旧年限
     */
    private Integer newDeprCount;

    /**
     * 归属部门
     */
    private String deptNo;

    /**
     * 新归属部门
     */
    private String newDeptNo;

    /**
     * 资产保管人
     */
    private String assetUser;

    /**
     * 新资产保管人
     */
    private String newAssetUser;

    /**
     * 资产保管人部门
     */
    private String userDept;

    /**
     * 新资产保管人部门
     */
    private String newUserDept;

    /**
     * 成本中心
     */
    private String costCenter;

    /**
     * 新成本中心
     */
    private String newCostCenter;

    /**
     * 数量
     */
    private Integer qty;

    /**
     * 新数量
     */
    private Integer newQty;

    /**
     * 计量单位
     */
    private String unit;

    /**
     * 新计量单位
     */
    private String newUnit;

    /**
     * 使用状态
     */
    private String assetStatus;

    /**
     * 新使用状态
     */
    private String newAssetStatus;

    /**
     * 存放地点
     */
    private String location;

    /**
     * 新存放地点
     */
    private String newLocation;

    /**
     * 资产规格
     */
    private String assetSpec;

    /**
     * 新资产规格
     */
    private String newAssetSpec;

    /**
     * 合同号
     */
    private String contractNo;

    /**
     * 预算编号
     */
    private String budgetNo;

    /**
     * 供应商
     */
    private String supplyerNo;

    /**
     * 新供应商
     */
    private String newSupplyerNo;

    /**
     * 制造商
     */
    private String manufacturer;

    /**
     * 新制造商
     */
    private String newManufacturer;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 新重量
     */
    private BigDecimal newWeight;

    /**
     * 容量
     */
    private BigDecimal capacity;

    /**
     * 新容量
     */
    private BigDecimal newCapacity;

    /**
     * 长度
     */
    private BigDecimal length;

    /**
     * 新长度
     */
    private BigDecimal newLength;

    /**
     * 建筑面积(m2)
     */
    private BigDecimal buildingArea;

    /**
     * 新建筑面积(m2)
     */
    private BigDecimal newBuildingArea;

    /**
     * 占地面积(m2)
     */
    private BigDecimal floorArea;

    /**
     * 新占地面积(m2)
     */
    private BigDecimal newFloorArea;

    /**
     * 厂房层数
     */
    private Integer layerNum;

    /**
     * 新厂房层数
     */
    private Integer newLayerNum;

    /**
     * 经济用途
     */
    private String economyPurpose;

    /**
     * 资金来源
     */
    private String source;

    /**
     * 新资金来源
     */
    private String newSource;

    /**
     * 资产性质
     */
    private String assetCharacter;

    /**
     * 新资产性质
     */
    private String newAssetCharacter;

    /**
     * 装备水平
     */
    private String levels;

    /**
     * 新装备水平
     */
    private String newLevels;

    /**
     * 料号
     */
    private String materialNo;

    /**
     * 储位
     */
    private String locationNo;

    /**
     * 交易单号
     */
    private String tradeNo;

    /**
     * 增加方式
     */
    private String cardModel;

    /**
     * 新增加方式
     */
    private String newCardModel;

    /**
     * 拆分方式
     */
    private String splitType;

    /**
     * 拆分数量
     */
    private Integer splitCount;

    /**
     * 来源系统
     */
    private String sourceId;

    /**
     * 程式编码
     */
    private String pgrmId;

    /**
     * 新增人
     */
    private String createBy;

    /**
     * 新增人姓名
     */
    private String createName;

    /**
     * 新增时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改人姓名
     */
    private String updateName;

    /**
     * 修改时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}