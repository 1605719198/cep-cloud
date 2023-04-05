package com.jlkj.product.oi.dto.productionplanconfigcokedetail;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * DTO类-获取配煤仓配煤计划信息
 *
 * @author sudeyou
 * @since 2022-11-18 10:40:14
 */
@Data
@Schema(description = "DTO类-获取配煤仓配煤计划信息")
public class InfoProductionPlanConfigCokeDetailByWarehouseNumberDTO {
    /**
     * 配煤仓号
     */
    @NotNull(message = "配煤仓号为必填项")
    @Schema(description = "配煤仓号")
    @JsonProperty("warehouse_number")
    private Integer warehouseNumber;

    /**
     * 配煤计划id
     */
    @NotNull(message = "配煤计划id为必填项")
    @Schema(description = "配煤计划id")
    @JsonProperty("plan_id")
    private String planId;

}
