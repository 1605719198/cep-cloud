package com.jlkj.finance.gp.dto;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlkj.common.core.annotation.Excel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wang'mai
 */
@Data
public class ManufacturerBaseDTO {
    /**
     * 中文名称
     */
    private  String manufacturerChineseName ;
    /**
     * 厂商编码
     */
    private   String manufacturerId ;
    /**
     * 税号
     */
    private   String taxNo ;
    /**
     * 厂商关系
     */
    private   String[]  relation;
    /**
     * 当前页
     */
    private Long pageNum;

    /**
     * 每页记录数
     */
    private Long pageSize;
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
     * 英文名称
     */
    private String manufacturerEnglishName;

    /**
     * 厂商简称
     */
    private String manufacturerShortName;



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
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 上层节点id
     */
    private String parentId;

    /** 关联日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "关联日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date correlationDate;
    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
