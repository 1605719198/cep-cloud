package com.jlkj.product.oi.vo.productioncokeovens;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-煤消耗
 *
 * @author sudeyou
 * @since 2022-11-01 10:10:47
 */
@Data
@Schema(description = "VO类-煤消耗")
public class ListProductionCoalConsumeVO {

    /**
     * 焦炭等级料号
     */
    @Schema(description = "焦炭等级料号")
    @JsonProperty("coke_materials_code")
    private String cokeMaterialsCode;

    /**
     * 煤料号
     */
    @Schema(description = "煤料号")
    @JsonProperty("coal_materials_code")
    private String coalMaterialsCode;

    /**
     * 重量
     */
    @Schema(description = "重量")
    @JsonProperty("qty")
    private BigDecimal qty;

}

