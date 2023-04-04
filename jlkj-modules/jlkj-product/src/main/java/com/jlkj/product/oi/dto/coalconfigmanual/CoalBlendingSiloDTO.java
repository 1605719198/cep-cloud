package com.jlkj.product.oi.dto.coalconfigmanual;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zyf
 * @Description
 * @create 2022-12-06 15:08
 */
@Data
public class CoalBlendingSiloDTO {

    @Schema(title = "配煤仓号")
    private Integer bunker_number;

    @Schema(title = "配煤前料位")
    private BigDecimal before_material_level;

    @Schema(title = "配煤后料位")
    private BigDecimal after_material_level;

    @Schema(title = "配煤前存量")
    private BigDecimal before_stock;

    @Schema(title = "配煤后存量")
    private BigDecimal after_stock;

    @Schema(title = "配煤时长")
    private BigDecimal duration;

    @Schema(title = "配煤重量")
    private BigDecimal material_weight;

    @Schema(title = "物料编号")
    private String material_number;

    @Schema(title = "物料名称")
    private String material_name;

    @Schema(title = "物料小煤种编号")
    private String material_small_code;

    @Schema(title = "物料小煤种名称")
    private String material_small_name;
}
