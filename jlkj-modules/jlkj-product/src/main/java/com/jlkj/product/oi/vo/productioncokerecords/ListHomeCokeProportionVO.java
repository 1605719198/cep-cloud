package com.jlkj.product.oi.vo.productioncokerecords;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-首页-焦碳比例
 * @author sudeyou
 * @since 2022-09-02 14:38:52
 */
@Data
@Schema(description = "VO类-首页-焦碳比例")
public class ListHomeCokeProportionVO {
    /**
     * 物料名称
     */
    @Schema(description = "物料名称")
    @JsonProperty("material_name")
    private String materialName;

    /**
     * 产量
     */
    @Schema(description = "产量")
    @JsonProperty("yield")
    private BigDecimal yield;

}

