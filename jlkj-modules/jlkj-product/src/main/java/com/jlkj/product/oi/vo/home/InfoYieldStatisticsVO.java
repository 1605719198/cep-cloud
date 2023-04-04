package com.jlkj.product.oi.vo.home;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-生产管理-首页-焦碳计划实绩统计
 * @author sudeyou
 * @since 2022-07-15 14:48:56
 */
@Data
@Schema(description = "VO类-生产管理-首页-焦碳计划实绩统计")
public class InfoYieldStatisticsVO {

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

