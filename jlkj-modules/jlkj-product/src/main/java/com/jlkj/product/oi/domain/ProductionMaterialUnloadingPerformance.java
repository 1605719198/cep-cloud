package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author  generator
 * 物料卸车实绩
 * @TableName product_oi_material_unloading_performance
 */
@TableName(value ="product_oi_material_unloading_performance")
@Data
public class ProductionMaterialUnloadingPerformance implements Serializable {
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 检斤计划编号 */
    private String planId ;

    /** 检斤计划日期 */
    private Date planDate ;

    /** 供货单位编号 */
    private String supplierNumber ;

    /** 供货单位名称 */
    private String supplierName ;

    /** 运输方式 */
    private String shippingMethod ;

    /** 车号 */
    private String vehicleNumber ;

    /** erp储位编号 */
    private String storageSpacesId ;

    /** 物料编号 */
    private String materialNumber ;

    /** 物料名称 */
    private String materialName ;

    /** 物料代码 */
    private String materialCode ;

    /** 物料重量 */
    private BigDecimal materialWeight ;

    /** 班次 */
    private String shiftName ;

    /** 班别 */
    private String className ;

    /** 卸车时间 */
    private Date unloadingTime ;

    /** 创建人 */
    private String createUserId ;

    /** 创建人 */
    private String createUserName ;

    /** 创建时间 */
    private Date createTime ;

    /** 修改人id */
    private String modifyUserId ;

    /** 修改人 */
    private String modifyUserName ;

    /** 修改时间 */
    private Date modifyTime ;

    /** 平台储位编号 */
    private String storageMaintainId ;

    /** 平台储位名称 */
    private String storageMaintainSpaceName ;

    /** erp储位说明 */
    private String storageSpacesName ;

    /** 组批号 */
    private String chkNo ;

    /**
     * 来源地代码0609新增
     */
    private String fromCode;

    /**
     * 来源地名称0609新增
     */
    private String fromDecs;

    /** 到货日期 */
    private Date recvDate ;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
