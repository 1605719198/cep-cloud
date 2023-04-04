package com.jlkj.product.oi.vo.productionplanconfigcokedetail;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * VO类-获取配煤仓配煤计划信息
 *
 * @author sudeyou
 * @since 2022-11-18 10:40:14
 */
@Data
@Schema(description = "VO类-获取配煤仓配煤计划信息")
public class InfoProductionPlanConfigCokeDetailByWarehouseNumberVO {
    /**
     * 计划id
     */
    @Schema(description = "计划id")
    @JsonProperty("plan_id")
    private String planId;

    /**
     * 计划编号
     */
    @Schema(description = "计划编号")
    @JsonProperty("plan_number")
    private String planNumber;

    /**
     * 煤种料号
     */
    @Schema(description = "煤种料号")
    @JsonProperty("coal_type_code")
    private String coalTypeCode;

    /**
     * 煤种名称
     */
    @Schema(description = "煤种名称")
    @JsonProperty("coal_type_name")
    private String coalTypeName;

    /**
     * 焦炭等级名称
     */
    @Schema(description = "焦炭等级名称")
    @JsonProperty("material_name")
    private String materialName;

    /**
     * 焦炭等级料号
     */
    @Schema(description = "焦炭等级料号")
    @JsonProperty("material_code")
    private String materialCode;

    /**
     * 小煤种编号
     */
    @Schema(description = "小煤种编号")
    @JsonProperty("materials_small_code")
    private String materialsSmallCode;

    /**
     * 小煤种名称
     */
    @Schema(description = "小煤种名称")
    @JsonProperty("materials_small_name")
    private String materialsSmallName;
}
