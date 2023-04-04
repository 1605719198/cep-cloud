package com.jlkj.product.oi.dto.productionyieldanalysismonth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * DTO类-生产管理-产量踪-图表-物料名称(月)
 * @author sudeyou
 * @since 2022-07-15 14:48:56
 */
@Data
@Schema(description = "DTO类-生产管理-产量跟踪-图表-物料名称(月)")
public class ListProductionYieldAnalysisMonthMaterialDTO {
    /**
     * 年份
     */
    @NotNull(message = "年份为必填项")
    @Schema(description = "年份")
    @JsonProperty("year")
    private Integer year;

    /**
     * 物料名称
     */
    @Schema(description = "物料名称")
    @NotNull(message = "物料名称为必填项")
    @NotEmpty(message = "物料名称不能为空")
    @Size(max = 50, message = "物料名称长度不能超过50")
    @JsonProperty("materials_name")
    private String materialsName;

}

