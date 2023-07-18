package com.jlkj.finance.ft.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description:
 * @Author: SunXuTong
 * @date: 2023/7/12 16:06
 */
@Data
public class FinanceFtAssetAddDTO {

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

    /** 变动单id */
    @Excel(name = "变动单id")
    private String changeId;

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

    /**
     * 单据id
     */
    private String billId;

    /**
     * 单据编号
     */
    private String billNo;

    /**
     * 附属设备编码
     */
    private String deviceNo;

    /**
     * 附属设备名称
     */
    private String deviceName;

    /**
     * 备注
     */
    private String deviceRemark;

    /**
     * 新增人
     */
    private String createBy;

    /**
     * 新增日期
     */
    private Date createTime;

    /**
     * 异动人
     */
    private String updateBy;

    /**
     * 异动日期
     */
    private Date updateTime;
}
