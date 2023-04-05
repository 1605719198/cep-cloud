package com.jlkj.product.oi.vo.productionpowergenerationperformance;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-首页-发电量
 * @author sudeyou
 * @since 2022-09-01 09:44:19
 */
@Data
@Schema(description = "VO类-首页-发电量")
public class ListHomeProductionPowerGenerationYieldVO {

    /**
     * 日期
     */
    @Schema(description = "日期")
    @JsonProperty("stat_time")
    private String statTime;

    /**
     * 计划产量
     */
    @Schema(description = "计划产量")
    @JsonProperty("plan_yield")
    private BigDecimal planYield;

    /**
     * 实绩产量
     */
    @Schema(description = "实绩产量")
    @JsonProperty("performance_yield")
    private BigDecimal performanceYield;

}

