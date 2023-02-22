package com.jlkj.finance.gp.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wang'mai
 */
@Data
public class ManufacturerTreeDTO {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    /**
     * 中文名称
     */
    private String manufacturerChineseName;

    /**
     * 父键
     */
    private String parentid;

    /**
     * 公司别
     */
    private String compId;

    /**
     * 节点编码
     */
    private String nodeNo;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 建立人员
     */
    private String createUser;

    /**
     * 建立时间
     */
    private Date createTime;

    /**
     * 修改人员
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建人员姓名
     */
    private String createUserName;

    /**
     * 修改人员姓名
     */
    private String updateUserName;



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
     * 上层节点id
     */
    private String parentId;

    /**
     * 逻辑删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 用来封装子节点数据
     */
    List<ManufacturerTreeDTO> children ;

    public ManufacturerTreeDTO() {
    }

    public ManufacturerTreeDTO(String nodeNo,String parentid, String id, String nodeName,List<ManufacturerTreeDTO> children) {
        this.id = id;
        this.nodeNo = nodeNo;
        this.parentid = parentid;
        this.nodeName = nodeName;
        this.children = children;

    }

    public ManufacturerTreeDTO(ManufacturerTreeDTO manufacturerTreeDTO) {
        this.id = manufacturerTreeDTO.getId();
        this.nodeNo = manufacturerTreeDTO.getNodeNo();
        this.parentid = manufacturerTreeDTO.getParentid();
        this.nodeName = manufacturerTreeDTO.getNodeName();
        this.children = manufacturerTreeDTO.getChildren();
        this.children = manufacturerTreeDTO.getChildren();
    }
}
