package com.jlkj.common.dto.finance.aa;



import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 厂商基本资料
 * @author wang'mai
 * @TableName t_manufacturer_base
 */
@TableName(value ="t_manufacturer_base")
@Data
public class ManufacturerBaseInterfaceDTO implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String id;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 厂商编码
     */
    private String manufacturerId;

    /**
     * 国别
     */
    private String countryId;

    /**
     * 国别名称
     */
    private String countryName;

    /**
     * 厂商类别
     */
    private String catalog;

    /**
     * 厂商类别名称
     */
    private String catalogName;

    /**
     * 地区码
     */
    private String areaId;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 中文名称
     */
    private String manufacturerChineseName;

    /**
     * 英文名称
     */
    private String manufacturerEnglishName;

    /**
     * 厂商简称
     */
    private String manufacturerShortName;

    /**
     * 税号
     */
    private String taxNo;

    /**
     * 负责人
     */
    private String owner;

    /**
     * 负责人职称
     */
    private String ownerTitle;

    /**
     * 资本额
     */
    private BigDecimal capital;

    /**
     * 资本额币别码
     */
    private String capitalCurrencyCode;

    /**
     * 营业额
     */
    private BigDecimal turnover;

    /**
     * 营业额币别
     */
    private String turnoverCurrencyCode;

    /**
     * 建立人员
     */
    private String createUser;

    /**
     * 修改人员
     */
    private String updateUser;

    /**
     * 旧厂商编号
     */
    private String originaManufacturerId;

    /**
     * 发票地址
     */
    private String billAddress;

    /**
     * 邮递区号（发票地址）
     */
    private String billZipCode;

    /**
     * 邮递区号中文描述
     */
    private String billZipDesc;

    /**
     * 网址
     */
    private String web;

    /**
     * 状态
     */
    private String status;

    /**
     * 相关性
     */
    private String corelation;

    /**
     * 专家
     */
    private String professional;

    /**
     * 地区码
     */
    private String mainAreaId;

    /**
     * 地区码说明
     */
    private String mainAreaDesc;

    /**
     * 详细地区码
     */
    private String detailAreaId;

    /**
     * 详细地区码说明
     */
    private String detailAreaDesc;

    /**
     * 集团归属
     */
    private String groupClass;

    /**
     * 关联方
     */
    private String relatedParty;

    /**
     * 关联方细项
     */
    private String relatedPartyItem;

    /**
     * 部门代码
     */
    private String departmentCode;

    /**
     * 清洗状态
     */
    private String clearStatus;

    /**
     * 是否集团集中采购
     */
    private String isCentralizedPurchase;

    /**
     * 原中文名称
     */
    private String originalManufacturerChineseName;

    /**
     * 建立时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 上层节点id
     */
    private String parentId;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
