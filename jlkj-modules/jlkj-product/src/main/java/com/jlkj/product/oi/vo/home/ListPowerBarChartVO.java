package com.jlkj.product.oi.vo.home;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * VO类-生产管理-首页-发电计划实绩图表
 * @author sudeyou
 * @since 2022-07-15 14:48:56
 */
@Data
@Schema(description = "VO类-生产管理-首页-发电计划实绩图表")
public class ListPowerBarChartVO {

    /**
     * 汽轮机号
     */
    @Schema(description = "汽轮机号")
    @JsonProperty("turbine_no")
    private String turbineNo;

    /**
     * 计划产量
     */
    @Schema(description = "计划产量")
    @JsonProperty("power_plan")
    private BigDecimal powerPlan;

    /**
     * 实绩产量
     */
    @Schema(description = "实绩产量")
    @JsonProperty("power_performance")
    private BigDecimal powerPerformance;

}

