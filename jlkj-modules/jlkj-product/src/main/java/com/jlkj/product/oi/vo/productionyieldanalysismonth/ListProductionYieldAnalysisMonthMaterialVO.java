package com.jlkj.product.oi.vo.productionyieldanalysismonth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-生产管理-产量跟踪-图表-物料名称(日)
 * @author sudeyou
 * @since 2022-07-15 14:48:56
 */
@Data
@Schema(description = "VO类-生产管理-产量跟踪-图表-物料名称(月)")
public class ListProductionYieldAnalysisMonthMaterialVO {

    /**
     * 月
     */
    @Schema(description = "月")
    @JsonProperty("month")
    private Integer month;

    /**
     * 计划产量
     */
    @Schema(description = "计划产量")
    @JsonProperty("production_plan")
    private BigDecimal productionPlan;

    /**
     * 实绩产量
     */
    @Schema(description = "实绩产量")
    @JsonProperty("production_performance")
    private BigDecimal productionPerformance;

}

