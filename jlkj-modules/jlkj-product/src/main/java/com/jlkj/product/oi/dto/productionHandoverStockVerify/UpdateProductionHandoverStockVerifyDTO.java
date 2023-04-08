package com.jlkj.product.oi.dto.productionhandoverstockverify;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author yzl
 * @Description 交班仓存核验 修改
 * @create 2022年7月26日 08:48:35
 */
@Data
public class UpdateProductionHandoverStockVerifyDTO {

	@NotNull(message = "主键标识;UUID为必须项")
	@JsonProperty("id")
	private String id ;

	@NotNull(message = "1#配煤仓料位为必须项")
	@JsonProperty("coal_blending_level1")
	private BigDecimal coalBlendingLevel1 ;

	@NotNull(message = "1#配煤仓重量为必须项")
	@JsonProperty("coal_blending_stock1")
	private BigDecimal coalBlendingStock1 ;

	@NotNull(message = "2#配煤仓料位为必须项")
	@JsonProperty("coal_blending_level2")
	private BigDecimal coalBlendingLevel2 ;

	@NotNull(message = "2#配煤仓重量为必须项")
	@JsonProperty("coal_blending_stock2")
	private BigDecimal coalBlendingStock2 ;

	@NotNull(message = "3#配煤仓料位为必须项")
	@JsonProperty("coal_blending_level3")
	private BigDecimal coalBlendingLevel3 ;

	@NotNull(message = "3#配煤仓重量为必须项")
	@JsonProperty("coal_blending_stock3")
	private BigDecimal coalBlendingStock3 ;

	@NotNull(message = "4#配煤仓料位为必须项")
	@JsonProperty("coal_blending_level4")
	private BigDecimal coalBlendingLevel4 ;

	@NotNull(message = "4#配煤仓重量为必须项")
	@JsonProperty("coal_blending_stock4")
	private BigDecimal coalBlendingStock4 ;

	@NotNull(message = "5#配煤仓料位为必须项")
	@JsonProperty("coal_blending_level5")
	private BigDecimal coalBlendingLevel5 ;

	@NotNull(message = "5#配煤仓重量为必须项")
	@JsonProperty("coal_blending_stock5")
	private BigDecimal coalBlendingStock5 ;

	@NotNull(message = "6#配煤仓料位为必须项")
	@JsonProperty("coal_blending_level6")
	private BigDecimal coalBlendingLevel6 ;

	@NotNull(message = "6#配煤仓重量为必须项")
	@JsonProperty("coal_blending_stock6")
	private BigDecimal coalBlendingStock6 ;

	@NotNull(message = "7#配煤仓料位为必须项")
	@JsonProperty("coal_blending_level7")
	private BigDecimal coalBlendingLevel7 ;

	@NotNull(message = "7#配煤仓重量为必须项")
	@JsonProperty("coal_blending_stock7")
	private BigDecimal coalBlendingStock7 ;

	@NotNull(message = "8#配煤仓料位为必须项")
	@JsonProperty("coal_blending_level8")
	private BigDecimal coalBlendingLevel8 ;

	@NotNull(message = "8#配煤仓重量为必须项")
	@JsonProperty("coal_blending_stock8")
	private BigDecimal coalBlendingStock8 ;

	@NotNull(message = "9#配煤仓料位为必须项")
	@JsonProperty("coal_blending_level9")
	private BigDecimal coalBlendingLevel9 ;

	@NotNull(message = "9#配煤仓重量为必须项")
	@JsonProperty("coal_blending_stock9")
	private BigDecimal coalBlendingStock9 ;

	@NotNull(message = "10#配煤仓料位为必须项")
	@JsonProperty("coal_blending_level10")
	private BigDecimal coalBlendingLevel10 ;

	@NotNull(message = "10#配煤仓重量为必须项")
	@JsonProperty("coal_blending_stock10")
	private BigDecimal coalBlendingStock10 ;

	@NotNull(message = "1#贮煤塔料位为必须项")
	@JsonProperty("coal_storage_tower_level1")
	private BigDecimal coalStorageTowerLevel1 ;

	@NotNull(message = "1#贮煤塔重量为必须项")
	@JsonProperty("coal_storage_tower_stock1")
	private BigDecimal coalStorageTowerStock1 ;

	@NotNull(message = "2#贮煤塔料位为必须项")
	@JsonProperty("coal_storage_tower_level2")
	private BigDecimal coalStorageTowerLevel2 ;

	@NotNull(message = "2#贮煤塔重量为必须项")
	@JsonProperty("coal_storage_tower_stock2")
	private BigDecimal coalStorageTowerStock2 ;

	@NotNull(message = "3#贮煤塔料位为必须项")
	@JsonProperty("coal_storage_tower_level3")
	private BigDecimal coalStorageTowerLevel3 ;

	@NotNull(message = "3#贮煤塔重量为必须项")
	@JsonProperty("coal_storage_tower_stock3")
	private BigDecimal coalStorageTowerStock3 ;

	@NotNull(message = "4#贮煤塔料位为必须项")
	@JsonProperty("coal_storage_tower_level4")
	private BigDecimal coalStorageTowerLevel4 ;

	@NotNull(message = "4#贮煤塔重量为必须项")
	@JsonProperty("coal_storage_tower_stock4")
	private BigDecimal coalStorageTowerStock4 ;

	@NotNull(message = "5#贮煤塔料位为必须项")
	@JsonProperty("coal_storage_tower_level5")
	private BigDecimal coalStorageTowerLevel5 ;

	@NotNull(message = "5#贮煤塔重量为必须项")
	@JsonProperty("coal_storage_tower_stock5")
	private BigDecimal coalStorageTowerStock5 ;

	@NotNull(message = "6#贮煤塔料位为必须项")
	@JsonProperty("coal_storage_tower_level6")
	private BigDecimal coalStorageTowerLevel6 ;

	@NotNull(message = "6#贮煤塔重量为必须项")
	@JsonProperty("coal_storage_tower_stock6")
	private BigDecimal coalStorageTowerStock6 ;

	@NotNull(message = "修改人id为必须项")
	@JsonProperty("modify_user_id")
	private String modifyUserId ;

	@NotNull(message = "修改人为必须项")
	@JsonProperty("modify_user_name")
	private String modifyUserName ;


}

