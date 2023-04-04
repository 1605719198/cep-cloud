package com.jlkj.product.oi.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author  generator
 */
@TableName(value ="product_oi_handover_stock_verify")
@Data
public class ProductionHandoverStockVerify implements Serializable{
    /** 主键标识;UUID */
    @TableId
    private String id ;

    /** 班次 */
	private String shift ;

    /** 班别 */
	private String classType ;

    /** 开始时间 */
	private Date startTime ;

    /** 结束时间 */
	private Date endTime ;

    /** 1#配煤仓料位 */
	private BigDecimal coalBlendingLevel1 ;

    /** 1#配煤仓重量 */
	private BigDecimal coalBlendingStock1 ;

    /** 2#配煤仓料位 */
	private BigDecimal coalBlendingLevel2 ;

    /** 2#配煤仓重量 */
	private BigDecimal coalBlendingStock2 ;

    /** 3#配煤仓料位 */
	private BigDecimal coalBlendingLevel3 ;

    /** 3#配煤仓重量 */
	private BigDecimal coalBlendingStock3 ;

    /** 4#配煤仓料位 */
	private BigDecimal coalBlendingLevel4 ;

    /** 4#配煤仓重量 */
	private BigDecimal coalBlendingStock4 ;

    /** 5#配煤仓料位 */
	private BigDecimal coalBlendingLevel5 ;

    /** 5#配煤仓重量 */
	private BigDecimal coalBlendingStock5 ;

    /** 6#配煤仓料位 */
	private BigDecimal coalBlendingLevel6 ;

    /** 6#配煤仓重量 */
	private BigDecimal coalBlendingStock6 ;

    /** 7#配煤仓料位 */
	private BigDecimal coalBlendingLevel7 ;

    /** 7#配煤仓重量 */
	private BigDecimal coalBlendingStock7 ;

    /** 8#配煤仓料位 */
	private BigDecimal coalBlendingLevel8 ;

    /** 8#配煤仓重量 */
	private BigDecimal coalBlendingStock8 ;

    /** 9#配煤仓料位 */
	private BigDecimal coalBlendingLevel9 ;

    /** 9#配煤仓重量 */
	private BigDecimal coalBlendingStock9 ;

    /** 10#配煤仓料位 */
	private BigDecimal coalBlendingLevel10 ;

    /** 10#配煤仓重量 */
	private BigDecimal coalBlendingStock10 ;

    /** 1#贮煤塔料位 */
	private BigDecimal coalStorageTowerLevel1 ;

    /** 1#贮煤塔重量 */
	private BigDecimal coalStorageTowerStock1 ;

    /** 2#贮煤塔料位 */
	private BigDecimal coalStorageTowerLevel2 ;

    /** 2#贮煤塔重量 */
	private BigDecimal coalStorageTowerStock2 ;

    /** 3#贮煤塔料位 */
	private BigDecimal coalStorageTowerLevel3 ;

    /** 3#贮煤塔重量 */
	private BigDecimal coalStorageTowerStock3 ;

    /** 4#贮煤塔料位 */
	private BigDecimal coalStorageTowerLevel4 ;

    /** 4#贮煤塔重量 */
	private BigDecimal coalStorageTowerStock4 ;

    /** 5#贮煤塔料位 */
	private BigDecimal coalStorageTowerLevel5 ;

    /** 5#贮煤塔重量 */
	private BigDecimal coalStorageTowerStock5 ;

    /** 6#贮煤塔料位 */
	private BigDecimal coalStorageTowerLevel6 ;

    /** 6#贮煤塔重量 */
	private BigDecimal coalStorageTowerStock6 ;

    /** 修改人id */
	private String modifyUserId ;

    /** 修改人 */
	private String modifyUserName ;

    /** 修改时间 */
	private Date modifyTime ;

    /** 配煤仓总存量 */
	private BigDecimal coalBlendingStockTotal ;

    /** 贮煤塔总存量123 */
	private BigDecimal coalStorageTowerStockTotal123 ;

    /** 贮煤塔总存量456 */
	private BigDecimal coalStorageTowerStockTotal456 ;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

